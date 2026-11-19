package io.github.composefluent.component

import androidx.compose.runtime.Composable

@Composable
internal expect fun rememberPlainTextPaste(): (suspend () -> String?)?

@Composable
internal expect fun RichEditFieldContext(content: @Composable () -> Unit)

internal expect val usesCommandKey: Boolean
