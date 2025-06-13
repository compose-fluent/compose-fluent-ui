package io.github.composefluent.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalCompactMode
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A composable function that represents a list item.
 *
 * This function provides a customizable list item with support for selection, icons, trailing content, and different selection types.
 *
 * @param selected Whether the list item is currently selected.
 * @param onSelectedChanged Callback invoked when the selected state changes. Provides the new selected state.
 * @param text The main text content of the list item.
 * @param modifier Modifier for styling and layout.
 * @param selectionType The type of selection indicator to use. See [ListItemSelectionType]. Defaults to [ListItemSelectionType.Standard].
 * @param icon Optional leading icon for the list item.
 * @param trailing Optional trailing content for the list item.
 * @param interaction Optional [MutableInteractionSource] to handle interaction events.
 * @param enabled Whether the list item is enabled for interaction.
 * @param colors A [VisualStateScheme] to define the colors of the list item based on its state.
 *               Defaults to selected or default list item colors based on the [selected] parameter.
 *
 * Design guide: [WinUI 3 Figma design file](https://www.figma.com/community/file/1159947337437047524)/Primitives/ListItem
 */
@Composable
fun ListItem(
    selected: Boolean,
    onSelectedChanged: (Boolean) -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectionType: ListItemSelectionType = ListItemSelectionType.Standard,
    icon: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    interaction: MutableInteractionSource? = null,
    enabled: Boolean = true,
    colors: VisualStateScheme<ListItemColor> = if (selected) {
        ListItemDefaults.selectedListItemColors()
    } else {
        ListItemDefaults.defaultListItemColors()
    },
) {
    ListItem(
        indicator = if (selectionType == ListItemSelectionType.Standard) {
            { ListItemDefaults.Indicator(selected, enabled) }
        } else {
            null
        },
        selectionIcon = when (selectionType) {
            ListItemSelectionType.Standard -> null
            ListItemSelectionType.Check -> { {
                if (selected) { ListItemDefaults.CheckIcon() }
            } }
            ListItemSelectionType.Radio -> { {
                if (selected) { ListItemDefaults.RadioIcon() }
            } }
        },
        text = text,
        icon = icon,
        trailing = trailing,
        interaction = interaction,
        enabled = enabled,
        onClick = { onSelectedChanged(!selected) },
        colors = colors,
        modifier = modifier
    )
}

/**
 * A composable function that represents a list item.
 *
 * This function displays a single item within a list. It supports various configurations,
 * including an optional selection icon, indicator, icon, and trailing content.
 *
 * @param onClick The callback to be invoked when the list item is clicked.
 * @param text The composable content representing the main text of the list item.
 * @param modifier The modifier to be applied to the list item.
 * @param selectionIcon An optional composable function to display a selection icon (e.g., a checkbox or radio button).
 * @param indicator An optional composable function to display an indicator (e.g., a vertical bar to denote selection).
 * @param icon An optional composable function to display an icon at the start of the list item.
 * @param trailing An optional composable function to display trailing content (e.g., a chevron or additional text).
 * @param interaction An optional [MutableInteractionSource] to handle item interactions.
 * @param enabled Specifies whether the list item is enabled for interaction. Defaults to true.
 * @param colors A [VisualStateScheme] of [ListItemColor] that defines the colors of the list item for different interaction states.
 * Defaults to [ListItemDefaults.defaultListItemColors].
 *
 * Design guide: [WinUI 3 Figma design file](https://www.figma.com/community/file/1159947337437047524)/Primitives/ListItem
 */
@Composable
fun ListItem(
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectionIcon: (@Composable () -> Unit)? = null,
    indicator: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    interaction: MutableInteractionSource? = null,
    enabled: Boolean = true,
    colors: VisualStateScheme<ListItemColor> = ListItemDefaults.defaultListItemColors(),
) {
    val actualInteraction = interaction ?: remember { MutableInteractionSource() }
    val color = colors.schemeFor(actualInteraction.collectVisualState(!enabled))

    val fillColor by animateColorAsState(
        color.fillColor,
        animationSpec = tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
    )

    val contentColor by animateColorAsState(
        color.contentColor,
        animationSpec = tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
    )
    Layer(
        modifier = modifier
            .defaultMinSize(minWidth = 108.dp, if (LocalCompactMode.current) ListItemCompactHeight else ListItemHeight)
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .fillMaxWidth(),
        shape = FluentTheme.shapes.control,
        color = fillColor,
        contentColor = contentColor,
        border = BorderStroke(1.dp, color.borderBrush),
        backgroundSizing = BackgroundSizing.OuterBorderEdge
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
        ) {
            indicator?.invoke()
            Row(
                modifier = Modifier
                    .clickable(
                        onClick = onClick,
                        interactionSource = actualInteraction,
                        indication = null,
                        enabled = enabled
                    )
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectionIcon != null && indicator == null) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.padding(horizontal = 2.dp).size(12.dp)
                    ) {
                        selectionIcon()
                    }
                }
                if (icon != null) {
                    Box(
                        modifier = Modifier.size(ListItemDefaults.iconSize),
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    text()
                }
                CompositionLocalProvider(
                    LocalContentColor provides color.trailingColor,
                    LocalContentAlpha provides color.trailingColor.alpha,
                    LocalTextStyle provides FluentTheme.typography.caption.copy(fontWeight = FontWeight.Normal)
                ) {
                    trailing?.invoke()
                }
            }
        }
    }
}

