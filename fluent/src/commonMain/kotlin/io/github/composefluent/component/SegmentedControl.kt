package io.github.composefluent.component

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.translate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.layout.HorizontalIndicatorContentLayout
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A segmented control is a linear set of two or more segments, each of which functions as a mutually exclusive button.
 *
 * Segmented controls allow users to switch between different views or modes.
 *
 * @param modifier The [Modifier] to be applied to this segmented control.
 * @param color The background color of the segmented control. Defaults to [FluentTheme.colors.controlAlt.secondary].
 * @param borderStroke The border stroke of the segmented control. If null, no border is drawn.
 * Defaults to a border with [buttonBorderStrokeWidth] and [FluentTheme.colors.stroke.control.default].
 * @param content The content of the segmented control.
 * The content should be composed of [SegmentedButton]s, each representing a segment.
 */
@Composable
fun SegmentedControl(
    modifier: Modifier = Modifier,
    color: Color = FluentTheme.colors.controlAlt.secondary,
    borderStroke: BorderStroke? = BorderStroke(
        buttonBorderStrokeWidth,
        FluentTheme.colors.stroke.control.default
    ),
    content: @Composable RowScope.() -> Unit,
) {
    Layer(
        color = color,
        border = borderStroke,
        shape = FluentTheme.shapes.control,
        backgroundSizing = BackgroundSizing.OuterBorderEdge,
        modifier = modifier
    ) {
        Row(content = content)
    }
}

/**
 * A segmented button represents a single item within a [SegmentedControl].
 *
 * @param checked Whether this item is currently selected.
 * @param onCheckedChanged Callback to be invoked when this item's checked state changes.
 * @param colors The color scheme to use for this button, based on the interaction state. If not provided,
 * [ButtonDefaults.buttonColors] will be used when checked and [ButtonDefaults.subtleButtonColors] will be used when not checked.
 * @param indicator The composable function that will draw an indicator to show the [checked] state. By default it will draw
 * a [HorizontalIndicator].
 * @param modifier The modifier to apply to this item.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable and appears
 * visually disabled to the user.
 * @param position The position of the button within the segmented control.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this button. You can create and
 * pass in your own remembered [MutableInteractionSource] if desired.
 * @param icon The icon to display in the button. If `null`, no icon will be displayed.
 * @param text The text to display in the button. If `null`, no text will be displayed.
 */
@Composable
fun SegmentedButton(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    colors: VisualStateScheme<ButtonColor> = if (checked) {
        ButtonDefaults.buttonColors()
    } else {
        ButtonDefaults.subtleButtonColors()
    },
    indicator: @Composable () -> Unit = {
        HorizontalIndicator(
            visible = checked,
            modifier = Modifier.padding(bottom = buttonBorderStrokeWidth)
        )
    },
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    position: SegmentedItemPosition = SegmentedItemPosition.Center,
    interactionSource: MutableInteractionSource? = null,
    icon: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null
) {
    val targetInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val currentColors = colors.schemeFor(targetInteractionSource.collectVisualState(!enabled))
    val shape = if (checked) {
        FluentTheme.shapes.control
    } else {
        val padding = when (position) {
            SegmentedItemPosition.Start -> PaddingValues(
                top = 3.dp,
                bottom = 3.dp,
                start = 3.dp,
                end = 1.dp
            )

            SegmentedItemPosition.Center -> PaddingValues(
                horizontal = 1.dp,
                vertical = 3.dp
            )

            SegmentedItemPosition.End -> PaddingValues(
                top = 3.dp,
                bottom = 3.dp,
                start = 1.dp,
                end = 3.dp
            )
        }
        PaddingBackgroundShape(2.dp, padding)
    }
    Layer(
        color = currentColors.fillColor,
        contentColor = currentColors.contentColor,
        border = BorderStroke(buttonBorderStrokeWidth, currentColors.borderBrush),
        backgroundSizing = BackgroundSizing.OuterBorderEdge,
        modifier = modifier.clickable(
            enabled = enabled,
            interactionSource = targetInteractionSource,
            indication = null,
            onClick = { onCheckedChanged(!checked) }
        ),
        shape = shape,
    ) {
        HorizontalIndicatorContentLayout(
            indicator = indicator,
            icon = icon,
            text = text,
            trailing = null,
            modifier = Modifier.defaultMinSize(minHeight = buttonMinHeight)
        )
    }
}

/**
 * Represents the position of a segmented item within a [SegmentedControl].
 *
 * This enum is used to define the visual appearance and padding of a [SegmentedButton]
 * when placed in a [SegmentedControl].
 *
 * - **Start**: Indicates the item is the first in the [SegmentedControl] and should have
 *   padding on its right side to appear connected to the next item.
 * - **Center**: Indicates the item is in the middle of the [SegmentedControl] and should
 *   have padding on both its left and right sides to appear connected to adjacent items.
 * - **End**: Indicates the item is the last in the [SegmentedControl] and should have
 *   padding on its left side to appear connected to the previous item.
 */
enum class SegmentedItemPosition { Start, Center, End }

/**
 * A horizontal indicator that animates its width based on the [visible] state.
 *
 * @param modifier The modifier to be applied to the indicator.
 * @param visible Whether the indicator should be visible. Animates its width between 0dp and 16dp.
 * @param enabled Whether the indicator is enabled. Affects the color of the indicator.
 * @param color The color of the indicator when enabled. Defaults to [FluentTheme.colors.fillAccent.default].
 * @param disabledColor The color of the indicator when disabled. Defaults to [FluentTheme.colors.fillAccent.disabled].
 */
@Composable
fun HorizontalIndicator(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    enabled: Boolean = true,
    color: Color = FluentTheme.colors.fillAccent.default,
    disabledColor: Color = FluentTheme.colors.fillAccent.disabled
) {
    val width by updateTransition(visible).animateDp(transitionSpec = {
        if (targetState) tween(
            FluentDuration.QuickDuration,
            easing = FluentEasing.FastInvokeEasing
        )
        else tween(FluentDuration.QuickDuration, easing = FluentEasing.FastDismissEasing)
    }, targetValueByState = { if (it) 16.dp else 0.dp })
    Box(
        modifier = modifier
            .size(width = width, height = 3.dp)
            .background(
                color = if (enabled) {
                    color
                } else {
                    disabledColor
                },
                shape = CircleShape
            )
    )
}

@Stable
private class PaddingBackgroundShape(corner: Dp, private val padding: PaddingValues) : Shape {
    private val shape = RoundedCornerShape(corner)
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return with(density) {
            val leftPadding = padding.calculateLeftPadding(layoutDirection).toPx()
            val topPadding = padding.calculateTopPadding().toPx()
            val rightPadding = padding.calculateRightPadding(layoutDirection).toPx()
            val bottomPadding = padding.calculateBottomPadding().toPx()
            val paddingSize = Size(
                size.width - leftPadding - rightPadding,
                size.height - topPadding - bottomPadding
            )
            when (val oldOutline = shape.createOutline(paddingSize, layoutDirection, density)) {
                is Outline.Rectangle -> Outline.Rectangle(
                    oldOutline.rect.translate(
                        Offset(
                            leftPadding,
                            topPadding
                        )
                    )
                )

                is Outline.Rounded -> Outline.Rounded(
                    oldOutline.roundRect.translate(
                        Offset(
                            leftPadding,
                            topPadding
                        )
                    )
                )

                is Outline.Generic -> Outline.Generic(oldOutline.path.apply {
                    translate(
                        Offset(
                            leftPadding,
                            topPadding
                        )
                    )
                })
            }
        }
    }
}