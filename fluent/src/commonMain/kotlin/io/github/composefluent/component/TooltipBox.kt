package io.github.composefluent.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipDefaults
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.plus
import androidx.compose.ui.window.PopupPositionProvider
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.ProvideTextStyle
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.ElevationDefaults
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * A composable that displays a tooltip when the user hovers over or long-presses the content.
 *
 * @param tooltip The composable content of the tooltip.
 * @param state The state of the tooltip, controlling its visibility and interaction. Defaults to a
 * remembered [TooltipState].
 * @param modifier The modifier to be applied to the tooltip's container.
 * @param positionProvider The provider for the tooltip's position relative to the content. Defaults
 * to a remembered [PopupPositionProvider] using [rememberTooltipPositionProvider].
 * @param enabled Whether the tooltip is enabled. If false, the tooltip will not be shown. Defaults
 * to true.
 * @param content The composable content that triggers the tooltip.
 */
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalFluentApi
@Composable
fun TooltipBox(
    tooltip: @Composable () -> Unit,
    state: TooltipState = rememberTooltipState(),
    modifier: Modifier = Modifier,
    positionProvider: PopupPositionProvider = rememberTooltipPositionProvider(state),
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    FlyoutAnchorScope {
        BasicTooltipBox(
            state = state,
            positionProvider = positionProvider,
            enableUserInput = false,
            tooltip = {
                TooltipBoxDefaults.Tooltip(
                    visible = state.isVisible,
                    content = tooltip,
                    modifier = Modifier.flyoutSize()
                )
            },
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .flyoutAnchor()
                    .gestureHandle(enabled, state)
            ) {
                content()
            }
        }
    }
}

