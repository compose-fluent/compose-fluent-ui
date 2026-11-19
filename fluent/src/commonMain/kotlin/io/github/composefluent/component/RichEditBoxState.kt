@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package io.github.composefluent.component

import androidx.compose.foundation.text.input.ExpandPolicy
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.toTextFieldBuffer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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

/** Remembers and restores an editor state with [RichEditBoxState.Saver]. Changes to [initialText] do not replace an existing document. */
@Composable
fun rememberRichEditBoxState(initialText: String = ""): RichEditBoxState =
    rememberSaveable(saver = RichEditBoxState.Saver) { RichEditBoxState(initialText) }

/** Remembers and restores an editor state initialized with character and paragraph styles. */
@Composable
fun rememberRichEditBoxState(initialText: AnnotatedString): RichEditBoxState =
    rememberSaveable(saver = RichEditBoxState.Saver) { RichEditBoxState(initialText) }

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
class RichEditBoxState(initialText: AnnotatedString) {
    constructor(initialText: String = "") : this(AnnotatedString(initialText))

    internal val textFieldState = TextFieldState(initialText.text)

    private var pendingStyle by mutableStateOf<SpanStyle?>(null)
    private var pendingSelection: TextRange? = null
    private val undoHistory = ArrayDeque<Edit>()
    private val redoHistory = ArrayDeque<Edit>()
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

    /** True when the entire selection (or the next insertion) has this formatting. */
    val isBold: Boolean get() = hasStyle { (it.fontWeight ?: FontWeight.Normal) >= FontWeight.Bold }
    val isItalic: Boolean get() = hasStyle { it.fontStyle == FontStyle.Italic }
    val isUnderline: Boolean get() = hasStyle { it.textDecoration?.contains(TextDecoration.Underline) == true }

