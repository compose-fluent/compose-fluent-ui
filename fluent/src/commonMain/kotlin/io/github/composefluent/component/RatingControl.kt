package io.github.composefluent.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.ProvideTextStyle
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.isActive

/**
 * A composable function that creates a rating control, allowing users to select a rating from a set of stars.
 *
 * @param value The current rating value, ranging from 0f to [maxRating].
 * @param onValueChanged A callback function that is invoked when the rating value changes. It receives the new rating value as a parameter.
 * @param modifier Modifier for styling and layout of the rating control.
 * @param colors A [VisualStateScheme] that defines the colors used for different visual states of the rating control (e.g., default, hovered, pressed, disabled).
 * @param width The width of each individual star in the rating control.
 * @param interactionSource A [MutableInteractionSource] to handle user interactions with the rating control.
 * @param placeholderValue A value to display as a placeholder when the [value] is 0f. This allows for showing a default rating (e.g., an average rating).
 * @param maxRating The maximum rating value, which determines the number of stars displayed.
 * @param caption A composable function that displays additional information or a label associated with the rating control.
 * @param stepValue If true, the [onValueChanged] callback will provide integer rating values (e.g., 1, 2, 3). If false, it will provide floating-point values, allowing for fractional ratings (e.g., 1.5, 2.7).
 * @param isReadOnly If true, the user cannot interact with the rating control to change the [value]. It will be displayed as a static rating.
 * @param isClearEnabled If true, clicking on the currently selected star will clear the rating, setting the [value] to 0f.
 * @param disabled If true, the rating control will be disabled, preventing user interaction and displaying it in a disabled state.
 */
@Composable
fun RatingControl(
    value: Float,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    colors: VisualStateScheme<RatingControlColor> = RatingControlDefaults.colors(),
    width: Dp = 16.dp,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholderValue: Float = 0f,
    maxRating: Int = 5,
    caption: @Composable () -> Unit = {},
    stepValue: Boolean = true,
    isReadOnly: Boolean = false,
    isClearEnabled: Boolean = false,
    disabled: Boolean = false
) {
    val valueRange = 0f..maxRating.toFloat()
    require(value in valueRange) { "value is invalid" }
    val isHovered = interactionSource.collectIsHoveredAsState()
    val itemPositions = remember(maxRating) { mutableStateListOf(*Array(maxRating) { Rect.Zero }) }
    val valueState by rememberUpdatedState { value }
    val placeholderValueState by rememberUpdatedState { placeholderValue }
    val displayPlaceholder = remember {
        derivedStateOf { valueState() == 0f && placeholderValueState() > 0f && !isHovered.value }
    }
    val shapeFraction by remember {
        derivedStateOf {
            parseValueToFraction(itemPositions, if (displayPlaceholder.value) placeholderValueState() else valueState())
        }
    }
    val color = colors.schemeFor(interactionSource.collectVisualState(disabled))
    val hoveredOffset = remember { mutableStateOf<Offset?>(null) }
    /** collect pointer release offset */
    LaunchedEffect(interactionSource, value) {
        interactionSource.interactions.filterIsInstance<PressInteraction>()
            .collectLatest {
                when (it) {
                    is PressInteraction.Release -> {
                        val offset = hoveredOffset.value ?: it.press.pressPosition
                        val targetValue = getValueByOffset(stepValue, offset, itemPositions)
                        if (isClearEnabled && value == targetValue) {
                            onValueChanged(0f)
                        } else {
                            println(targetValue)
                            onValueChanged(targetValue)
                        }
                    }
                }
            }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = modifier.hoverable(interactionSource, !disabled)
                .pointerInput(isReadOnly, disabled) {
                    /** detect hovered state pointer offset */
                    val currentContext = currentCoroutineContext()
                    if (!isReadOnly && !disabled) {
                        awaitPointerEventScope {
                            while (currentContext.isActive) {
                                val event = awaitPointerEvent(PointerEventPass.Main)
                                if (event.type == PointerEventType.Move) {
                                    hoveredOffset.value = event.changes.first().position
                                } else if (event.type == PointerEventType.Exit) {
                                    hoveredOffset.value = null
                                }
                            }
                        }
                    } else if (hoveredOffset.value != null) {
                        hoveredOffset.value = null
                    }
                }
                .clickable(
                    enabled = !disabled && !isReadOnly,
                    interactionSource = interactionSource,
                    onClick = {},
                    indication = null
                )
        ) {
            drawStar(
                color = color,
                width = width,
                maxRating = maxRating,
                selected = true,
                isHovered = isHovered.value,
                onItemPositioned = { index, position -> itemPositions[index] = position },
                value = valueState,
                displayPlaceholder = displayPlaceholder.value,
                modifier = Modifier.graphicsLayer {
                    clip = true
                    val currentHoveredOffset = hoveredOffset.value
                    shape = if (currentHoveredOffset != null) {
                        val hoveredValue = getValueByOffset(stepValue, currentHoveredOffset, itemPositions)
                        RatingStarClipShape(true, parseValueToFraction(itemPositions, hoveredValue))
                    } else {
                        RatingStarClipShape(true, shapeFraction)
                    }
                }
            )
            drawStar(
                color = color,
                width = width,
                maxRating = maxRating,
                selected = false,
                isHovered = isHovered.value,
                value = valueState,
                displayPlaceholder = displayPlaceholder.value,
                modifier = Modifier.graphicsLayer {
                    clip = true
                    val currentHoveredOffset = hoveredOffset.value
                    shape = if (currentHoveredOffset != null) {
                        val hoveredValue = getValueByOffset(stepValue, currentHoveredOffset, itemPositions)
                        RatingStarClipShape(false, parseValueToFraction(itemPositions, hoveredValue))
                    } else {
                        RatingStarClipShape(false, shapeFraction)
                    }
                })
        }
        ProvideTextStyle(
            FluentTheme.typography.caption.copy(color.captionColor)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides color.captionColor
            ) {
                caption()
            }
        }

    }
}

