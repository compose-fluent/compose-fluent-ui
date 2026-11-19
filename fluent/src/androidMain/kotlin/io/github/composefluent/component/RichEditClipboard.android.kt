@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package io.github.composefluent.component

import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager

@Composable
internal actual fun rememberPlainTextPaste(): (suspend () -> String?)? {
    val clipboard = LocalClipboard.current
    if (!LocalClipboardManager.current.hasText()) return null
    return { clipboard.getClipEntry()?.clipData?.getItemAt(0)?.text?.toString() }
}

@Composable
internal actual fun RichEditFieldContext(content: @Composable () -> Unit) = content()

internal actual val usesCommandKey: Boolean = false
