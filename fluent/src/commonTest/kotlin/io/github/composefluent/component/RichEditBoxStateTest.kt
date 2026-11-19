package io.github.composefluent.component

import androidx.compose.foundation.text.input.ExpandPolicy
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RichEditBoxStateTest {
    @Test
    fun partialBoldTogglePreservesUnselectedFormatting() {
        val state = RichEditBoxState(AnnotatedString("abcdef", SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)))
        state.selection = TextRange(4, 2)
        state.toggleBold()
        assertEquals(FontWeight.Bold, state.styleAt(0).fontWeight)
        assertEquals(FontWeight.Normal, state.styleAt(2).fontWeight)
        assertEquals(FontWeight.Bold, state.styleAt(4).fontWeight)
        repeat(6) { assertEquals(Color.Red, state.styleAt(it).color) }
        state.undo()
        repeat(6) { assertEquals(FontWeight.Bold, state.styleAt(it).fontWeight) }
        assertEquals(TextRange(4, 2), state.selection)
        state.redo()
        assertEquals(FontWeight.Normal, state.styleAt(2).fontWeight)
    }

    @Test
    fun mixedSelectionBecomesBoldThenNormal() {
        val state = RichEditBoxState(AnnotatedString("abcdef", listOf(AnnotatedString.Range(SpanStyle(fontWeight = FontWeight.Bold), 0, 3))))
        state.selection = TextRange(0, 6)
        assertFalse(state.isBold)
        state.toggleBold()
        assertTrue(state.isBold)
        state.toggleBold()
        assertFalse(state.isBold)
        repeat(6) { assertEquals(FontWeight.Normal, state.styleAt(it).fontWeight) }
    }

    @Test
    fun underlineTogglePreservesStrikethroughAndItalic() {
        val state = RichEditBoxState(AnnotatedString("text", SpanStyle(
            textDecoration = TextDecoration.Underline + TextDecoration.LineThrough,
            fontStyle = FontStyle.Italic
        )))
        state.selection = TextRange(0, 4)
        state.toggleUnderline()
        assertFalse(state.isUnderline)
        assertTrue(state.isItalic)
        assertEquals(TextDecoration.LineThrough, state.styleAt(1).textDecoration)
        state.undo()
        assertTrue(state.isUnderline)
        assertTrue(state.isItalic)
    }

    @Test
    fun deletingStyledDocumentRestoresStylesParagraphsAndSelection() {
        val initial = AnnotatedString(
            "one\ntwo",
            spanStyles = listOf(AnnotatedString.Range(SpanStyle(fontWeight = FontWeight.Bold), 0, 3)),
            paragraphStyles = listOf(AnnotatedString.Range(ParagraphStyle(textAlign = TextAlign.Center), 0, 3))
        )
        val state = RichEditBoxState(initial)
        state.selection = TextRange(7, 0)
        state.userEdit { replace(0, length, ""); selection = TextRange.Zero }
        assertEquals("", state.text.toString())
        state.undo()
        assertEquals(initial, state.toAnnotatedString())
        assertEquals(TextRange(7, 0), state.selection)
        state.redo()
        assertEquals("", state.text.toString())
        state.undo()
        assertEquals(initial, state.toAnnotatedString())
    }

    @Test
    fun textFormattingAndDeletionShareOneHistory() {
        val state = RichEditBoxState()
        state.type("hello")
        state.selection = TextRange(0, 5)
        state.toggleBold()
        state.userEdit { replace(0, length, ""); selection = TextRange.Zero }
        state.undo()
        assertEquals("hello", state.text.toString())
        assertTrue(state.isBold)
        state.undo()
        assertFalse(state.isBold)
        state.undo()
        assertEquals("", state.text.toString())
        assertFalse(state.canUndo)
        repeat(3) { state.redo() }
        assertEquals("", state.text.toString())
        assertFalse(state.canRedo)
    }

    @Test
    fun pendingFormattingAppliesToTypedTextAndSurvivesRedo() {
        val state = RichEditBoxState()
        state.toggleBold()
        state.toggleItalic()
        state.type("a")
        state.type("b")
        assertEquals(1, state.toAnnotatedString().spanStyles.size)
        assertEquals(FontWeight.Bold, state.styleAt(1).fontWeight)
        assertEquals(FontStyle.Italic, state.styleAt(1).fontStyle)
        state.undo()
        assertEquals("", state.text.toString())
        state.redo()
        assertEquals("ab", state.text.toString())
        assertEquals(FontWeight.Bold, state.styleAt(1).fontWeight)
    }

    @Test
    fun turningBoldOffAtCursorDoesNotChangeExistingText() {
        val state = RichEditBoxState(AnnotatedString("bold", SpanStyle(fontWeight = FontWeight.Bold)))
        state.toggleBold()
        state.type("plain")
        assertEquals(FontWeight.Bold, state.styleAt(0).fontWeight)
        assertEquals(FontWeight.Normal, state.styleAt(4).fontWeight)
        state.undo()
        assertEquals("bold", state.text.toString())
        assertEquals(FontWeight.Bold, state.styleAt(3).fontWeight)
        state.redo()
        assertEquals(FontWeight.Normal, state.styleAt(4).fontWeight)
    }

    @Test
    fun movingCursorClearsPendingFormatting() {
        val state = RichEditBoxState("ab")
        state.toggleBold()
        state.selection = TextRange.Zero
        state.type("x")
        assertEquals("xab", state.text.toString())
        assertFalse(state.isBold)
        assertEquals(null, state.styleAt(0).fontWeight)
    }

    @Test
    fun consecutiveTypingMergesButCursorMovementStartsNewGroup() {
        val state = RichEditBoxState()
        state.type("a")
        state.type("b")
        state.selection = TextRange.Zero
        state.type("x")
        state.undo()
        assertEquals("ab", state.text.toString())
        state.undo()
        assertEquals("", state.text.toString())
    }

    @Test
    fun explicitPasteBoundaryDoesNotMergeWithTyping() {
        val state = RichEditBoxState()
        state.type("a")
        state.breakUndoGroup()
        state.type("x")
        state.type("b")
        state.undo()
        assertEquals("ax", state.text.toString())
        state.undo()
        assertEquals("a", state.text.toString())
    }

    @Test
    fun newEditAfterUndoClearsRedo() {
        val state = RichEditBoxState("hello")
        state.selection = TextRange(0, 5)
        state.toggleBold()
        state.undo()
        assertTrue(state.canRedo)
        state.toggleItalic()
        assertFalse(state.canRedo)
        state.redo()
        assertTrue(state.isItalic)
        assertFalse(state.isBold)
    }

    @Test
    fun historyRestoresExpansionPolicy() {
        val state = RichEditBoxState("ab")
        state.edit { addStyle(SpanStyle(fontWeight = FontWeight.Bold), TextRange(0, 2), ExpandPolicy.InsideOnly) }
        state.edit { replace(0, length, "") }
        state.undo()
        state.selection = TextRange(2)
        state.type("c")
        assertEquals(null, state.styleAt(2).fontWeight)
    }

    @Test
    fun emojiAndCjkTextRoundTripWithFormatting() {
        val state = RichEditBoxState("你好🌍")
        state.selection = TextRange(2, 4)
        state.toggleUnderline()
        val before = state.toAnnotatedString()
        state.userEdit { replace(2, 4, "世界"); selection = TextRange(4) }
        state.undo()
        assertEquals(before, state.toAnnotatedString())
        assertEquals(TextRange(2, 4), state.selection)
        state.redo()
        assertEquals("你好世界", state.text.toString())
    }

    @Test
    fun replacingPlainSelectionWithItselfPreservesRedo() {
        val state = RichEditBoxState("abc")
        state.type("d")
        state.undo()
        state.selection = TextRange(0, 3)
        state.type("abc")
        assertTrue(state.canRedo)
        assertFalse(state.canUndo)
    }

    @Test
    fun groupedTypingInLongDocumentRestoresStylesAndEditSelection() {
        val initial = AnnotatedString("abc".repeat(20000), SpanStyle(color = Color.Blue))
        val state = RichEditBoxState(initial)
        state.selection = TextRange(1000)
        state.type("x")
        state.type("y")
        state.type("z")
        val typed = state.toAnnotatedString()
        state.selection = TextRange(0)
        state.undo()
        assertEquals(initial, state.toAnnotatedString())
        assertEquals(TextRange(1000), state.selection)
        assertFalse(state.canUndo)
        state.redo()
        assertEquals(typed, state.toAnnotatedString())
        assertEquals(TextRange(1003), state.selection)
    }

    @Test
    fun clearingHistoryKeepsDocument() {
        val state = RichEditBoxState("abc")
        state.selection = TextRange(0, 3)
        state.toggleBold()
        val before = state.toAnnotatedString()
        state.clearHistory()
        assertFalse(state.canUndo)
        assertFalse(state.canRedo)
        state.undo()
        assertEquals(before, state.toAnnotatedString())
    }

    // Exercise the component's input transformation on a real Compose edit buffer, including
    // originalSelection, change tracking and style expansion, without substituting programmatic edit.
    private fun RichEditBoxState.userEdit(block: TextFieldBuffer.() -> Unit) {
        textFieldState.edit {
            block()
            with(inputTransformation) { transformInput() }
        }
    }

    private fun RichEditBoxState.type(value: String) = userEdit {
        val start = selection.min
        replace(start, selection.max, value)
        selection = TextRange(start + value.length)
    }

    private fun RichEditBoxState.styleAt(index: Int): SpanStyle =
        toAnnotatedString().spanStyles.fold(SpanStyle()) { result, span ->
            if (index >= span.start && index < span.end) result.merge(span.item) else result
        }
}
