@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.composefluent.platform

import androidx.compose.ui.dom.domEventOrNull
import androidx.compose.ui.input.pointer.PointerEvent
import org.w3c.dom.events.WheelEvent

internal actual fun PointerEvent.platformScrollInfo(): PlatformScrollInfo =
    when ((domEventOrNull as? WheelEvent)?.deltaMode) {
        WheelEvent.DOM_DELTA_LINE, WheelEvent.DOM_DELTA_PAGE -> WheelScroll
        // Pixel units identify neither a trackpad nor a native momentum lifecycle. Let
        // Foundation interpret them instead of quantizing every pixel packet to one item.
        else -> UnknownScroll
    }

private val WheelScroll = PlatformScrollInfo(ScrollInput.Wheel)
private val UnknownScroll = PlatformScrollInfo(ScrollInput.Unknown)
