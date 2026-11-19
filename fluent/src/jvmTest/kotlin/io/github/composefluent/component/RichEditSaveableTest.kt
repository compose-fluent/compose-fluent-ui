package io.github.composefluent.component

import androidx.compose.foundation.text.input.ExpandPolicy
import androidx.compose.foundation.text.input.toTextFieldBuffer
import androidx.compose.runtime.AbstractApplier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.runtime.saveable.SaveableStateRegistry
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RichEditSaveableTest {
    @Test
    fun restoresDocumentSelectionRangePolicyAndBothHistoryStacks() {
        var initial = AnnotatedString("abcd")
        val content: @Composable () -> RichEditBoxState = { rememberRichEditBoxState(initial) }
        val saved = withRegistry(null, content) { state ->
            state.edit {
                addStyle(ParagraphStyle(textAlign = TextAlign.Center), TextRange(0, 4), ExpandPolicy.AtBoth)
                addStyle(SpanStyle(color = Color.Blue), TextRange(1, 3), ExpandPolicy.InsideOnly)
                selection = TextRange(3, 1)
            }
            state.toggleBold()
            state.undo()
        }
        initial = AnnotatedString("new initial value")
        withRegistry(saved, content) { state ->
            assertEquals("abcd", state.text.toString())
            assertEquals(TextRange(3, 1), state.selection)
            with(state.textFieldState.toTextFieldBuffer()) {
                assertEquals(ExpandPolicy.InsideOnly, getSpanStyles(TextRange(0, 4)).single().expandPolicy)
                assertEquals(ExpandPolicy.AtBoth, getParagraphStyles(TextRange(0, 4)).single().expandPolicy)
            }
            assertEquals(TextAlign.Center, state.toAnnotatedString().paragraphStyles.single().item.textAlign)
            assertEquals(Color.Blue, state.textColor)
            assertTrue(state.canUndo && state.canRedo)
            state.redo()
            assertTrue(state.isBold)
            state.undo()
            state.undo()
            assertEquals(AnnotatedString("abcd"), state.toAnnotatedString())
            assertFalse(state.canUndo)
        }
    }

    @Test
    fun restoresPendingStyleForNextInsertion() = runBlocking {
        val content: @Composable () -> RichEditBoxState = { rememberRichEditBoxState("abc") }
        val saved = withRegistry(null, content) { state ->
            state.selection = TextRange(1)
            state.setTextColor(Color.Red)
            state.toggleItalic()
        }
        lateinit var restored: RichEditBoxState
        withRegistry(saved, content) { restored = it }
        assertTrue(restored.pastePlainText({ "x" }, { true }))
        assertEquals("axbc", restored.text.toString())
        assertEquals(Color.Red, restored.textColor)
        assertTrue(restored.isItalic)
    }

    @Test
    fun oversizedCheckpointKeepsTheCurrentDocumentAndRecentUndo() {
        val content: @Composable () -> RichEditBoxState = { rememberRichEditBoxState("a".repeat(60000)) }
        val saved = withRegistry(null, content) { state ->
            state.edit { replace(0, length, "new") }
            state.edit { replace(0, length, "b".repeat(60000)) }
        }
        withRegistry(saved, content) { state ->
            assertEquals("b".repeat(60000), state.text.toString())
            state.undo()
            assertEquals("new", state.text.toString())
            assertFalse(state.canUndo)
        }
    }

    @Test
    fun documentReferenceRestoresFromHostAndSavesOnlyEditorMetadata() {
        val key = "draft-1"
        var persisted = AnnotatedString("界".repeat(200000))
        val content: @Composable () -> RichEditBoxState = { rememberRichEditBoxState(key, persisted) }
        val saved = withRegistry(null, content) { state ->
            state.selection = TextRange(10)
            state.toggleItalic()
        }
        assertFalse(saved.toString().contains("界"))
        // A host can restore a newer, shorter revision of the same document.
        persisted = AnnotatedString("新版")
        withRegistry(saved, content) { state ->
            assertEquals(persisted, state.toAnnotatedString())
            assertEquals(TextRange(2), state.selection)
            assertTrue(state.isItalic)
        }
    }

    @Test
    fun restoredEditorUsesCurrentHistoryLimit() {
        var limit = 4
        val content: @Composable () -> RichEditBoxState = {
            rememberRichEditBoxState(historyPolicy = RichEditHistoryPolicy(maxEntries = limit))
        }
        val saved = withRegistry(null, content) { state ->
            repeat(4) { state.edit { replace(length, length, "a") } }
        }
        limit = 1
        withRegistry(saved, content) { state ->
            assertEquals(1, state.historyPolicy.maxEntries)
            state.undo()
            assertEquals("aaa", state.text.toString())
            assertFalse(state.canUndo)
        }
    }

    private fun withRegistry(
        restored: Map<String, List<Any?>>?,
        editor: @Composable () -> RichEditBoxState,
        check: (RichEditBoxState) -> Unit
    ): Map<String, List<Any?>> {
        val registry = SaveableStateRegistry(restored) { true }
        val applier = object : AbstractApplier<Unit>(Unit) {
            override fun insertTopDown(index: Int, instance: Unit) {}
            override fun insertBottomUp(index: Int, instance: Unit) {}
            override fun remove(index: Int, count: Int) {}
            override fun move(from: Int, to: Int, count: Int) {}
            override fun onClear() {}
        }
        val recomposer = Recomposer(EmptyCoroutineContext)
        val composition = Composition(applier, recomposer)
        try {
            lateinit var state: RichEditBoxState
            composition.setContent {
                CompositionLocalProvider(LocalSaveableStateRegistry provides registry) { state = editor() }
            }
            check(state)
            return registry.performSave()
        } finally {
            composition.dispose()
            recomposer.cancel()
        }
    }
}