/**
 * Creates and remembers a [TooltipState].
 *
 * @param initialIsVisible the initial visibility state of the tooltip.
 * @param isPersistent whether the tooltip should remain visible until explicitly hidden.
 * @param mutatorMutex the [MutatorMutex] used to mutually exclude state changes.
 * @return a [TooltipState] that can be used to control the tooltip's visibility.
 */
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalFluentApi
@Composable
fun rememberTooltipState(
    initialIsVisible: Boolean = false,
    isPersistent: Boolean = true,
    mutatorMutex: MutatorMutex = BasicTooltipDefaults.GlobalMutatorMutex,
): TooltipState {
    return remember(initialIsVisible, isPersistent, mutatorMutex) {
        TooltipState(initialIsVisible, isPersistent, mutatorMutex)
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.gestureHandle(
    enabled: Boolean,
    state: TooltipState
): Modifier {
    return if (enabled) {

        then(Modifier.pointerInput(state) {
            coroutineScope {
                awaitEachGesture {
                    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
                    val pass = PointerEventPass.Initial
                    val change = awaitFirstDown(pass = pass)
                    val inputType = change.type
                    if (inputType == PointerType.Touch || inputType == PointerType.Stylus) {
                        try {
                            withTimeout(longPressTimeout) {
                                waitForUpOrCancellation(pass)
                            }
                        } catch (_: PointerEventTimeoutCancellationException) {
                            state.pointerPosition = change.position - if (inputType == PointerType.Touch) {
                                viewConfiguration.minimumTouchTargetSize.toSize().center
                            } else {
                                Offset.Zero
                            }
                            state.pointerType = inputType
                            launch {
                                state.show(MutatePriority.UserInput)
                            }
                            val changes = awaitPointerEvent(pass).changes
                            for (i in 0 until changes.size) { changes[i].consume() }
                        }
                    }
                }
            }
        }.pointerInput(state) {
            coroutineScope {
                var hoveredJob: Job? = null
                awaitEachGesture {
                    val event = awaitPointerEvent(PointerEventPass.Main)
                    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
                    val change = event.changes[0]
                    if (change.type != PointerType.Mouse) return@awaitEachGesture
                    if (event.type == PointerEventType.Enter) {
                        hoveredJob?.cancel()
                        hoveredJob = launch {
                            delay(longPressTimeout)
                            state.show(mutatePriority = MutatePriority.UserInput)
                        }
                    }
                    if (!state.isVisible && event.type != PointerEventType.Exit) {
                        state.pointerType = change.type
                        state.pointerPosition = change.position
                    } else if (event.type == PointerEventType.Exit) {
                        state.pointerPosition = null
                        state.pointerType = null
                        hoveredJob?.cancel()
                        state.dismiss()
                    }
                }
            }

        })
    } else {
        this
    }
}

/**
 * Creates and remembers a [PopupPositionProvider] for a tooltip, based on the provided
 * [state] and [anchorPadding].
 *
 * This function calculates the position of the tooltip popup relative to its anchor or
 * the mouse pointer, ensuring it remains within the screen bounds.
 *
 * @param state The [TooltipState] that controls the tooltip's visibility and provides
 *              information about the pointer position.
 * @param anchorPadding Additional padding to apply around the anchor bounds when
 *                      calculating the tooltip position. Defaults to 0.dp.
 * @return A [PopupPositionProvider] that can be used to position the tooltip popup.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberTooltipPositionProvider(
    state: TooltipState?,
    anchorPadding: Dp = 0.dp
): PopupPositionProvider {
    val anchorPadding = with(LocalDensity.current) { (anchorPadding + flyoutPopPaddingFixShadowRender + flyoutDefaultPadding).toPx().roundToInt() }
    val mouseOverflowPadding =
        with(LocalDensity.current) { defaultMousePadding.toPx().roundToInt() }
    return remember(state, anchorPadding, mouseOverflowPadding) {
        TooltipPopupPositionProvider(state, anchorPadding, mouseOverflowPadding)
    }
}

/**
 * A state object that can be hoisted to control and observe tooltip box state.
 *
 * In most cases, this will be created via [rememberTooltipState].
 *
 * @param initialIsVisible The initial visibility state of the tooltip.
 * @param isPersistent Whether the tooltip should stay visible when clicked.
 * @param mutatorMutex Mutex for managing mutual exclusion of visibility changes.
 */
@Stable
@ExperimentalFoundationApi
class TooltipState(
    initialIsVisible: Boolean = false,
    isPersistent: Boolean = true,
    mutatorMutex: MutatorMutex = BasicTooltipDefaults.GlobalMutatorMutex,
) : BasicTooltipState by BasicTooltipState(
    initialIsVisible = initialIsVisible,
    isPersistent = isPersistent,
    mutatorMutex = mutatorMutex
) {
    var pointerPosition: Offset? by mutableStateOf(null)
        internal set
    var pointerType: PointerType? by mutableStateOf(null)
        internal set
}

/**
 * Contains the default values used for [TooltipBox].
 */
@ExperimentalFluentApi
object TooltipBoxDefaults {

    /**
     * The spacing between the icon and the text in the tooltip.
     */
    val iconSpacing = 8.dp

    /**
     * Displays a tooltip with the given [content] when [visible] is true.
     *
     * This function provides a simple way to show a tooltip that appears and disappears based
     * on the provided visibility state. It uses a [MutableTransitionState] internally to
     * animate the visibility change.
     *
     * @param visible `true` if the tooltip should be visible, `false` otherwise.
     * @param modifier Modifier to be applied to the tooltip.
     * @param content The composable content of the tooltip.
     */
    @Composable
    fun Tooltip(
        visible: Boolean,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        val visibleState = remember {
            MutableTransitionState(false)
        }
        visibleState.targetState = visible
        Tooltip(
            visibleState = visibleState,
            content = content,
            modifier = modifier
        )
    }

    /**
     * Creates a tooltip with the specified [visibleState] to control its visibility.
     *
     * @param visibleState A [MutableTransitionState] to control the visibility of the tooltip.
     *   When `true`, the tooltip is shown. When `false`, the tooltip is hidden.
     * @param modifier The [Modifier] to be applied to the tooltip.
     * @param content The content to be displayed within the tooltip.
     */
    @Composable
    fun Tooltip(
        visibleState: MutableTransitionState<Boolean>,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {

        AcrylicPopupContent(
            elevation = ElevationDefaults.tooltip,
            visibleState = visibleState,
            enterTransition = fadeIn(
                tween(
                    FluentDuration.ShortDuration,
                    easing = FluentEasing.FastInvokeEasing
                )
            ),
            exitTransition = fadeOut(
                tween(
                    FluentDuration.QuickDuration,
                    easing = FluentEasing.FastDismissEasing
                )
            ),
            shape = FluentTheme.shapes.control,
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = 6.dp,
                bottom = 8.dp
            ),
            content = {
                ProvideTextStyle(
                    value = FluentTheme.typography.caption,
                    content = content
                )
            },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
internal class TooltipPopupPositionProvider(
    private val state: TooltipState?,
    private val anchorPadding: Int,
    private val mouseOverflowPadding: Int,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return Snapshot.withoutReadObservation {
            val pointerPosition = state?.pointerPosition
            val alignmentPosition = pointerPosition ?: Offset(
                x = anchorBounds.width / 2f,
                y = 0f
            )
            var (offsetX, offsetY) = alignmentPosition + IntOffset(
                x = anchorBounds.left - popupContentSize.width / 2,
                y = anchorBounds.top - anchorPadding - popupContentSize.height
            )
            val topOverflow = -offsetY

            if (topOverflow > 0) {

                if (pointerPosition != null) {
                    // Fixed pointer overflow dismiss
                    offsetY = pointerPosition.y + anchorPadding + mouseOverflowPadding
                } else {
                    offsetY -= topOverflow
                }
            }

            val rightOverflow = offsetX + popupContentSize.width - windowSize.width
            if (rightOverflow > 0) {
                offsetX -= rightOverflow
            }

            offsetX = offsetX.coerceAtLeast(0f)
            offsetY = offsetY.coerceAtLeast(0f)

            return IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
        }
    }
}

private val defaultMousePadding = 16.dp