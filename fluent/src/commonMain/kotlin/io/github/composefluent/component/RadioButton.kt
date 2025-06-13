package io.github.composefluent.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import kotlin.math.roundToInt

/**
 * A composable function that represents a radio button.
 *
 * @param selected Indicates whether the radio button is selected.
 * @param onClick The callback to be invoked when the radio button is clicked. If null, the radio button will not be clickable.
 * @param modifier The modifier to be applied to the radio button.
 * @param label The optional label to be displayed next to the radio button.
 * @param enabled Indicates whether the radio button is enabled.
 * @param styles The visual styles to be applied to the radio button based on its state.
 *  Defaults to [RadioButtonDefaults.selectedRadioButtonStyle] if selected is true, otherwise [RadioButtonDefaults.defaultRadioButtonStyle].
 * @param interactionSource The [MutableInteractionSource] representing the stream of [androidx.compose.foundation.interaction.Interaction]s
 *  for this RadioButton. You can create and pass in your own remembered instance to observe
 *  [androidx.compose.foundation.interaction.Interaction]s and customize the appearance / behavior of this RadioButton in different states.
 */
@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    styles: VisualStateScheme<RadioButtonStyle> = if(selected) {
        RadioButtonDefaults.selectedRadioButtonStyle()
    } else {
        RadioButtonDefaults.defaultRadioButtonStyle()
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    // TODO: Extract same logic
    val style = styles.schemeFor(interactionSource.collectVisualState( !enabled))
    Row(modifier.then(
        if (label != null) Modifier.defaultMinSize(minWidth = 120.dp)
        else Modifier
    ).clickable(interactionSource, null) {
        onClick?.invoke()
    }) {
        val fillColor by animateColorAsState(
            style.fillColor,
            tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
        )
        Layer(
            modifier = Modifier.size(20.dp),
            shape = CircleShape,
            color = fillColor,
            border = BorderStroke(
                1.dp,
                style.borderColor
            ),
            backgroundSizing = BackgroundSizing.InnerBorderEdge
        ) {
            Box(contentAlignment = FixedCenterAlignment) {
                // Bullet, Only displays when selected, or is pressed

                val size by animateDpAsState(
                    style.dotSize,
                    tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
                )
                // Inner
                Layer(
                    modifier = Modifier.size(if (size == 0.dp || !selected) size else size + 2.dp), // TODO: Remove this 2dp if outside border is provided
                    shape = CircleShape,
                    color = style.dotColor,
                    border = if (selected) BorderStroke(
                        1.dp,
                        style.dotBorderBrush
                    ) else null,
                    backgroundSizing = BackgroundSizing.InnerBorderEdge,
                    content = {}
                )
            }
        }
        label?.let {
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.offset(y = (-1).dp),
                text = it,
                style = FluentTheme.typography.body.copy(
                    color = style.labelColor
                )
            )
        }
    }
}

typealias RadioButtonStyleScheme = PentaVisualScheme<RadioButtonStyle>

/**
 * Represents the styling configuration for a RadioButton.
 *
 * @property fillColor The fill color of the radio button.
 * @property borderColor The border color of the radio button.
 * @property labelColor The color of the label text associated with the radio button.
 * @property dotSize The size of the inner dot that indicates selection.
 * @property dotColor The color of the inner dot.
 * @property dotBorderBrush The border brush of the inner dot.
 */
@Immutable
data class RadioButtonStyle(
    val fillColor: Color,
    val borderColor: Color,
    val labelColor: Color,
    val dotSize: Dp,
    val dotColor: Color,
    val dotBorderBrush: Brush
)

/**
 * Contains the default values used for [RadioButton].
 */
object RadioButtonDefaults {

