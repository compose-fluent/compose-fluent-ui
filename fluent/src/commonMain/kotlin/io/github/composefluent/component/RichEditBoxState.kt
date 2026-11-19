@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package io.github.composefluent.component

import androidx.compose.foundation.text.input.ExpandPolicy
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.toTextFieldBuffer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import kotlin.time.TimeSource

/**
 * Runtime limits applied independently to each undo and redo stack. [maxEntries] counts edit
 * groups; zero disables history. [maxEstimatedBytes] is a soft budget: one oversized checkpoint
 * is retained so that the most recent edit remains undoable.
 */
data class RichEditHistoryPolicy(
    val maxEntries: Int = 100,
    val maxEstimatedBytes: Long = 2L * 1024 * 1024
) {
    init {
        require(maxEntries >= 0) { "maxEntries must be non-negative" }
        require(maxEstimatedBytes > 0) { "maxEstimatedBytes must be positive" }
    }

    companion object {
        val Default = RichEditHistoryPolicy()
    }
}

/** Remembers and restores an editor state. Changes to [initialText] do not replace an existing document. */
@Composable
fun rememberRichEditBoxState(
    initialText: String = "",
    historyPolicy: RichEditHistoryPolicy = RichEditHistoryPolicy.Default
): RichEditBoxState {
    val state = rememberSaveable(saver = RichEditBoxState.Saver) { RichEditBoxState(initialText, historyPolicy) }
    SideEffect { state.historyPolicy = historyPolicy }
    return state
}

/** Remembers and restores an editor state initialized with character and paragraph styles. */
@Composable
fun rememberRichEditBoxState(
    initialText: AnnotatedString,
    historyPolicy: RichEditHistoryPolicy = RichEditHistoryPolicy.Default
): RichEditBoxState {
    val state = rememberSaveable(saver = RichEditBoxState.Saver) { RichEditBoxState(initialText, historyPolicy) }
    SideEffect { state.historyPolicy = historyPolicy }
    return state
}

/**
 * For host-managed documents. Supply the latest persisted [document] when opening [documentKey],
 * and persist edits through the host's document store. Saved instance state contains the key,
 * selection and typing style; the undo stacks live for the lifetime of the editor.
 * Compose this overload after loading the document. Use a different key when switching documents.
 */
@Composable
fun rememberRichEditBoxState(
    documentKey: String,
    document: AnnotatedString,
    historyPolicy: RichEditHistoryPolicy = RichEditHistoryPolicy.Default
): RichEditBoxState {
    val state = rememberSaveable(documentKey, saver = RichEditBoxState.documentReferenceSaver(documentKey, document)) {
        RichEditBoxState(document, historyPolicy)
    }
    SideEffect { state.historyPolicy = historyPolicy }
    return state
}

/**
 * Text, formatting, selection, and a bounded undo history for [RichEditBox].
 *
 * Use [edit] for programmatic changes and [undo]/[redo] for history. Formatting and text share
 * the same history; Compose 1.12's text-only undo history cannot restore deleted styles.
 * This state does not parse document formats or implement rich clipboard interchange.
 * Keep this state in a state holder when it must outlive the composition.
 *
 * @param initialText The initial text and styles. Link and custom annotations are not imported.
 */
