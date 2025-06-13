package io.github.composefluent.component

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.PopupProperties
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * A composable that displays a slider, allowing the user to select a value from a continuous range.
 *
 * This slider provides visual feedback through a rail, track, and thumb, and can also display tick marks
 * for discrete steps. It supports snapping to these steps and displaying a tooltip with the selected
 * value.
 *
 * @param value The current value of the slider.
 * @param onValueChange Called when the value is changed by the user.
 * @param modifier Modifier for the slider.
 * @param enabled Whether the slider is enabled for user interaction.
 * @param valueRange The range of values the slider can take.
 * @param steps The number of discrete steps between the start and end of the value range.
 *              If 0, the slider is continuous.
 * @param snap Whether the slider should snap to the nearest step value. Defaults to true if steps > 0.
 * @param showTickMark Whether to display tick marks along the rail. Defaults to true if steps > 0.
 * @param onValueChangeFinished Called when the user finishes interacting with the slider.
 *                              It will receive the value of the slider when interaction ended.
 * @param tooltipContent A composable function that provides the content for the tooltip.
 *                       It receives a [SliderState] as a parameter.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for this Slider.
 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    snap: Boolean = steps != 0,
    showTickMark: Boolean = steps != 0,
    onValueChangeFinished: ((Float) -> Unit)? = null,
    tooltipContent: @Composable (SliderState) -> Unit = { SliderDefaults.Tooltip(it, snap = snap) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BasicSlider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = valueRange,
        steps = steps,
        snap = snap,
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource,
        rail = { state ->
            SliderDefaults.Rail(
                state = state,
                enabled = enabled,
                showTick = showTickMark
            )
        },
        track = { state ->
            SliderDefaults.Track(state, enabled = enabled)
        },
        thumb = { state ->
            SliderDefaults.Thumb(state, enabled = enabled, label = tooltipContent)
        }
    )
}

/**
 * A composable function that displays a slider with a customizable state.
 *
 * @param state The state of the slider, including the current value, steps, snap behavior, and value range.
 * @param modifier Modifier to be applied to the slider.
 * @param enabled Whether the slider is enabled or disabled.
 * @param showTickMark Whether to show tick marks along the slider rail. Defaults to true if steps are greater than 0.
 * @param tooltipContent A composable function that defines the content of the tooltip that appears when dragging the thumb.
 * It receives the current [SliderState] as a parameter. Defaults to a basic tooltip showing the current value.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this slider.
 */
@Composable
fun Slider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showTickMark: Boolean = state.steps != 0,
    tooltipContent: @Composable (SliderState) -> Unit = { SliderDefaults.Tooltip(it, snap = state.snap) },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BasicSlider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        rail = { state ->
            SliderDefaults.Rail(
                state = state,
                enabled = enabled,
                showTick = showTickMark
            )
        },
        track = { state ->
            SliderDefaults.Track(state, enabled = enabled)
        },
        thumb = { state ->
            SliderDefaults.Thumb(state, enabled = enabled, label = tooltipContent)
        }
    )
}

/**
 * A basic slider component that allows users to select a value from a range.
 *
 * This slider provides a foundation for creating custom sliders with different styles and behaviors.
 * It handles the core logic of value selection, snapping, and interaction, while allowing you to
 * define the visual appearance of the slider rail, track, and thumb.
 *
 * @param value The current value of the slider.
 * @param onValueChange Callback that is triggered when the slider's value changes.
 * @param modifier Modifier for styling and layout customization of the slider.
 * @param enabled Controls whether the slider is enabled or disabled.
 * @param valueRange The range of values the slider can represent.
 * @param steps The number of discrete steps in the slider's value range. A value of 0 means continuous range.
 * @param snap Whether the slider should snap to the nearest step value when released.
 * @param onValueChangeFinished Callback that is triggered when the user finishes interacting with the slider.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for the slider.
 * @param rail Composable function to draw the slider's rail.
 * @param track Composable function to draw the slider's track (the portion indicating the selected value).
 * @param thumb Composable function to draw the slider's thumb (the draggable element).
 */
