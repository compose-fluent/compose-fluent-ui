package io.github.composefluent.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextRange
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RichEditInputTest {
    @Test
    fun delayedPasteRevalidatesItsTarget() = runBlocking {
        val state = RichEditBoxState("abc")
        var writable = true
        suspend fun attempt(change: () -> Unit) {
            val text = CompletableDeferred<String>()
            val result = async(start = CoroutineStart.UNDISPATCHED) {
                state.pastePlainText({ text.await() }, { writable })
            }
            change()
            text.complete("stale")
            assertFalse(result.await())
        }
        attempt { state.edit { replace(0, length, "xyz") } }
        attempt { state.selection = TextRange(1) }
        attempt { writable = false }
        assertEquals("xyz", state.text.toString())
    }

    @Test
    fun menuPasteUsesPendingColorAndHasOneUndoEntry() = runBlocking {
        val state = RichEditBoxState("abc")
        state.setTextColor(Color.Blue)
        assertTrue(state.pastePlainText({ "two words" }, { true }))
        assertEquals("abctwo words", state.text.toString())
        val pasted = state.toAnnotatedString()
        assertTrue(pasted.spanStyles.any { it.start == 3 && it.end == 12 && it.item.color == Color.Blue })
        state.undo()
        assertEquals("abc", state.text.toString())
        assertFalse(state.canUndo)
        state.redo()
        assertEquals(pasted, state.toAnnotatedString())
    }

    @Test
    fun commandAndControlArePlatformSpecific() {
        assertTrue(matchesEditorShortcut(true, true, false, false, Key.B))
        assertFalse(matchesEditorShortcut(true, false, true, false, Key.B))
        assertTrue(matchesEditorShortcut(false, false, true, false, Key.B))
        assertFalse(matchesEditorShortcut(false, true, false, false, Key.B))
        assertTrue(matchesEditorShortcut(true, true, false, true, Key.Z))
        assertFalse(matchesEditorShortcut(true, true, false, true, Key.B))
    }
}
