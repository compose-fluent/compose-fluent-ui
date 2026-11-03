package io.github.composefluent.platform

import androidx.compose.ui.input.pointer.PointerEvent

// Compose's UIKit bridge does not expose the scroll recognizer's phase in PointerEvent.
// Do not infer a stepping wheel or macOS-style system momentum from PointerType.Mouse.
internal actual fun PointerEvent.platformScrollInfo(): PlatformScrollInfo = UnknownScroll

private val UnknownScroll = PlatformScrollInfo(ScrollInput.Unknown)
