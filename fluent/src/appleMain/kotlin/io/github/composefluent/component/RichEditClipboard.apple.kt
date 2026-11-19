package io.github.composefluent.component

import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalClipboardManager

@Composable
internal actual fun rememberPlainTextPaste(): (suspend () -> String?)? {
    val clipboard = LocalClipboardManager.current
    if (!clipboard.hasText()) return null
    return { clipboard.getText()?.text }
}

@Composable
internal actual fun RichEditFieldContext(content: @Composable () -> Unit) = content()

internal actual val usesCommandKey: Boolean = true