@Stable
class RichEditBoxState(
    initialText: AnnotatedString,
    historyPolicy: RichEditHistoryPolicy = RichEditHistoryPolicy.Default
) {
    constructor(initialText: String = "", historyPolicy: RichEditHistoryPolicy = RichEditHistoryPolicy.Default) :
        this(AnnotatedString(initialText), historyPolicy)

    internal val textFieldState = TextFieldState(initialText.text)

    private var pendingStyle by mutableStateOf<SpanStyle?>(null)
    private var pendingSelection: TextRange? = null
    private val undoHistory = HistoryStack()
    private val redoHistory = HistoryStack()
    private var policy by mutableStateOf(historyPolicy)

    /** Lowering the limits evicts the oldest checkpoints without resetting the document. */
    var historyPolicy: RichEditHistoryPolicy
        get() = policy
        set(value) {
            if (value == policy) return
            policy = value
            undoHistory.trim(value)
            redoHistory.trim(value)
            updateHistoryAvailability()
        }
    private var lastInputTime = TimeSource.Monotonic.markNow()
    private var lastInputKind: InputKind? = null
    private var lastEditWasInput = false
    private var nextInputIsAtomic = false

    var canUndo by mutableStateOf(false)
        private set
    var canRedo by mutableStateOf(false)
        private set

    private var lastInputSelection: TextRange? = null
    private var revision = 0L

    init {
        textFieldState.edit {
            initialText.spanStyles.forEach {
                if (it.start < it.end) addStyle(it.item, TextRange(it.start, it.end), ExpandPolicy.AtEnd)
            }
            initialText.paragraphStyles.forEach {
                if (it.start < it.end) addStyle(it.item, TextRange(it.start, it.end), ExpandPolicy.InsideOnly)
            }
            selection = TextRange(length)
        }
    }

    val text: CharSequence get() = textFieldState.text

    var selection: TextRange
        get() = textFieldState.selection
        set(value) {
            textFieldState.edit { selection = value }
            selectionChanged()
        }

    private val formatting by derivedStateOf {
        var bold = true
        var italic = true
        var underline = true
        var color: Color? = null
        fun include(style: SpanStyle) {
            bold = bold && (style.fontWeight ?: FontWeight.Normal) >= FontWeight.Bold
            italic = italic && style.fontStyle == FontStyle.Italic
            underline = underline && style.textDecoration?.contains(TextDecoration.Underline) == true
            color = if (color == null || color == style.color) style.color else Color.Unspecified
        }
        if (selection.collapsed) include(insertionStyle())
        else forEachStyleRun(selection, textFieldState.textStyles.getSpanStyles(selection)) { _, _, style -> include(style) }
        Formatting(bold, italic, underline, color ?: Color.Unspecified)
    }

    private data class Formatting(val bold: Boolean, val italic: Boolean, val underline: Boolean, val color: Color)

    val isBold: Boolean get() = formatting.bold
    val isItalic: Boolean get() = formatting.italic
    val isUnderline: Boolean get() = formatting.underline
    /** Foreground color at the caret or across the selection. Mixed or default colors are unspecified. */
    val textColor: Color get() = formatting.color

    /** Applies a foreground color to the selection or to subsequent typing at the caret. */
    fun setTextColor(color: Color) = applyFormatting(SpanStyle(color = color))

    /** Returns the current UI text and its character and paragraph styles. */
    fun toAnnotatedString(): AnnotatedString {
        val range = TextRange(0, text.length)
        return AnnotatedString(
            text = text.toString(),
            spanStyles = textFieldState.textStyles.getSpanStyles(range),
            paragraphStyles = textFieldState.textStyles.getParagraphStyles(range)
        )
    }

    /** Applies one undoable edit. Selection-only changes do not create history entries. */
    fun edit(block: TextFieldBuffer.() -> Unit) {
        val before = if (historyPolicy.maxEntries > 0) capture() else null
        textFieldState.edit(block)
        pendingStyle = null
        pendingSelection = null
        if (before == null) {
            revision++
        } else {
            val after = capture()
            if (!before.sameContent(after)) {
                addUndo(before, after.selection)
                revision++
            }
        }
        lastInputKind = null
        lastEditWasInput = false
        nextInputIsAtomic = false
        textFieldState.undoState.clearHistory()
    }

    fun toggleBold() = applyFormatting(SpanStyle(fontWeight = if (isBold) FontWeight.Normal else FontWeight.Bold))

    fun toggleItalic() = applyFormatting(SpanStyle(fontStyle = if (isItalic) FontStyle.Normal else FontStyle.Italic))

    fun toggleUnderline() {
        val underline = !isUnderline
        if (selection.collapsed) {
            val decoration = insertionStyle().textDecoration ?: TextDecoration.None
            applyFormatting(SpanStyle(textDecoration = decoration.withUnderline(underline)))
        } else {
            // Preserve other decorations, including strikethrough, in each selected run.
            edit {
                transformStyles(TextRange(selection.min, selection.max)) { style ->
                    style.copy(textDecoration = (style.textDecoration ?: TextDecoration.None).withUnderline(underline))
                }
            }
        }
    }

    fun undo() = restoreLast(undoHistory, redoHistory)

    fun redo() = restoreLast(redoHistory, undoHistory)

    private fun restoreLast(from: HistoryStack, to: HistoryStack) {
        val entry = from.removeLastOrNull() ?: return
        to.add(Edit(capture().copy(selection = entry.resultSelection), entry.target.selection), historyPolicy)
        restore(entry.target)
        revision++
        updateHistoryAvailability()
    }

    /** Cancel a delayed read if an edit, history operation, or selection change invalidates its target. */
    internal suspend fun pastePlainText(read: suspend () -> String?, isEditable: () -> Boolean): Boolean {
        val target = selection
        val version = revision
        val value = read()
        if (value.isNullOrEmpty() || version != revision || selection != target || !isEditable()) return false
        val style = pendingStyle.takeIf { pendingSelection == target }
        edit {
            replace(target.min, target.max, value)
            val end = target.min + value.length
            if (style != null) applyStyle(TextRange(target.min, end), style)
            selection = TextRange(end)
        }
        return true
    }

    /** Clears both histories without changing the document or selection. */
    fun clearHistory() {
        undoHistory.clear()
        redoHistory.clear()
        lastInputKind = null
        lastEditWasInput = false
        nextInputIsAtomic = false
        textFieldState.undoState.clearHistory()
        updateHistoryAvailability()
    }

    internal fun selectionChanged() {
        if (pendingSelection != selection) {
            pendingStyle = null
            pendingSelection = null
        }
        if (lastInputSelection != selection) {
            lastInputKind = null
            lastEditWasInput = false
        }
    }

    internal fun breakUndoGroup() {
        lastInputKind = null
        lastEditWasInput = false
        nextInputIsAtomic = true
    }

    internal val inputTransformation = InputTransformation {
        val hasTextChanges = changes.changeCount > 0
        val typingStyle = pendingStyle.takeIf { pendingSelection == originalSelection }
        val kind = if (hasTextChanges && !nextInputIsAtomic) inputKind() else null
        if (hasTextChanges && typingStyle != null) {
            for (index in 0 until changes.changeCount) {
                val range = changes.getRange(index)
                if (!range.collapsed) applyStyle(range, typingStyle)
            }
        }
        // Replacing selected text with itself can still change its formatting. Only this
        // ambiguous case needs a full comparison to avoid clearing redo for a no-op.
        if (hasTextChanges && this@RichEditBoxState.historyPolicy.maxEntries > 0 && hasIdenticalReplacements() && capture().sameContent(snapshot())) {
            lastEditWasInput = false
            nextInputIsAtomic = false
            return@InputTransformation
        }
        if (hasTextChanges) {
            val merge = !nextInputIsAtomic && lastEditWasInput && undoHistory.entries.isNotEmpty() &&
                originalSelection == lastInputSelection &&
                (textFieldState.composition != null ||
                    (kind != null && kind == lastInputKind && lastInputTime.elapsedNow().inWholeMilliseconds < 1000))
            // The committed field still contains the pre-input value during a transformation.
            // Keep one checkpoint per undo group and capture its inverse only on undo/redo.
            if (merge) {
                undoHistory.entries.last().resultSelection = selection
            } else if (this@RichEditBoxState.historyPolicy.maxEntries > 0) {
                addUndo(capture(), selection)
            }
            revision++
            lastInputSelection = selection
            lastInputKind = kind
            lastEditWasInput = true
            nextInputIsAtomic = false
            lastInputTime = TimeSource.Monotonic.markNow()
            pendingStyle = typingStyle
            pendingSelection = if (typingStyle != null) selection else null
        } else if (selection != originalSelection) {
            pendingStyle = null
            pendingSelection = null
            lastInputKind = null
            lastEditWasInput = false
        }
    }

    private fun applyFormatting(style: SpanStyle) {
        if (selection.collapsed) {
            revision++
            pendingStyle = insertionStyle().merge(style)
            pendingSelection = selection
            lastInputKind = null
            lastEditWasInput = false
        } else {
            edit { applyStyle(TextRange(selection.min, selection.max), style) }
        }
    }

    private fun insertionStyle(): SpanStyle {
        pendingStyle?.takeIf { pendingSelection == selection }?.let { return it }
        if (text.isEmpty()) return SpanStyle()
        // Probe the preceding UTF-16 code unit only for styles; never split or edit a character.
        val index = (selection.min - 1).coerceAtLeast(0)
        return textFieldState.textStyles.getSpanStyles(TextRange(index, index + 1))
            .fold(SpanStyle()) { style, span -> style.merge(span.item) }
    }

    private fun capture(): Document = textFieldState.toTextFieldBuffer().snapshot()

    private fun TextFieldBuffer.snapshot(): Document {
        val range = TextRange(0, length)
        return Document(
            text = toString(),
            selection = selection,
            spans = getSpanStyles(range).map { Style(it.spanStyle, it.textRange, it.expandPolicy) },
            paragraphs = getParagraphStyles(range).map { Style(it.paragraphStyle, it.textRange, it.expandPolicy) }
        )
    }

    private fun restore(value: Document) {
        textFieldState.edit {
            val range = TextRange(0, length)
            getSpanStyles(range).forEach { removeStyle(it) }
            getParagraphStyles(range).forEach { removeStyle(it) }
            replace(0, length, value.text)
            value.spans.forEach { addStyle(it.value, it.range, it.expandPolicy) }
            value.paragraphs.forEach { addStyle(it.value, it.range, it.expandPolicy) }
            selection = value.selection
        }
        pendingStyle = null
        pendingSelection = null
        lastInputKind = null
        lastEditWasInput = false
        nextInputIsAtomic = false
        textFieldState.undoState.clearHistory()
    }

    private fun addUndo(before: Document, resultSelection: TextRange) {
        undoHistory.add(Edit(before, resultSelection), historyPolicy)
        redoHistory.clear()
        updateHistoryAvailability()
    }

    private class HistoryStack {
        private val checkpoints = ArrayDeque<Edit>()
        val entries: List<Edit> get() = checkpoints
        private var bytes = 0L

        fun add(entry: Edit, policy: RichEditHistoryPolicy) {
            checkpoints.addLast(entry)
            bytes += entry.target.estimatedBytes
            trim(policy)
        }

        fun trim(policy: RichEditHistoryPolicy) {
            while (checkpoints.size > policy.maxEntries ||
                (checkpoints.size > 1 && bytes > policy.maxEstimatedBytes)) {
                bytes -= checkpoints.removeFirst().target.estimatedBytes
            }
        }

        fun removeLastOrNull(): Edit? = checkpoints.removeLastOrNull()?.also { bytes -= it.target.estimatedBytes }

        fun clear() {
            checkpoints.clear()
            bytes = 0
        }
    }

    private fun updateHistoryAvailability() {
        canUndo = undoHistory.entries.isNotEmpty()
        canRedo = redoHistory.entries.isNotEmpty()
    }

    private fun TextFieldBuffer.hasIdenticalReplacements(): Boolean {
        if (length != originalText.length) return false
        return (0 until changes.changeCount).all { index ->
            val old = changes.getOriginalRange(index)
            val new = changes.getRange(index)
            old.length == new.length && originalText.regionMatches(old.min, asCharSequence(), new.min, old.length)
        }
    }

    private fun TextFieldBuffer.inputKind(): InputKind? {
        if (changes.changeCount != 1 || !originalSelection.collapsed || !selection.collapsed) return null
        val old = changes.getOriginalRange(0)
        val new = changes.getRange(0)
        if (old.collapsed && new.length in 1..2 && new.min == originalSelection.end &&
            asCharSequence().subSequence(new.min, new.max).none { it == '\n' || it == '\r' }) return InputKind.Insert
        if (new.collapsed && old.length in 1..2) {
            if (old.max == originalSelection.end) return InputKind.Backspace
            if (old.min == originalSelection.end) return InputKind.Delete
        }
        return null
    }

    private data class Style<T>(val value: T, val range: TextRange, val expandPolicy: ExpandPolicy)
    private data class Document(
        val text: String,
        val selection: TextRange,
        val spans: List<Style<SpanStyle>>,
        val paragraphs: List<Style<ParagraphStyle>>
    ) {
        val estimatedBytes: Long get() = 64L + 2L * text.length + 256L * (spans.size.toLong() + paragraphs.size)

        fun sameContent(other: Document): Boolean =
            text == other.text && spans == other.spans && paragraphs == other.paragraphs
    }
    private class Edit(val target: Document, var resultSelection: TextRange)
    private enum class InputKind { Insert, Backspace, Delete }

    companion object {
        // Stable persisted order for the two bits of range expansion behavior.
        private val policies = listOf(ExpandPolicy.InsideOnly, ExpandPolicy.AtStart, ExpandPolicy.AtEnd, ExpandPolicy.AtBoth)

        // Compose exposes this saver with a star-projected payload. Only pass its own output back to it.
        @Suppress("UNCHECKED_CAST")
        private val annotatedSaver = AnnotatedString.Saver as Saver<AnnotatedString, Any>

        /**
         * Saves the document, directional selection, pending typing style and recent undo/redo.
         * Style serialization follows [AnnotatedString.Saver]. Each history stack saves its newest
         * consecutive checkpoints within an estimated size budget. The current document is saved in full.
         */
        val Saver: Saver<RichEditBoxState, Any> = Saver(
            save = { state ->
                arrayListOf(
                    saveDocument(state.capture()), savePending(state),
                    saveHistory(state.undoHistory.entries), saveHistory(state.redoHistory.entries),
                    state.historyPolicy.maxEntries, state.historyPolicy.maxEstimatedBytes
                )
            },
            restore = { payload ->
                val fields = payload as List<*>
                val policy = if (fields.size >= 6) {
                    RichEditHistoryPolicy(fields[4] as Int, fields[5] as Long)
                } else {
                    RichEditHistoryPolicy.Default
                }
                RichEditBoxState(historyPolicy = policy).apply {
                    restore(restoreDocument(fields[0]))
                    restorePending(fields[1])
                    restoreHistory(fields[2], undoHistory)
                    restoreHistory(fields[3], redoHistory)
                    updateHistoryAvailability()
                }
            }
        )

        internal fun documentReferenceSaver(key: String, document: AnnotatedString): Saver<RichEditBoxState, Any> =
            Saver(
                save = { state -> arrayListOf(key, state.selection.start, state.selection.end, savePending(state)) },
                restore = { payload ->
                    val fields = payload as List<*>
                    if (fields[0] != key) null else RichEditBoxState(document).apply {
                        selection = TextRange(
                            (fields[1] as Int).coerceIn(0, text.length),
                            (fields[2] as Int).coerceIn(0, text.length)
                        )
                        restorePending(fields[3])
                    }
                }
            )

        private fun SaverScope.savePending(state: RichEditBoxState): Any? {
            val pending = state.pendingStyle?.takeIf { state.pendingSelection == state.selection } ?: return null
            return with(annotatedSaver) { save(AnnotatedString(" ", pending)) }
        }

        private fun RichEditBoxState.restorePending(saved: Any?) {
            if (saved != null) {
                pendingStyle = annotatedSaver.restore(saved)!!.spanStyles.single().item
                pendingSelection = selection
            }
        }

        private fun SaverScope.saveDocument(doc: Document): Any {
            val annotated = AnnotatedString(doc.text, doc.spans.map { it.annotation() }, doc.paragraphs.map { it.annotation() })
            return arrayListOf(
                with(annotatedSaver) { save(annotated) }, doc.selection.start, doc.selection.end,
                doc.spans.map { policies.indexOf(it.expandPolicy) }.toIntArray(),
                doc.paragraphs.map { policies.indexOf(it.expandPolicy) }.toIntArray()
            )
        }

        private fun <T> Style<T>.annotation() = AnnotatedString.Range(value, range.start, range.end)

        private fun restoreDocument(payload: Any?): Document {
            val fields = payload as List<*>
            val annotated = annotatedSaver.restore(fields[0]!!)!!
            return Document(
                annotated.text, TextRange(fields[1] as Int, fields[2] as Int),
                restoreStyles(annotated.spanStyles, fields[3] as IntArray),
                restoreStyles(annotated.paragraphStyles, fields[4] as IntArray)
            )
        }

        private fun <T> restoreStyles(ranges: List<AnnotatedString.Range<T>>, flags: IntArray) =
            ranges.mapIndexed { i, range -> Style(range.item, TextRange(range.start, range.end), policies[flags[i]]) }

        private fun SaverScope.saveHistory(history: List<Edit>): List<Any> {
            val saved = ArrayList<Any>()
            // Estimate cost in UTF-16 code units, allowing for each serialized style's metadata.
            var remaining = 32 * 1024
            for (entry in history.asReversed()) {
                val doc = entry.target
                val cost = doc.text.length.toLong() + 128L * (doc.spans.size + doc.paragraphs.size) + 16
                if (cost > remaining) break
                remaining -= cost.toInt()
                saved.add(arrayListOf(saveDocument(doc), entry.resultSelection.start, entry.resultSelection.end))
            }
            return saved
        }

        private fun RichEditBoxState.restoreHistory(payload: Any?, history: HistoryStack) {
            for (entry in (payload as List<*>).asReversed()) {
                val fields = entry as List<*>
                history.add(Edit(restoreDocument(fields[0]), TextRange(fields[1] as Int, fields[2] as Int)), historyPolicy)
            }
        }
    }
}

