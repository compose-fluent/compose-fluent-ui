package io.github.composefluent.surface

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A basic card composable that provides a contained space for content with a defined shape.
 *
 * @param modifier The [Modifier] to be applied to the card.
 * @param shape The [Shape] of the card's background. Defaults to `FluentTheme.shapes.overlay`.
 * @param content The content to be displayed inside the card.
 */
@Composable
fun Card(
    modifier: Modifier,
    shape: Shape = FluentTheme.shapes.overlay,
    content: @Composable () -> Unit
) {
    Layer(
        modifier = modifier,
        shape = shape,
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        content = content
    )
}

/**
 * A composable that represents a clickable card.
 *
 * @param onClick The callback to be invoked when the card is clicked.
 * @param modifier The [Modifier] to be applied to the card.
 * @param shape The [Shape] of the card. Defaults to [FluentTheme.shapes.control].
 * @param disabled Whether the card is disabled or not. Defaults to `false`.
 * @param cardColors The colors of the card in different states. Defaults to [CardDefaults.cardColors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions for the card.
 * @param content The composable content of the card.
 */
@Composable
fun Card(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FluentTheme.shapes.control,
    disabled: Boolean = false,
    cardColors: VisualStateScheme<CardColor> = CardDefaults.cardColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val visualState = interactionSource.collectVisualState(disabled)
    val colors = cardColors.schemeFor(visualState)

    val fillColor by animateColorAsState(
        colors.fillColor,
        animationSpec = tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
    )

    val contentColor by animateColorAsState(
        colors.contentColor,
        animationSpec = tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
    )

    Layer(
        modifier = modifier.clickable(
            enabled = !disabled,
            onClick = onClick,
            indication = null,
            interactionSource = interactionSource
        ),
        shape = shape,
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        color = fillColor,
        border = BorderStroke(1.dp, colors.borderBrush),
        contentColor = contentColor,
        content = content
    )
}

/**
 * Represents the color scheme for a [Card] in different states.
 *
 * @property fillColor The background color of the card.
 * @property contentColor The color of the content (text, icons, etc.) inside the card.
 * @property borderBrush The brush used to draw the border around the card.
 */
data class CardColor(
    val fillColor: Color,
    val contentColor: Color,
    val borderBrush: Brush
)

/**
 * Contains the default values used for [Card].
 */
object CardDefaults {
    /**
     * Creates a [PentaVisualScheme] that represents the color scheme for a card in different states.
     *
     * @param default The default [CardColor] used when the card is in its normal state.
     *  Defaults to a card with a default background layer, primary text color, and a default card border.
     * @param hovered The [CardColor] used when the card is being hovered over.
     *  Defaults to a card based on the `default` configuration but with a secondary control background
     *  and a control border.
     * @param pressed The [CardColor] used when the card is being pressed.
     *  Defaults to a card with a tertiary control background, secondary text color, and a default
     *  control border.
     * @param disabled The [CardColor] used when the card is disabled.
     *  Defaults to a card based on the `pressed` configuration but with a default background layer,
     *  primary text color, and a default card border.
     * @return A [PentaVisualScheme] containing the color schemes for the card in various states.
     */
    @Stable
    @Composable
    fun cardColors(
        default: CardColor = CardColor(
            fillColor = FluentTheme.colors.background.layer.default,
            contentColor = FluentTheme.colors.text.text.primary,
            borderBrush = SolidColor(FluentTheme.colors.stroke.card.default)
        ),
        hovered: CardColor = default.copy(
            fillColor = FluentTheme.colors.control.secondary,
            borderBrush = FluentTheme.colors.borders.control
        ),
        pressed: CardColor = CardColor(
            fillColor = FluentTheme.colors.control.tertiary,
            contentColor = FluentTheme.colors.text.text.secondary,
            borderBrush = SolidColor(FluentTheme.colors.stroke.control.default)
        ),
        disabled: CardColor = pressed.copy(
            fillColor = FluentTheme.colors.background.layer.default,
            contentColor = FluentTheme.colors.text.text.primary,
            borderBrush = SolidColor(FluentTheme.colors.stroke.card.default)
        )
    ) = PentaVisualScheme(default, hovered, pressed, disabled)
}