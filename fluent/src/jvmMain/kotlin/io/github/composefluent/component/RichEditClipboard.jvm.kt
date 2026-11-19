@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package io.github.composefluent.component

import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.LocalTextContextMenu
import androidx.compose.foundation.text.TextContextMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.asAwtTransferable
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
internal actual fun rememberPlainTextPaste(): (suspend () -> String?)? {
    val clipboard = LocalClipboard.current
    if (!LocalClipboardManager.current.hasText()) return null
    return {
        withContext(Dispatchers.IO) {
            val data = clipboard.getClipEntry()?.asAwtTransferable
            try {
                if (data?.isDataFlavorSupported(DataFlavor.stringFlavor) == true)
                    data.getTransferData(DataFlavor.stringFlavor) as? String else null
            } catch (_: IOException) {
                null
            } catch (_: UnsupportedFlavorException) {
                null
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private object RichEditDesktopContext : TextContextMenu {
    @Composable
    override fun Area(textManager: TextContextMenu.TextManager, state: ContextMenuState, content: @Composable () -> Unit) {
        content()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal actual fun RichEditFieldContext(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalTextContextMenu provides RichEditDesktopContext, content = content)
}

internal actual val usesCommandKey: Boolean = org.jetbrains.skiko.hostOs.isMacOS