private fun TextFieldBuffer.applyStyle(range: TextRange, style: SpanStyle) {
    transformStyles(range) { it.merge(style) }
}

/** Rewrite the affected spans once, retaining style precedence and ranges outside the edit. */
private fun TextFieldBuffer.transformStyles(range: TextRange, transform: (SpanStyle) -> SpanStyle) {
    val tracked = getSpanStyles(range)
    tracked.singleOrNull()?.let {
        if (it.textRange.min <= range.min && it.textRange.max >= range.max && transform(it.spanStyle) == it.spanStyle) return
    }
    val spans = tracked.map { AnnotatedString.Range(it.spanStyle, it.textRange.min, it.textRange.max) }
    // Reinsert the outside fragments in their original order.
    val policies = tracked.map { it.expandPolicy }
    tracked.forEach { removeStyle(it) }
    spans.forEachIndexed { i, span ->
        if (span.start < range.min) addStyle(span.item, TextRange(span.start, range.min), policies[i])
        if (span.end > range.max) addStyle(span.item, TextRange(range.max, span.end), policies[i])
    }
    var previous: SpanStyle? = null
    var start = range.min
    forEachStyleRun(range, spans) { position, _, style ->
        val next = transform(style)
        if (next != previous) {
            previous?.let { addStyle(it, TextRange(start, position), ExpandPolicy.AtEnd) }
            previous = next
            start = position
        }
    }
    previous?.let { addStyle(it, TextRange(start, range.max), ExpandPolicy.AtEnd) }
}