@Composable
fun BasicSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    snap: Boolean = steps != 0,
    onValueChangeFinished: ((Float) -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    rail: @Composable (SliderState) -> Unit,
    track: @Composable (SliderState) -> Unit,
    thumb: @Composable (SliderState) -> Unit,
) {
    val state =
        remember(steps, valueRange) { SliderState(value, steps, snap, onValueChangeFinished, valueRange) }
    state.value = value
    state.onValueChangeFinished = onValueChangeFinished
    state.onValueChange = onValueChange

    SliderImpl(
        modifier = modifier,
        state = state,
        enabled = enabled,
        interactionSource = interactionSource,
        rail = rail,
        track = track,
        thumb = thumb
    )
}

/**
 * A basic slider without any default styling.
 *
 * This composable provides a foundational slider implementation, allowing for full customization
 * of the rail, track, and thumb components. It is intended for cases where the default
 * [Slider] does not meet specific design or functionality requirements.
 *
 * @param state The current state of the slider, which manages the value, steps, snapping, and
 *              drag behavior.
 * @param modifier Modifier for the slider layout.
 * @param enabled Whether the slider is enabled for interaction.
 * @param interactionSource The [MutableInteractionSource] representing the stream of Interactions
 *                          for this slider. You will usually create and pass in your own
 *                          remembered instance.
 * @param rail A composable function to render the slider's rail. It receives the [SliderState] as a parameter.
 * @param track A composable function to render the slider's track (the filled portion). It receives the [SliderState] as a parameter.
 * @param thumb A composable function to render the slider's thumb (the draggable element). It receives the [SliderState] as a parameter.
 */
@Composable
fun BasicSlider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    rail: @Composable (SliderState) -> Unit,
    track: @Composable (SliderState) -> Unit,
    thumb: @Composable (SliderState) -> Unit,
) {
    SliderImpl(
        modifier = modifier,
        state = state,
        enabled = enabled,
        interactionSource = interactionSource,
        rail = rail,
        track = track,
        thumb = thumb
    )
}

@Composable
private fun SliderImpl(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    rail: @Composable (SliderState) -> Unit,
    track: @Composable (SliderState) -> Unit,
    thumb: @Composable (SliderState) -> Unit,
) {
    var widthPx by remember { mutableStateOf(0) }
    val density by rememberUpdatedState(LocalDensity.current)

    Box(
        content = {
            rail(state)
            track(state)
            thumb(state)
        },
        contentAlignment = Alignment.CenterStart,
        propagateMinConstraints = true,
        modifier = modifier
            .height(32.dp)
            .defaultMinSize(minWidth = 120.dp)
            .layout { measurable, constraints ->
                if (constraints.hasFixedWidth) {
                    val placeable = measurable.measure(constraints)
                    widthPx = placeable.width
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                } else {
                    val placeable =
                        measurable.measure(constraints.copy(maxWidth = constraints.minWidth))
                    widthPx = placeable.width
                    layout(placeable.width, placeable.height) {
                        placeable.place(0, 0)
                    }
                }
            }
            // .semantics {  } // TODO: Slider semantics
            .pointerInput(enabled, state.onValueChange) {
                if (enabled) awaitEachGesture {
                    val down = awaitFirstDown()
                    down.consume()

                    val press = PressInteraction.Press(down.position)
                    interactionSource.tryEmit(press)

                    // Fluent  Behavior: Press will immediately change the value
                    state.startDragging(down.position, widthPx, density)

                    var change: PointerInputChange? = down

                    // We don't need touch slop
                    /*var change = awaitHorizontalTouchSlopOrCancellation(down.id) { change, overslop ->
                        val delta = change.positionChange()
                        change.consume()
                        println("Slop: ${delta} $overslop")
                        offset = Offset(x = offset.x + delta.x + overslop, y = offset.y)
                        currentOnFractionChange(calcFraction(offset))
                    }*/

                    while (change != null && change.pressed) {
                        change = awaitHorizontalDragOrCancellation(down.id)
                        if (change != null) {
                            val delta = change.positionChange()
                            change.consume()
                            state.updateDelta(delta, widthPx, density)
                        }
                    }
                    // Notify change finished
                    interactionSource.tryEmit(PressInteraction.Release(press))
                    state.stopDragging(widthPx, density)
                }
            }
    )
}