    /**
     * Creates a [RadioButtonStyleScheme] with default styling for a RadioButton.
     *
     * This function provides a default appearance for a radio button, with different styles
     * for its various states (default, hovered, pressed, and disabled).
     *
     * @param default The base [RadioButtonStyle] used when the radio button is in its normal state.
     * Defaults to a style with:
     *   - `fillColor`: `FluentTheme.colors.controlAlt.secondary`
     *   - `borderColor`: `FluentTheme.colors.stroke.controlStrong.default`
     *   - `labelColor`: `FluentTheme.colors.text.text.primary`
     *   - `dotSize`: `0.dp` (no inner dot by default)
     *   - `dotColor`: `FluentTheme.colors.text.onAccent.primary`
     *   - `dotBorderBrush`: `FluentTheme.colors.borders.circle`
     * @param hovered The [RadioButtonStyle] used when the radio button is hovered.
     * Defaults to a style based on `default` but with `fillColor` set to `FluentTheme.colors.controlAlt.tertiary`.
     * @param pressed The [RadioButtonStyle] used when the radio button is pressed.
     * Defaults to a style based on `default` but with:
     *   - `fillColor`: `FluentTheme.colors.controlAlt.quaternary`
     *   - `borderColor`: `FluentTheme.colors.stroke.controlStrong.disabled`
     *   - `dotSize`: `10.dp` (a visible inner dot)
     * @param disabled The [RadioButtonStyle] used when the radio button is disabled.
     * Defaults to a style based on `default` but with:
     *   - `fillColor`: `FluentTheme.colors.controlAlt.disabled`
     *   - `borderColor`: `FluentTheme.colors.stroke.controlStrong.disabled`
     *   - `labelColor`: `FluentTheme.colors.text.text.disabled`
     *
     * @return A [RadioButtonStyleScheme] containing the specified styles for each state.
     */
    @Stable
    @Composable
    fun defaultRadioButtonStyle(
        default: RadioButtonStyle = RadioButtonStyle(
            fillColor = FluentTheme.colors.controlAlt.secondary,
            borderColor = FluentTheme.colors.stroke.controlStrong.default,
            labelColor = FluentTheme.colors.text.text.primary,
            dotSize = 0.dp,
            dotColor = FluentTheme.colors.text.onAccent.primary,
            dotBorderBrush = FluentTheme.colors.borders.circle
        ),
        hovered: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.tertiary,
        ),
        pressed: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.quaternary,
            borderColor = FluentTheme.colors.stroke.controlStrong.disabled,
            dotSize = 10.dp
        ),
        disabled: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.disabled,
            borderColor = FluentTheme.colors.stroke.controlStrong.disabled,
            labelColor = FluentTheme.colors.text.text.disabled
        )
    ) = RadioButtonStyleScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [RadioButtonStyleScheme] for a selected radio button.
     *
     * @param default The default style of the selected radio button.
     * @param hovered The style of the selected radio button when hovered.
     * @param pressed The style of the selected radio button when pressed.
     * @param disabled The style of the selected radio button when disabled.
     * @return A [RadioButtonStyleScheme] representing the visual states of a selected radio button.
     */
    @Stable
    @Composable
    fun selectedRadioButtonStyle(
        default: RadioButtonStyle = RadioButtonStyle(
            fillColor = FluentTheme.colors.fillAccent.default,
            borderColor = FluentTheme.colors.fillAccent.default,
            labelColor = FluentTheme.colors.text.text.primary,
            dotSize = 8.dp,
            dotColor = FluentTheme.colors.text.onAccent.primary,
            dotBorderBrush = FluentTheme.colors.borders.circle
        ),
        hovered: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.secondary,
            dotSize = 10.dp,
        ),
        pressed: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.tertiary,
            dotSize = 6.dp
        ),
        disabled: RadioButtonStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.disabled,
            borderColor = FluentTheme.colors.fillAccent.disabled,
            labelColor = FluentTheme.colors.text.text.disabled
        )
    ) = RadioButtonStyleScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

private val FixedCenterAlignment = Alignment { size, space, _ ->
    val centerX = (space.width - size.width).toFloat() / 2f
    val centerY = (space.height - size.height).toFloat() / 2f
    IntOffset(x = centerX.toInt(), y = centerY.roundToInt())
}