/**
 * A header for a list of items.
 *
 * @param content The composable content to display within the header.
 * @param modifier The [Modifier] to be applied to the header.
 * @param color The color of the text in the header. Defaults to `FluentTheme.colors.text.text.secondary`.
 */
@Composable
fun ListHeader(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = FluentTheme.colors.text.text.secondary
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .height(
                height = if (LocalCompactMode.current) {
                    ListItemCompactHeight
                } else {
                    ListItemHeight
                }
            )
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides FluentTheme.typography.caption.copy(fontWeight = FontWeight.Normal),
            LocalContentAlpha provides color.alpha,
            LocalContentColor provides color,
            content = content
        )
    }
}

/**
 * A separator line for list items.
 *
 * This composable displays a horizontal line that can be used to separate items within a list.
 * It uses the `divider` color from the current [FluentTheme.colors.stroke] for its background.
 *
 * @param modifier The [Modifier] to be applied to this separator.
 */
@Composable
fun ListItemSeparator(modifier: Modifier) {
    Box(
        Modifier
            .then(modifier)
            .padding(top = 1.dp, bottom = 2.dp)
            .fillMaxWidth().height(1.dp)
            .background(FluentTheme.colors.stroke.divider.default)
    )
}

/**
 * Contains the default values used for [ListItem].
 */
object ListItemDefaults {

    /**
     * The default size of the icon in the list item.
     */
    val iconSize = 16.dp

    /**
     * Represents a check icon for use in a list item.
     *
     * This composable displays a checkmark icon, typically used to indicate a selected state
     * in a list item with a "check" selection type.
     *
     * The icon is a solid checkmark with the following properties:
     * - `type`: `FontIconPrimitive.Accept` (checkmark)
     * - `contentDescription`: "Check" for accessibility
     * - `size`: `FontIconSize.Small`
     *
     * @see ListItem
     * @see ListItemSelectionType
     */
    @Composable
    fun CheckIcon() {
        FontIconSolid8(
            type = FontIconPrimitive.Accept,
            contentDescription = "Check",
            size = FontIconSize.Small,
        )
    }

    /**
     * Represents a radio button icon for use in a list item.
     *
     * This composable draws a small circle, filled with the current content color,
     * representing a selected radio button state.
     *
     * The icon has a fixed size of 12.dp and contains a smaller, 4.dp filled circle.
     */
    @Composable
    fun RadioIcon() {
        Box(modifier = Modifier
            .size(12.dp)
            .wrapContentSize(Alignment.Center)
            .size(4.dp)
            .background(LocalContentColor.current.copy(LocalContentAlpha.current), CircleShape)
        )
    }

    /**
     * Represents an icon that indicates a cascading or hierarchical relationship.
     * It displays a chevron-right icon.
     */
    @Composable
    fun CascadingIcon() {
        FontIcon(
            type = FontIconPrimitive.ChevronRight,
            contentDescription = "cascading",
            size = FontIconSize.Small,
        )
    }