/**
 * [SliderState] holds the state of the [Slider] composable.
 *
 * @param value The initial value of the slider.
 * @param steps The number of discrete steps in the slider. If 0, the slider is continuous.
 * @param snap Whether the slider should snap to the nearest step when released. Defaults to `true` if `steps > 0`, `false` otherwise.
 * @param onValueChangeFinished A callback to be invoked when the user finishes interacting with the slider.
 *   The callback receives the final value of the slider.
 * @param valueRange The range of values that the slider can represent.
 */
class SliderState(
    value: Float = 0f,
    val steps: Int = 0,
    val snap: Boolean = steps != 0,
    var onValueChangeFinished: ((Float) -> Unit)? = null,
    val valueRange: ClosedFloatingPointRange<Float>
) {
    /**
     * An array of fractions representing the positions of each step along the slider's track.
     * The array includes the start (0.0f) and end (1.0f) positions, as well as the intermediate step positions.
     * Used for snapping the slider's thumb to discrete values when steps are enabled.
     */
    val stepFractions = getStepFractions(steps)

    private var valueState by mutableFloatStateOf(value)
    internal var onValueChange: ((Float) -> Unit)? = null

    /**
     * The current value of the slider. If the new value is outside of [valueRange], it will be
     * coerced to the closest value inside the range.
     *
     * @throws IllegalArgumentException if the [valueRange] is empty.
     */
    var value: Float
        set(newVal) {
            val coercedValue = newVal.coerceIn(valueRange.start, valueRange.endInclusive)
            // We snap value at dragging ending instead of each dragging delta
            /*val snappedValue =
                snapValueToTick(
                    coercedValue,
                    tickFractions,
                    valueRange.start,
                    valueRange.endInclusive
                )*/
            valueState = coercedValue
        }
        get() = valueState

    /**
     * Whether the slider is currently being dragged.
     */
    var isDragging by mutableStateOf(false)
        private set

    /**
     * The raw offset of the slider thumb, relative to the start of the slider track.
     * This offset is used to accumulate the delta during dragging, it's not converted to user space yet.
     * Relating to component size, for accumulating offset delta
     */
    var rawOffset by mutableStateOf(Offset.Zero)
        private set

    /**
     * The current fraction of the slider's progress, without any scaling or consideration of
     * the component's size. This is a value between 0.0 and 1.0, representing the relative
     * position of the thumb along the track, regardless of the track's actual length.
     */
    var rawFraction by mutableStateOf(valueToFraction(value, valueRange))
        private set

    private fun setRawOffset(offset: Offset, width: Int, density: Density) {
        this.rawOffset = offset
        this.rawFraction = offsetToFraction(offset, width, density)
    }

    private fun setRawFraction(fraction: Float, width: Int, density: Density) {
        this.rawFraction = fraction
        this.rawOffset =
            Offset(x = fractionToOffset(fraction, width, density), y = this.rawOffset.y)
    }

    internal fun startDragging(downOffset: Offset, width: Int, density: Density) {
        setRawOffset(downOffset, width, density)

        this.isDragging = true

        val fraction = offsetToFraction(downOffset, width, density)
        this.value = scaleToUserValue(fraction, this.valueRange)
        this.onValueChange?.invoke(this.value)
    }

    internal fun updateDelta(delta: Offset, width: Int, density: Density) {
        setRawOffset(Offset(x = this.rawOffset.x + delta.x, y = this.rawOffset.y), width, density)

        val fraction = offsetToFraction(this.rawOffset, width, density)
        this.value = scaleToUserValue(fraction, this.valueRange)
        this.onValueChange?.invoke(this.value)
    }

    internal fun stopDragging(width: Int, density: Density) {
        if (this.steps > 0) {
            // Snap
            // TODO: Add snap animation, maybe we should use anchoredDraggable?
            val currentValue = this.value
            if (this.snap) {
                val nearestValue = snapToNearestTickValue(currentValue)
                val fraction = valueToFraction(nearestValue, this.valueRange)
                this.value = nearestValue
                setRawFraction(fraction, width, density)
            } else {
                val fraction = valueToFraction(currentValue, this.valueRange)
                setRawFraction(fraction, width, density)
            }
        }

        this.onValueChangeFinished?.invoke(this.value)
        this.isDragging = false
    }

    internal fun snapToNearestTickValue(value: Float): Float {
        return this.stepFractions
            .map { lerp(this.valueRange.start, this.valueRange.endInclusive, it) }
            .minBy { abs(it - value) }
    }

    /**
     * Calculates the nearest value to the current [value] from the predefined step fractions.
     *
     * This function iterates through the [stepFractions], linearly interpolates the values within
     * the [valueRange] based on these fractions, and then determines which of these interpolated
     * values is closest to the current [value].
     *
     * @return The nearest value to the current [value] based on the defined step fractions.
     */
    fun nearestValue(): Float {
        return this.stepFractions
            .map { lerp(this.valueRange.start, this.valueRange.endInclusive, it) }
            .minBy { abs(it - value) }
    }
}

