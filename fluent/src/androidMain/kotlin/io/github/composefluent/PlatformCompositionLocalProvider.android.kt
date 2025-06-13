package io.github.composefluent

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformCompositionLocalProvider(content: @Composable () -> Unit) {
    content()
}