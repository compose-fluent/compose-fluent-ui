package io.github.composefluent.platform

import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.input.pointer.PointerEvent
import java.awt.event.MouseWheelEvent
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

internal actual fun PointerEvent.platformScrollInfo(): PlatformScrollInfo {
    val event = awtEventOrNull as? MouseWheelEvent ?: return UnknownScroll
    return when {
        // AWT on macOS rounds and accelerates ordinary wheels too, so fractional rotation is
        // not trackpad evidence. Its phase-aware accumulator can instead emit a zero integer
        // rotation for trackpads. Nonzero packets remain ambiguous within that same gesture.
        isMacOS -> if (event.wheelRotation == 0) MacContinuousScroll else WheelScroll
        // Windows keeps WM_MOUSEWHEEL's fractional WHEEL_DELTA units; other AWT backends may
        // also expose sub-notch deltas. Delegate those without assuming macOS momentum semantics.
        else -> abs(
            event.preciseWheelRotation - event.wheelRotation.toDouble()
        ).let { if (it > PrecisionEpsilon) ContinuousScroll else WheelScroll }
    }
}

private val isMacOS = System.getProperty("os.name").startsWith("Mac", ignoreCase = true)
private const val PrecisionEpsilon = 0.001
private val WheelScroll = PlatformScrollInfo(ScrollInput.Wheel)
private val UnknownScroll = PlatformScrollInfo(ScrollInput.Unknown)
private val ContinuousScroll = PlatformScrollInfo(ScrollInput.Continuous)

// CPlatformResponder forwards native momentum deltas but drops phase and zero-delta end
// events. Only this backend uses an idle heuristic; it cannot detect a stationary finger.
private val MacContinuousScroll = PlatformScrollInfo(
    ScrollInput.Continuous,
    ScrollSettling.AfterIdle(150.milliseconds)
)
