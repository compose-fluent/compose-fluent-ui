package io.github.composefluent.platform

import android.view.InputDevice
import android.view.MotionEvent
import androidx.compose.ui.input.pointer.PointerEvent

internal actual fun PointerEvent.platformScrollInfo(): PlatformScrollInfo {
    val event = motionEvent ?: return UnknownScroll
    return when {
        event.isFromSource(InputDevice.SOURCE_TOUCHPAD) -> ContinuousScroll
        event.isFromSource(InputDevice.SOURCE_MOUSE) &&
            event.getToolType(0) == MotionEvent.TOOL_TYPE_MOUSE -> WheelScroll
        else -> UnknownScroll
    }
}

private val WheelScroll = PlatformScrollInfo(ScrollInput.Wheel)
private val ContinuousScroll = PlatformScrollInfo(ScrollInput.Continuous)
private val UnknownScroll = PlatformScrollInfo(ScrollInput.Unknown)