private fun getStepFractions(steps: Int): FloatArray {
    return FloatArray(steps + 2) {
        it.toFloat() / (steps + 1)
    }
}

private fun fractionToOffset(fraction: Float, width: Int, density: Density): Float {
    val thumbRadius = with(density) { (ThumbSizeWithBorder / 2).toPx() }
    return lerp(thumbRadius, width - thumbRadius, fraction)
}

@Stable
private fun offsetToFraction(offset: Offset, width: Int, density: Density): Float {
    val thumbRadius = with(density) { (ThumbSizeWithBorder / 2).toPx() }

    return valueToFraction(offset.x, thumbRadius..(width - thumbRadius)).coerceIn(0f, 1f)
}

@Stable
private fun scaleToUserValue(fraction: Float, range: ClosedFloatingPointRange<Float>): Float =
    (range.endInclusive - range.start) * fraction + range.start

@Stable
private fun valueToFraction(
    value: Float, valueRange: ClosedFloatingPointRange<Float>
): Float = (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)

@Stable
private fun calcThumbOffset(
    maxWidth: Int, thumbSize: Float, padding: Float, fraction: Float
): Float {
    return (maxWidth - thumbSize) * fraction - padding
}

/**
 * Contains the default values used for the [Slider] and its components.
 */
object SliderDefaults {

    /**
     * The track of the slider, which displays the progress of the slider.
     *
     * @param state The state of the slider.
     * @param modifier The modifier to apply to this layout.
     * @param enabled Controls the enabled state of the track. When `false`, the track will be displayed in a disabled state.
     * @param color The color of the track when enabled.
     * @param disabledColor The color of the track when disabled.
     * @param shape The shape of the track.
     */
    @Composable
    fun Track(
        state: SliderState,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        color: Color = FluentTheme.colors.fillAccent.default,
        disabledColor: Color = FluentTheme.colors.fillAccent.disabled,
        shape: Shape = CircleShape
    ) {
        Spacer(
            modifier = modifier
                .layout { measurable, constraints ->
                    val placeable = if (constraints.hasBoundedWidth) {
                        val maxWidth =
                            (ThumbRadiusWithBorder.toPx() + (state.rawFraction * (constraints.maxWidth - ThumbSizeWithBorder.toPx())))
                                .roundToInt()
                                .coerceIn(0, constraints.maxWidth)
                        val newConstraints = constraints.copy(
                            minWidth = maxWidth,
                            maxWidth = maxWidth
                        )
                        measurable.measure(newConstraints)
                    } else {
                        measurable.measure(constraints)
                    }
                    val width = maxOf(constraints.maxWidth, placeable.width)
                    val height = maxOf(constraints.maxHeight, placeable.height)
                    layout(width, height) {
                        val offset = Alignment.CenterStart.align(
                            size = IntSize(placeable.width, placeable.height),
                            space = IntSize(width, height),
                            layoutDirection = layoutDirection
                        )
                        placeable.place(offset)
                    }
                }
                .requiredHeight(4.dp)
                .background(if (enabled) color else disabledColor, shape)
        )
    }