    /**
     * The indicator for a list item.
     *
     * @param visible Whether the indicator is visible or not. Defaults to `true`.
     * @param enabled Whether the indicator is enabled or not. Defaults to `true`.
     * @param color The color of the indicator when it's enabled. Defaults to `FluentTheme.colors.fillAccent.default`.
     * @param disabledColor The color of the indicator when it's disabled. Defaults to `FluentTheme.colors.fillAccent.disabled`.
     */
    @Composable
    fun Indicator(
        visible: Boolean = true,
        enabled: Boolean = true,
        color: Color = FluentTheme.colors.fillAccent.default,
        disabledColor: Color = FluentTheme.colors.fillAccent.disabled
    ) {
        val height by updateTransition(visible).animateDp(transitionSpec = {
            if (targetState) tween(FluentDuration.ShortDuration, easing = FluentEasing.FastInvokeEasing)
            else tween(FluentDuration.QuickDuration, easing = FluentEasing.SoftDismissEasing)
        }, targetValueByState = { if (it) 16.dp else 0.dp })
        Box(
            modifier = Modifier
                .size(width = 3.dp, height = height)
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

    /**
     * Creates a [ListItemColorScheme] with the default colors for a list item.
     *
     * @param default The default colors for the list item.
     * @param hovered The colors for the list item when it is hovered.
     * @param pressed The colors for the list item when it is pressed.
     * @param disabled The colors for the list item when it is disabled.
     * @return A [ListItemColorScheme] with the specified colors.
     */
    @Composable
    @Stable
    fun defaultListItemColors(
        default: ListItemColor = ListItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.primary,
            trailingColor = FluentTheme.colors.text.text.secondary,
            borderBrush = SolidColor(Color.Transparent)
        ),
        hovered: ListItemColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.secondary
        ),
        pressed: ListItemColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.secondary
        ),
        disabled: ListItemColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.disabled,
            contentColor = FluentTheme.colors.text.text.disabled,
            trailingColor = FluentTheme.colors.text.text.disabled,
        )
    ) = ListItemColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [ListItemColorScheme] with colors for a selected list item.
     *
     * @param default The default colors for the list item.
     * @param hovered The colors for the list item when it is hovered.
     * @param pressed The colors for the list item when it is pressed.
     * @param disabled The colors for the list item when it is disabled.
     * @return A [ListItemColorScheme] with the specified colors.
     */
    @Composable
    @Stable
    fun selectedListItemColors(
        default: ListItemColor = ListItemColor(
            fillColor = FluentTheme.colors.subtleFill.secondary,
            contentColor = FluentTheme.colors.text.text.primary,
            trailingColor = FluentTheme.colors.text.text.secondary,
            borderBrush = SolidColor(Color.Transparent)
        ),
        hovered: ListItemColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.tertiary
        ),
        pressed: ListItemColor = default.copy(
            contentColor = FluentTheme.colors.text.text.secondary
        ),
        disabled: ListItemColor = default.copy(
            contentColor = FluentTheme.colors.text.text.disabled,
            trailingColor = FluentTheme.colors.text.text.disabled,
        )
    ) = ListItemColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

/**
 * Represents the selection type for a list item.
 *
 * - [Standard]: Indicates a standard list item with a simple indicator.
 * - [Radio]: Indicates a list item with a radio button-like selection.
 * - [Check]: Indicates a list item with a checkbox-like selection.
 */
enum class ListItemSelectionType {
    Standard, Radio, Check
}

/**
 * Represents the color scheme for a list item in different states.
 *
 * @param fillColor The background color of the list item.
 * @param contentColor The color of the primary content within the list item (e.g., text).
 * @param trailingColor The color of the trailing content within the list item (e.g., icons or secondary text).
 * @param borderBrush The brush used to draw the border around the list item.
 */
@Immutable
data class ListItemColor(
    val fillColor: Color,
    val contentColor: Color,
    val trailingColor: Color,
    val borderBrush: Brush
)

typealias ListItemColorScheme = PentaVisualScheme<ListItemColor>

private val ListItemHeight = 40.dp
private val ListItemCompactHeight = 32.dp