private data class StyleBoundary(val position: Int, val index: Int, val entering: Boolean)

/** Sweep boundaries with a merge tree ordered by original span index. Each event updates O(log n) nodes. */
private inline fun forEachStyleRun(
    range: TextRange,
    spans: List<AnnotatedString.Range<SpanStyle>>,
    visit: (Int, Int, SpanStyle) -> Unit
) {
    val empty = SpanStyle()
    val events = ArrayList<StyleBoundary>(spans.size * 2)
    spans.forEachIndexed { index, span ->
        val start = maxOf(span.start, range.min)
        val end = minOf(span.end, range.max)
        if (start < end) {
            events.add(StyleBoundary(start, index, true))
            events.add(StyleBoundary(end, index, false))
        }
    }
    events.sortBy { it.position }
    var leaves = 1
    while (leaves < spans.size) leaves *= 2
    val tree = arrayOfNulls<SpanStyle>(2 * leaves)
    var position = range.min
    for (event in events) {
        if (position < event.position) visit(position, event.position, tree[1] ?: empty)
        position = event.position
        var node = leaves + event.index
        tree[node] = if (event.entering) spans[event.index].item else null
        while (node > 1) {
            node /= 2
            val left = tree[2 * node]
            val right = tree[2 * node + 1]
            tree[node] = if (left != null && right != null) left.merge(right) else left ?: right
        }
    }
    if (position < range.max) visit(position, range.max, tree[1] ?: empty)
}

private fun TextDecoration.withUnderline(enabled: Boolean): TextDecoration = when {
    enabled -> this + TextDecoration.Underline
    contains(TextDecoration.LineThrough) -> TextDecoration.LineThrough
    else -> TextDecoration.None
}