    private val TickThickness = 1.dp
    private val TickHeight = 4.dp
    private val TickY = 22.dp
    private val TopTickY = 6.dp

    /**
     * The rail of the slider, can be use alone, normally include tick mark on top of it.
     *
     * @param state the state of the slider.
     * @param modifier the [Modifier] to be applied to this rail.
     * @param enabled controls the enabled state of the slider. When `false`, this rail will
     * be appear disabled, it won't respond to user input.
     * @param showTick if true will show tick mark, default true when [SliderState.steps] > 0.
     * @param showTopTick if true will show tick mark on the top, default true.
     * @param color the color of this rail, default `controlStrong.default`.
     * @param disabledColor the color of this rail when disabled, default `controlStrong.default`.
     * @param border the border of this rail, default `controlStrong.default` in dark mode and light mode.
     * @param shape the shape of this rail, default `CircleShape`.
     */
    @Composable
    fun Rail(
        state: SliderState,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        showTick: Boolean = state.steps > 0,
        showTopTick: Boolean = true,
        color: Color = FluentTheme.colors.controlStrong.default,
        disabledColor: Color = FluentTheme.colors.controlStrong.default,
        border: BorderStroke? = BorderStroke(
            1.dp, if (FluentTheme.colors.darkMode) FluentTheme.colors.stroke.controlStrong.default
            else FluentTheme.colors.controlStrong.default
        ),
        shape: Shape = CircleShape
    ) {
        Box(modifier, propagateMinConstraints = true) {
            val color = if (enabled) color else disabledColor

            Layer(
                modifier = Modifier.requiredHeight(4.dp),
                shape = shape,
                color = color,
                border = border,
                backgroundSizing = BackgroundSizing.InnerBorderEdge,
                content = {}
            )

            if (showTick && state.steps > 0) Tick(
                modifier = Modifier.matchParentSize(),
                color = color,
                state = state,
                showTopTick = showTopTick
            )
        }
    }

    /**
     * Draws tick marks on the slider rail.
     *
     * @param modifier The modifier to be applied to the tick marks.
     * @param color The color of the tick marks.
     * @param state The [SliderState] that holds the state of the slider.
     * @param showTopTick Whether to show the ticks on the top side of the rail.
     */
    @Composable
    fun Tick(modifier: Modifier, color: Color, state: SliderState, showTopTick: Boolean) {
        Canvas(modifier) {
            // Start at center of the Thumb
            val scaledWidth =
                size.width - ThumbSize.toPx() // We don't need the start and end half Thumb
            val startX = ThumbSize.toPx() / 2
            val tickY = TickY.toPx()
            val topTickY = TopTickY.toPx()
            val tickThickness = TickThickness.toPx()
            val tickHeight = TickHeight.toPx()

            for (stepFraction in state.stepFractions) {
                val x = scaledWidth * stepFraction + startX
                drawLine(
                    color = color,
                    start = Offset(x = x, y = tickY),
                    end = Offset(x = x, y = tickY + tickHeight),
                    strokeWidth = tickThickness
                )

                if (showTopTick) {
                    drawLine(
                        color = color,
                        start = Offset(x = x, y = topTickY),
                        end = Offset(x = x, y = topTickY + tickHeight),
                        strokeWidth = tickThickness
                    )
                }
            }
        }
    }

