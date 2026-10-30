package io.github.composefluent.platform

import androidx.compose.ui.input.pointer.PointerEvent
import kotlin.time.Duration

/**
 * Describes the scroll information actually exposed by the platform backend. Delta conversion
 * remains the responsibility of Foundation; precision alone does not imply a gesture lifecycle
 * or that the platform supplies momentum.
 *
 * [ScrollInput.Wheel] is a wheel-like packet, not proof of a physical device type. Backends
 * without device identity can emit these during continuous input too, so callers should retain
 * [ScrollInput.Continuous] until the complete scroll sequence has settled.
 */
internal expect fun PointerEvent.platformScrollInfo(): PlatformScrollInfo

internal data class PlatformScrollInfo(
    val input: ScrollInput,
    val settling: ScrollSettling = ScrollSettling.PlatformDefault
)

internal enum class ScrollInput {
    Wheel,
    Continuous,
    Unknown
}

/** Only backends known to deliver system momentum override [PlatformDefault]. */
internal sealed interface ScrollSettling {
    data object PlatformDefault : ScrollSettling

    /** A native gesture or its momentum is still active, even if no delta is being delivered. */
    data object AwaitEnd : ScrollSettling

    /** The native gesture/momentum ended. Allow a frame for a pending momentum handoff. */
    data object Ended : ScrollSettling

    /** The backend delivers system momentum but loses native end events. */
    data class AfterIdle(val timeout: Duration) : ScrollSettling
}
