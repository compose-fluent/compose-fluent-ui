package io.github.composefluent.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A composable function that renders a Switcher UI element.
 *
 * @param checked The current checked state of the Switcher.
 * @param onCheckStateChange A callback function invoked when the checked state changes.
 *   It receives the new checked state as a parameter.
 * @param text An optional text label to display alongside the Switcher.
 * @param textBefore Whether to display the text label before the Switcher (true) or after (false). Defaults to false.
 * @param enabled Whether the Switcher is enabled (true) or disabled (false). Defaults to true.
 * @param styles The visual styles to apply to the Switcher, defined by [SwitcherStyleScheme].
 *   Defaults to selected styles if [checked] is true, or default styles otherwise.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [androidx.compose.foundation.interaction.Interaction]s
 * for this Switcher. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe
 * the Switcher's interactions in your custom UI.
 */
@Composable
fun Switcher(
    checked: Boolean,
    onCheckStateChange: (checked: Boolean) -> Unit,
    text: String? = null,
    textBefore: Boolean = false,
    enabled: Boolean = true,
    styles: SwitcherStyleScheme = if (checked) {
        SwitcherDefaults.selectedSwitcherStyle()
    } else {
        SwitcherDefaults.defaultSwitcherStyle()
    },
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // TODO: Draggable
    // TODO: Extract same logic
    val transition = updateTransition(checked)
    val style = styles.schemeFor(interactionSource.collectVisualState(!enabled))
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = interactionSource,
            role = Role.Button,
            enabled = enabled
        ) {
            onCheckStateChange(!checked)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (textBefore) {
            text?.let {
                Text(
                    modifier = Modifier.offset(y = (-1).dp),
                    text = it,
                    color = style.labelColor
                )
                Spacer(Modifier.width(12.dp))
            }
        }

        val fillColor by animateColorAsState(
            style.fillColor,
            tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
        )

        Box(
            modifier = Modifier.size(40.dp, 20.dp)
                .border(1.dp, style.borderBrush, CircleShape)
                .clip(CircleShape)
                .background(fillColor)
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            val height by animateDpAsState(
                style.controlSize.height,
                tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
            )

            val width by animateDpAsState(
                style.controlSize.width,
                tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
            )

            val density = LocalDensity.current
            val offset by transition.animateDp(
                transitionSpec = {
                    tween(FluentDuration.QuickDuration, easing = FluentEasing.PointToPointEasing)
                },
                targetValueByState = {
                    if (checked) 26.dp - (width / 2) else 0.dp
                }
            )

            val offsetX by remember(density) {
                derivedStateOf { with(density) { offset.toPx() } }
            }

            // Control
            Box(
                Modifier.size(width, height)
                    .graphicsLayer {
                        translationX = offsetX
                        transformOrigin = TransformOrigin.Center
                    }
                    .clip(CircleShape)
                    .background(
                        if (checked) when {
                            !enabled -> FluentTheme.colors.text.onAccent.disabled
                            else -> FluentTheme.colors.text.onAccent.primary
                        }
                        else when {
                            !enabled -> FluentTheme.colors.text.text.disabled
                            else -> FluentTheme.colors.text.text.secondary
                        }
                    )
            )
        }

        if (!textBefore) {
            text?.let {
                Spacer(Modifier.width(12.dp))
                Text(
                    modifier = Modifier.offset(y = (-1).dp),
                    text = it,
                    style = FluentTheme.typography.body,
                    color = style.labelColor
                )
            }
        }
    }
}

/**
 * Contains the default values used for [Switcher].
 */
object SwitcherDefaults {

    /**
     * Creates a default [SwitcherStyleScheme] for the [Switcher] component.
     *
     * This function defines the visual appearance of the [Switcher] in its different states:
     * default, hovered, pressed, and disabled. It uses the [FluentTheme] for styling.
     *
     * @param default The [SwitcherStyle] for the default state.
     * @param hovered The [SwitcherStyle] for the hovered state.
     * @param pressed The [SwitcherStyle] for the pressed state.
     * @param disabled The [SwitcherStyle] for the disabled state.
     * @return A [SwitcherStyleScheme] containing the styles for each state.
     */
    @Stable
    @Composable
    fun defaultSwitcherStyle(
        default: SwitcherStyle = SwitcherStyle(
            fillColor = FluentTheme.colors.controlAlt.secondary,
            labelColor = FluentTheme.colors.text.text.primary,
            controlColor = FluentTheme.colors.text.text.secondary,
            controlSize = DpSize(width = 12.dp, height = 12.dp),
            borderBrush = SolidColor(FluentTheme.colors.controlStrong.default)
        ),
        hovered: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.tertiary,
            controlSize = DpSize(width = 14.dp, height = 14.dp)
        ),
        pressed: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.quaternary,
            controlSize = DpSize(width = 17.dp, height = 14.dp)
        ),
        disabled: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.controlAlt.disabled,
            borderBrush = SolidColor(FluentTheme.colors.controlStrong.disabled),
            controlColor = FluentTheme.colors.text.text.disabled,
            labelColor = FluentTheme.colors.text.text.disabled
        )
    ) = SwitcherStyleScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [SwitcherStyleScheme] for a selected state of the [Switcher].
     *
     * This function defines the visual styles for the [Switcher] when it's in a selected state.
     * It allows customization of the appearance based on different interaction states (default, hovered, pressed, disabled).
     *
     * @param default The default [SwitcherStyle] applied when no specific interaction is occurring.
     * @param hovered The [SwitcherStyle] applied when the switcher is hovered over.
     * @param pressed The [SwitcherStyle] applied when the switcher is pressed.
     * @param disabled The [SwitcherStyle] applied when the switcher is disabled.
     * @return A [SwitcherStyleScheme] containing the styles for each state.
     */
    @Stable
    @Composable
    fun selectedSwitcherStyle(
        default: SwitcherStyle = SwitcherStyle(
            fillColor = FluentTheme.colors.fillAccent.default,
            labelColor = FluentTheme.colors.text.text.primary,
            controlColor = FluentTheme.colors.text.onAccent.primary,
            controlSize = DpSize(width = 12.dp, height = 12.dp),
            borderBrush = SolidColor(Color.Transparent)
        ),
        hovered: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.secondary,
            controlSize = DpSize(width = 14.dp, height = 14.dp)
        ),
        pressed: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.tertiary,
            controlSize = DpSize(width = 17.dp, height = 14.dp)
        ),
        disabled: SwitcherStyle = default.copy(
            fillColor = FluentTheme.colors.fillAccent.disabled,
            borderBrush = SolidColor(FluentTheme.colors.fillAccent.disabled),
            controlColor = FluentTheme.colors.text.onAccent.disabled,
            labelColor = FluentTheme.colors.text.text.disabled
        )
    ) = SwitcherStyleScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

typealias SwitcherStyleScheme = PentaVisualScheme<SwitcherStyle>

/**
 * Represents the visual style of a [Switcher].
 *
 * @property fillColor The fill color of the switcher's background.
 * @property labelColor The color of the text label associated with the switcher.
 * @property controlColor The color of the movable control within the switcher.
 * @property controlSize The size of the movable control within the switcher.
 * @property borderBrush The brush used to draw the border around the switcher's background.
 */
data class SwitcherStyle(
    val fillColor: Color,
    val labelColor: Color,
    val controlColor: Color,
    val controlSize: DpSize,
    val borderBrush: Brush
)