    /**
     * The thumb used in [Slider].
     *
     * @param state The [SliderState] of the slider.
     * @param label The composable lambda to render the label of the thumb, it is visible when thumb is dragging.
     * @param modifier The [Modifier] to be applied to the thumb.
     * @param enabled Controls the enabled state of the thumb. When `false`, this thumb will not respond to user input,
     *   and it will appear visually disabled.
     * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s
     *   for this thumb. You can create and pass in your own remembered [MutableInteractionSource] if
     *   you want to observe [Interaction]s and customize the appearance / behavior of this thumb in
     *   different [Interaction]s.
     * @param shape The [Shape] of the thumb.
     * @param border The [BorderStroke] of the thumb. If `null`, no border will be drawn.
     * @param ringColor The color of the thumb's outer ring.
     * @param color The default color of the inner thumb.
     * @param draggingColor The color of the inner thumb when it is being dragged.
     * @param disabledColor The color of the inner thumb when it is disabled.
     */
    @OptIn(ExperimentalFluentApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun Thumb(
        state: SliderState,
        label: @Composable (state: SliderState) -> Unit = { Tooltip(state) },
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape = CircleShape,
        border: BorderStroke? = BorderStroke(1.dp, FluentTheme.colors.borders.circle),
        ringColor: Color = FluentTheme.colors.controlSolid.default,
        color: Color = FluentTheme.colors.fillAccent.default,
        draggingColor: Color = FluentTheme.colors.fillAccent.tertiary,
        disabledColor: Color = FluentTheme.colors.fillAccent.disabled
    ) {
        val hovered by interactionSource.collectIsHoveredAsState()
        val pressed by interactionSource.collectIsPressedAsState()

        FlyoutAnchorScope {
            Layer(
                modifier = modifier
                    .flyoutAnchor()
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints.copy(minWidth = 0))
                        val width = maxOf(constraints.maxWidth, placeable.width)
                        val height = maxOf(constraints.maxHeight, placeable.height)
                        layout(width, height) {
                            val offset = Alignment.CenterStart.align(
                                size = IntSize(placeable.width, placeable.height),
                                space = IntSize(width, height),
                                layoutDirection = layoutDirection
                            )
                            placeable.place(
                                x = offset.x + calcThumbOffset(
                                    maxWidth = width,
                                    thumbSize = ThumbSize.toPx(),
                                    padding = 1.dp.toPx(),
                                    fraction = state.rawFraction
                                ).roundToInt(),
                                y = offset.y + 0
                            )
                        }
                    }
                    .requiredSize(ThumbSizeWithBorder)
                    .hoverable(interactionSource, enabled),
                shape = shape,
                color = ringColor,
                border = border,
                backgroundSizing = BackgroundSizing.InnerBorderEdge
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Inner Thumb
                    Box(
                        Modifier.size(
                            animateDpAsState(
                                when {
                                    pressed || state.isDragging -> InnerThumbPressedSize
                                    hovered -> InnerThumbHoverSize
                                    else -> InnerThumbSize
                                },
                                tween(
                                    FluentDuration.QuickDuration,
                                    easing = FluentEasing.FastInvokeEasing
                                )
                            ).value
                        ).background(
                            when {
                                !enabled -> disabledColor
                                pressed || state.isDragging -> draggingColor
                                else -> color
                            }, shape
                        )
                    )
                }
                if (state.isDragging) {
                    Popup(
                        properties = PopupProperties(focusable = false),
                        popupPositionProvider = rememberTooltipPositionProvider(state = null),
                        content = {
                            TooltipBoxDefaults.Tooltip(
                                visibleState = remember { MutableTransitionState(true) },
                                content = { label(state) },
                                modifier = Modifier.flyoutAnchor()
                            )
                        }
                    )
                }
            }
        }
    }

    /**
     * A composable function that displays a tooltip for a slider.
     *
     * @param state The [SliderState] object that holds the current state of the slider.
     * @param snap Whether to snap the tooltip value to the nearest tick mark. If true, the tooltip
     * will display the snapped value. Otherwise, it will display the current value. Defaults to the `snap` value in the [SliderState].
     */
    @Composable
    fun Tooltip(state: SliderState, snap: Boolean = state.snap) {
        Text(
            if (snap) state.snapToNearestTickValue(state.value).toString()
            else state.value.toString()
        )
    }
}

private val ThumbSize = 20.dp
private val ThumbSizeWithBorder = ThumbSize + 2.dp
private val ThumbRadiusWithBorder = ThumbSizeWithBorder / 2
private val InnerThumbSize = 12.dp
private val InnerThumbHoverSize = 14.dp
private val InnerThumbPressedSize = 10.dp