typealias RatingControlColorScheme = PentaVisualScheme<RatingControlColor>

/**
 * Represents the color scheme for the [RatingControl] component.
 *
 * @param color The color of the rating star when it's not selected.
 * @param selectedColor The color of the rating star when it's selected.
 * @param captionColor The color of the caption text displayed next to the rating stars.
 * @param placeholderColor The color of the rating star when it's used as a placeholder.
 */
@Immutable
data class RatingControlColor(
    val color: Color,
    val selectedColor: Color,
    val captionColor: Color,
    val placeholderColor: Color
)

/**
 * Contains the default values used for [RatingControl].
 */
object RatingControlDefaults {

    /**
     * Creates a [RatingControlColorScheme] with the default colors for a [RatingControl].
     *
     * @param default The default [RatingControlColor] to use.
     * @param hovered The [RatingControlColor] to use when the rating control is hovered.
     * @param pressed The [RatingControlColor] to use when the rating control is pressed.
     * @param disabled The [RatingControlColor] to use when the rating control is disabled.
     *
     * @return A [RatingControlColorScheme] that contains the specified colors.
     */
    @Composable
    @Stable
    fun colors(
        default: RatingControlColor = RatingControlColor(
            color = FluentTheme.colors.text.text.secondary,
            selectedColor = FluentTheme.colors.fillAccent.default,
            placeholderColor = FluentTheme.colors.text.text.primary,
            captionColor = FluentTheme.colors.text.text.secondary
        ),
        hovered: RatingControlColor = default.copy(
            selectedColor = FluentTheme.colors.fillAccent.default,
            placeholderColor = FluentTheme.colors.controlAlt.tertiary,
        ),
        pressed: RatingControlColor = default,
        disabled: RatingControlColor = default.copy(
            selectedColor = FluentTheme.colors.text.text.secondary
        )
    ) = RatingControlColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

/** parse value to rating control clip percent */
private fun parseValueToFraction(itemPositions: List<Rect>, value: Float): Float {
    val currentValueInt = value.toInt()
    val currentValueFloat = value - currentValueInt
    val lastRect = itemPositions.last()
    return if (lastRect.right > 0) {
        if (currentValueFloat == 0f && currentValueInt > 0) {
            itemPositions[currentValueInt - 1].right / lastRect.right
        } else {
            val item = itemPositions[currentValueInt]
            (item.left + item.width * currentValueFloat) / lastRect.right
        }
    } else {
        0f
    }
}

/** parse hovered offset to star value */
private fun getValueByOffset(stepValue: Boolean = true, offset: Offset, itemPositions: List<Rect>): Float {
    val lastIndex = itemPositions.lastIndex
    val offsetX = offset.x
    for (index in lastIndex downTo 0) {
        val nextRect = itemPositions.getOrNull(index + 1)
        val rect = itemPositions[index]
        when {
            index == lastIndex && rect.right <= offsetX -> return lastIndex + 1f
            offsetX >= rect.left && offsetX <= (nextRect?.right ?: rect.right) -> return if (stepValue) {
                index + 1f
            } else {
                index + ((offsetX - rect.left) / rect.width).coerceAtMost(1f)
            }
        }
    }
    return 0f
}

@Composable
private fun drawStar(
    color: RatingControlColor,
    width: Dp,
    maxRating: Int,
    selected: Boolean,
    isHovered: Boolean,
    displayPlaceholder: Boolean,
    modifier: Modifier = Modifier,
    onItemPositioned: (index: Int, bounds: Rect) -> Unit = { _, _ -> },
    value: () -> Float = { 0.0f }
) {
    Row(horizontalArrangement = Arrangement.spacedBy(ratingSpacing), modifier = modifier) {
        val hasValue = value() != 0f
        val (icon, iconColor) = when {
            selected -> FontIconPrimitive.FavoriteStarFull to when {
                (!hasValue && isHovered) || displayPlaceholder -> color.placeholderColor
                else -> color.selectedColor
            }

            else -> FontIconPrimitive.RatingStar to color.color
        }
        repeat(maxRating) { index ->
            Box(
                modifier = Modifier.onGloballyPositioned {
                    onItemPositioned(index, it.boundsInParent())
                }
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides iconColor,
                    LocalContentAlpha provides iconColor.alpha
                ) {
                    FontIcon(
                        type = icon,
                        contentDescription = null,
                        size = FontIconSize(width.value)
                    )
                }
                if (isHovered && !hasValue && selected) {
                    CompositionLocalProvider(
                        LocalContentColor provides color.color,
                        LocalContentAlpha provides color.color.alpha
                    ) {
                        FontIcon(
                            type = FontIconPrimitive.RatingStar,
                            contentDescription = null,
                            size = FontIconSize(width.value)
                        )
                    }
                }
            }


        }
    }
}

@Stable
private class RatingStarClipShape(
    private val isStart: Boolean,
    private val fraction: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val isRtl = layoutDirection == LayoutDirection.Rtl

        return Outline.Rectangle(
            if ((!isRtl && isStart) || isRtl) {
                Rect(
                    Offset.Zero,
                    Size(
                        size.width * if (!isRtl) {
                            fraction
                        } else {
                            1 - fraction
                        },
                        size.height
                    )
                )
            } else {
                Rect(
                    Offset(
                        size.width * fraction,
                        0f
                    ),
                    Size(
                        size.width * (1 - fraction),
                        size.height
                    )
                )
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherShape = other as? RatingStarClipShape ?: return false
        return otherShape.isStart == isStart && otherShape.fraction == fraction
    }

    override fun hashCode(): Int {
        return 31 * fraction.hashCode() + isStart.hashCode()
    }
}

private val ratingSpacing = 4.dp