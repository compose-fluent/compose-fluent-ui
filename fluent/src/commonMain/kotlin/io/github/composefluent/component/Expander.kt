package io.github.composefluent.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.ProvideTextStyle
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import io.github.composefluent.surface.Card
import io.github.composefluent.surface.CardColor

/**
 * An expander is a component that allows the user to show and hide a region of content.
 *
 * @param expanded Whether the expander is expanded.
 * @param onExpandedChanged The callback that is triggered when the expander's expanded state changes.
 * @param heading The content displayed in the header of the expander.
 * @param modifier Modifier for the expander.
 * @param enabled Whether the expander is enabled.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [androidx.compose.foundation.interaction.Interaction]s for this Expander.
 * @param shape The shape of the expander's container.
 * @param icon The icon displayed in the header of the expander.
 * @param caption The caption displayed below the heading in the header.
 * @param trailing The content displayed at the trailing edge of the header.
 * @param expandContent The content that is shown when the expander is expanded.
 */
@Composable
fun Expander(
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    heading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = FluentTheme.shapes.control,
    icon: (@Composable () -> Unit)? = {},
    caption: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    expandContent: (@Composable ColumnScope.() -> Unit) = {},
) {
    Layer(
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        modifier = modifier,
        color = Color.Transparent,
        shape = shape,
        clipContent = true
    ) {
        Column {
            val targetInteractionSource =
                interactionSource ?: remember { MutableInteractionSource() }

            ExpanderItemContent(
                icon = icon,
                heading = heading,
                caption = caption,
                trailing = trailing,
                dropdown = {
                    SubtleButton(
                        interaction = targetInteractionSource,
                        onClick = { onExpandedChanged(!expanded) },
                        content = {
                            val degrees by animateFloatAsState(if (expanded) 180f else 0f)
                            FontIcon(
                                type = FontIconPrimitive.ChevronDown,
                                contentDescription = null,
                                modifier = Modifier.graphicsLayer { rotationZ = degrees }
                            )
                        },
                        iconOnly = true,
                        disabled = !enabled
                    )
                },
                modifier = Modifier.padding(top = 1.dp)
                    .heightIn(ExpanderHeaderHeight)
                    .background(FluentTheme.colors.background.card.default)
                    .clickable(
                        interactionSource = targetInteractionSource,
                        indication = null,
                        onClick = { onExpandedChanged(!expanded) },
                        enabled = enabled
                    )
            )
            ExpanderItemSeparator(color = FluentTheme.colors.stroke.card.default)
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    tween(FluentDuration.MediumDuration, 0, FluentEasing.FastInvokeEasing)
                ),
                exit = shrinkVertically(
                    tween(FluentDuration.QuickDuration, 0, FluentEasing.SoftDismissEasing)
                )
            ) {
                Column(modifier = Modifier.padding(bottom = 1.dp)) {
                    expandContent()
                }
            }
        }
    }
}

/**
 * A composable function that creates a single item within an expander component.
 *
 * @param heading The content to be displayed as the heading of the item.
 * @param modifier Modifier for styling and layout of the item.
 * @param color The background color of the item. Defaults to [FluentTheme.colors.background.card.secondary].
 * @param icon The optional icon to be displayed at the start of the item. If null, no icon is displayed.
 * @param caption The optional caption content to be displayed below the heading. If null, no caption is displayed.
 * @param trailing The optional content to be displayed at the end of the item. If null, no trailing content is displayed.
 * @param dropdown The optional content to be displayed in a dropdown section at the end of the item, commonly used for expansion indicators or actions. If null, no dropdown is displayed.
 */
@Composable
fun ExpanderItem(
    heading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = FluentTheme.colors.background.card.secondary,
    icon: (@Composable () -> Unit)? = {},
    caption: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    dropdown: (@Composable () -> Unit)? = {}
) {
    ExpanderItemContent(
        icon = icon,
        heading = heading,
        caption = caption,
        trailing = trailing,
        dropdown = dropdown,
        modifier = modifier.background(color)
    )
}

/**
 * A card-like item for an expander list.
 *
 * @param onClick The callback to be invoked when this item is clicked.
 * @param heading The content to be displayed as the item's heading.
 * @param modifier The [Modifier] to be applied to this item.
 * @param shape The shape of the card.
 * @param enabled Controls the enabled state of the item. When `false`, this item will not be clickable and will appear visually disabled.
 * @param colors The color scheme to use for the item's background and content based on its visual state.
 * @param captionColors The color scheme to use for the item's caption based on its visual state.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this item.
 * @param icon An optional icon to be displayed at the leading edge of the item.
 * @param caption Optional supporting text to be displayed below the heading.
 * @param trailing Optional content to be displayed at the trailing edge of the item.
 * @param dropdown Optional content to be displayed as a dropdown indicator at the trailing edge.
 */
