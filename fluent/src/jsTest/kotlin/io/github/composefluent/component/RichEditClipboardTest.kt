package io.github.composefluent.component

import androidx.compose.ui.platform.ClipEntry
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlin.test.Test
import kotlin.test.assertEquals

class RichEditClipboardTest {
    @Test
    fun pasteReadsPlainTextFromBrowserClipboardItem() = MainScope().promise {
        val state = RichEditBoxState("A")
        val entry = ClipEntry.withPlainText("颜色🌍")
        state.pastePlainText({ entry.plainText() }, { true })
        assertEquals("A颜色🌍", state.text.toString())
        state.undo()
        assertEquals("A", state.text.toString())
    }
}