    /** Foreground color at the caret or across the selection. Mixed or default colors are unspecified. */
    val textColor: Color
        get() = if (selection.collapsed) insertionStyle().color else {
            val runs = styleRuns(selection)
            val first = runs.first().second.color
            if (runs.all { it.second.color == first }) first else Color.Unspecified
        }

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
        val before = capture()
        textFieldState.edit(block)
        pendingStyle = null
        pendingSelection = null
        val after = capture()
        if (!before.sameContent(after)) {
            addUndo(before, after.selection)
            revision++
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
            val runs = styleRuns(selection)
            edit {
                runs.forEach { (range, style) ->
                    applyStyle(
                        range,
                        SpanStyle(textDecoration = (style.textDecoration ?: TextDecoration.None).withUnderline(underline))
                    )
                }
            }
        }
    }

    fun undo() = restoreLast(undoHistory, redoHistory)

    fun redo() = restoreLast(redoHistory, undoHistory)

    private fun restoreLast(from: ArrayDeque<Edit>, to: ArrayDeque<Edit>) {
        val entry = from.removeLastOrNull() ?: return
        to.addLast(Edit(capture().copy(selection = entry.resultSelection), entry.target.selection))
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
        if (hasTextChanges && hasIdenticalReplacements() && capture().sameContent(snapshot())) {
            lastEditWasInput = false
            nextInputIsAtomic = false
            return@InputTransformation
        }
        if (hasTextChanges) {
            val merge = !nextInputIsAtomic && lastEditWasInput && undoHistory.isNotEmpty() &&
                originalSelection == lastInputSelection &&
                (textFieldState.composition != null ||
                    (kind != null && kind == lastInputKind && lastInputTime.elapsedNow().inWholeMilliseconds < 1000))
            // The committed field still contains the pre-input value during a transformation.
            // Keep one checkpoint per undo group and capture its inverse only on undo/redo.
            if (merge) {
                undoHistory.last().resultSelection = selection
            } else {
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
        return styleRuns(TextRange(index, index + 1)).first().second
    }

    private fun hasStyle(predicate: (SpanStyle) -> Boolean): Boolean =
        if (selection.collapsed) predicate(insertionStyle())
        else styleRuns(selection).all { predicate(it.second) }

    private fun styleRuns(range: TextRange): List<Pair<TextRange, SpanStyle>> {
        val styles = textFieldState.textStyles.getSpanStyles(range)
        val boundaries = (listOf(range.min, range.max) + styles.flatMap {
            listOf(it.start.coerceIn(range.min, range.max), it.end.coerceIn(range.min, range.max))
        }).distinct().sorted()
        return boundaries.zipWithNext { start, end ->
            TextRange(start, end) to styles.fold(SpanStyle()) { result, span ->
                if (span.start <= start && span.end >= end) result.merge(span.item) else result
            }
        }
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
        undoHistory.addLast(Edit(before, resultSelection))
        if (undoHistory.size > 100) undoHistory.removeFirst()
        redoHistory.clear()
        updateHistoryAvailability()
    }

    private fun updateHistoryAvailability() {
        canUndo = undoHistory.isNotEmpty()
        canRedo = redoHistory.isNotEmpty()
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
                val pending = state.pendingStyle?.takeIf { state.pendingSelection == state.selection }
                val savedPending = pending?.let { with(annotatedSaver) { save(AnnotatedString(" ", it)) } }
                arrayListOf(
                    saveDocument(state.capture()), savedPending,
                    saveHistory(state.undoHistory), saveHistory(state.redoHistory)
                )
            },
            restore = { payload ->
                val fields = payload as List<*>
                RichEditBoxState().apply {
                    restore(restoreDocument(fields[0]))
                    fields[1]?.let { pending ->
                        pendingStyle = annotatedSaver.restore(pending)!!.spanStyles.single().item
                        pendingSelection = selection
                    }
                    restoreHistory(fields[2], undoHistory)
                    restoreHistory(fields[3], redoHistory)
                    updateHistoryAvailability()
                }
            }
        )

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

        private fun SaverScope.saveHistory(history: ArrayDeque<Edit>): List<Any> {
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

        private fun restoreHistory(payload: Any?, history: ArrayDeque<Edit>) {
            for (entry in (payload as List<*>).asReversed()) {
                val fields = entry as List<*>
                history.addLast(Edit(restoreDocument(fields[0]), TextRange(fields[1] as Int, fields[2] as Int)))
            }
        }
    }
}

/** Flatten only the affected character runs, preserving unrelated style properties and ordering. */
private fun TextFieldBuffer.applyStyle(range: TextRange, style: SpanStyle) {
    val existing = getSpanStyles(range).map { Triple(it.spanStyle, it.textRange, it.expandPolicy) }
    // Continuing to type with the same pending style should extend a run, not create one per key.
    existing.singleOrNull()?.let { (value, old, _) ->
        if (old.min <= range.min && old.max >= range.max && value.merge(style) == value) return
    }
    val boundaries = (listOf(range.min, range.max) + existing.flatMap {
        listOf(it.second.min.coerceIn(range.min, range.max), it.second.max.coerceIn(range.min, range.max))
    }).distinct().sorted()
    getSpanStyles(range).forEach { removeStyle(it) }
    existing.forEach { (value, old, policy) ->
        if (old.min < range.min) addStyle(value, TextRange(old.min, range.min), policy)
        if (old.max > range.max) addStyle(value, TextRange(range.max, old.max), policy)
    }
    boundaries.zipWithNext { start, end ->
        val merged = existing.fold(SpanStyle()) { result, (value, old, _) ->
            if (old.min <= start && old.max >= end) result.merge(value) else result
        }.merge(style)
        addStyle(merged, TextRange(start, end), ExpandPolicy.AtEnd)
    }
}

private fun TextDecoration.withUnderline(enabled: Boolean): TextDecoration = when {
    enabled -> this + TextDecoration.Underline
    contains(TextDecoration.LineThrough) -> TextDecoration.LineThrough
    else -> TextDecoration.None
}
