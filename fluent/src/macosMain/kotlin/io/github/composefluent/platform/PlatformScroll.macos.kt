package io.github.composefluent.platform

import androidx.compose.ui.appkit.appkitEventOrNull
import androidx.compose.ui.input.pointer.PointerEvent
import platform.AppKit.NSEventPhaseCancelled
import platform.AppKit.NSEventPhaseEnded
import platform.AppKit.NSEventPhaseNone

internal actual fun PointerEvent.platformScrollInfo(): PlatformScrollInfo {
    val event = appkitEventOrNull ?: return UnknownScroll
    // A momentum phase takes precedence over the preceding finger gesture's end phase.
    val phase = event.momentumPhase.takeUnless { it == NSEventPhaseNone } ?: event.phase
    return when {
        phase and (NSEventPhaseEnded or NSEventPhaseCancelled) != NSEventPhaseNone -> EndedScroll
        phase != NSEventPhaseNone -> ActiveScroll
        // Pixel precision does not by itself guarantee phase or system momentum support.
        event.hasPreciseScrollingDeltas -> ContinuousScroll
        else -> WheelScroll
    }
}

private val UnknownScroll = PlatformScrollInfo(ScrollInput.Unknown)
private val WheelScroll = PlatformScrollInfo(ScrollInput.Wheel)
private val ContinuousScroll = PlatformScrollInfo(ScrollInput.Continuous)
private val ActiveScroll = PlatformScrollInfo(ScrollInput.Continuous, ScrollSettling.AwaitEnd)
private val EndedScroll = PlatformScrollInfo(ScrollInput.Continuous, ScrollSettling.Ended)