@Composable
fun CardExpanderItem(
    onClick: () -> Unit,
    heading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FluentTheme.shapes.control,
    enabled: Boolean = true,
    colors: VisualStateScheme<CardColor> = ExpanderDefaults.cardExpanderItemColors(),
    captionColors: VisualStateScheme<Color> = PentaVisualScheme(
        default = FluentTheme.colors.text.text.secondary,
        hovered = FluentTheme.colors.text.text.secondary,
        pressed = FluentTheme.colors.text.text.tertiary,
        disabled = FluentTheme.colors.text.text.disabled
    ),
    interactionSource: MutableInteractionSource? = null,
    icon: (@Composable () -> Unit)? = {},
    caption: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    dropdown: (@Composable () -> Unit)? = null,
) {
    val targetInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val captionTextColor = captionColors.schemeFor(targetInteractionSource.collectVisualState(!enabled))
    Card(
        onClick = onClick,
        modifier = modifier,
        disabled = !enabled,
        cardColors = colors,
        shape = shape,
        interactionSource = targetInteractionSource
    ) {
        ExpanderItemContent(
            icon = icon,
            heading = heading,
            caption = caption,
            trailing = trailing,
            dropdown = dropdown,
            modifier = Modifier.heightIn(ExpanderHeaderHeight),
            captionTextColor = captionTextColor
        )
    }
}

/**
 * A card-like item for an expander list.
 * This item provides a visually distinct header for an expander section, allowing for
 * customization of the heading, optional icon, caption, trailing content, and dropdown.
 *
 * @param heading The composable function that renders the primary heading text or content.
 * @param modifier Modifier to be applied to the underlying Layer.
 * @param shape The shape of the card. Defaults to [FluentTheme.shapes.control].
 * @param color The background color of the card. Defaults to [FluentTheme.colors.background.card.default].
 * @param contentColor The preferred content color for text and icons within the card. Defaults to [FluentTheme.colors.text.text.primary].
 * @param captionTextColor The color of the caption text. Defaults to [FluentTheme.colors.text.text.secondary].
 * @param icon An optional composable function that renders an icon. If not provided, no icon is displayed.
 * @param caption A composable function that renders a caption. If not provided, no caption is displayed.
 * @param trailing A composable function that renders content to the right of the heading and caption, such as a button or indicator.
 * @param dropdown An optional composable function to render a dropdown or expand/collapse indicator.
 */
@Composable
fun CardExpanderItem(
    heading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FluentTheme.shapes.control,
    color: Color = FluentTheme.colors.background.card.default,
    contentColor: Color = FluentTheme.colors.text.text.primary,
    captionTextColor: Color = FluentTheme.colors.text.text.secondary,
    icon: (@Composable () -> Unit)? = {},
    caption: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    dropdown: (@Composable () -> Unit)? = null
) {
    Layer(
        modifier = modifier,
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        shape = shape,
        color = color,
        contentColor = contentColor
    ) {
        ExpanderItemContent(
            icon = icon,
            heading = heading,
            caption = caption,
            trailing = trailing,
            dropdown = dropdown,
            modifier = Modifier.heightIn(ExpanderHeaderHeight),
            captionTextColor = captionTextColor
        )
    }
}

/**
 * A separator line for [Expander] items.
 *
 * @param modifier Modifier to be applied to the separator.
 * @param color The color of the separator line. Defaults to `FluentTheme.colors.stroke.divider.default`.
 */
@Composable
fun ExpanderItemSeparator(
    modifier: Modifier = Modifier,
    color: Color = FluentTheme.colors.stroke.divider.default
) {
    Box(
        modifier = modifier.fillMaxWidth().height(1.dp).background(color)
    )
}

/**
 * Contains the default values used by [CardExpanderItem].
 */
object ExpanderDefaults {

    /**
     * Creates a [PentaVisualScheme] for the colors of a card expander item.
     *
     * @param default The default colors of the card expander item.
     * @param hovered The colors of the card expander item when hovered.
     * @param pressed The colors of the card expander item when pressed.
     * @param disabled The colors of the card expander item when disabled.
     *
     * @return A [PentaVisualScheme] containing the specified colors for the different states.
     */
    @Stable
    @Composable
    fun cardExpanderItemColors(
        default: CardColor = CardColor(
            fillColor = FluentTheme.colors.background.card.default,
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
            fillColor = FluentTheme.colors.background.card.default,
            contentColor = FluentTheme.colors.text.text.disabled,
            borderBrush = SolidColor(FluentTheme.colors.stroke.card.default)
        )
    ) = PentaVisualScheme(default, hovered, pressed, disabled)
}

@Composable
internal fun ExpanderItemContent(
    modifier: Modifier = Modifier,
    captionTextColor: Color = FluentTheme.colors.text.text.secondary,
    icon: (@Composable () -> Unit)? = {},
    heading: @Composable () -> Unit = {},
    caption: @Composable () -> Unit = {},
    trailing: @Composable () -> Unit = {},
    dropdown: (@Composable () -> Unit)? = {}
) {
    Row(
        modifier = Modifier.then(modifier)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Box(
                modifier = Modifier.widthIn(48.dp).defaultMinSize(16.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column(modifier = Modifier.padding(vertical = 13.dp)) {
            heading()
            ProvideTextStyle(FluentTheme.typography.caption.copy(captionTextColor)) {
                caption()
            }
        }
        Spacer(modifier = Modifier.weight(1f).height(1.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            trailing()
            if (dropdown != null) {
                Box(
                    modifier = Modifier.padding(start = 4.dp).defaultMinSize(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    dropdown()
                }
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

private val ExpanderHeaderHeight = 62.dp
