package io.github.composefluent.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.layout.overflow.OverflowActionScope
import io.github.composefluent.layout.overflow.OverflowFlyoutContainer
import io.github.composefluent.layout.overflow.OverflowPosition
import io.github.composefluent.layout.overflow.OverflowRow
import io.github.composefluent.layout.overflow.OverflowRowScope
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualState
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A composable function that displays a breadcrumb bar, which is a navigation aid that
 * shows the user's current location within a hierarchy.
 *
 * @param modifier The modifier to be applied to the breadcrumb bar.
 * @param overflow The position where the overflow items should be displayed, defaults to [OverflowPosition.Start].
 * @param horizontalArrangement The horizontal arrangement of the breadcrumb items, defaults to [Arrangement.Start].
 * @param verticalAlignment The vertical alignment of the breadcrumb items, defaults to [Alignment.CenterVertically].
 * @param overflowAction A composable function that defines the action to be taken when there is an overflow.
 *   Defaults to showing an overflow tag that, when clicked, displays a flyout with the hidden items.
 *   The scope of this function is [OverflowActionScope], which provides access to `isFlyoutVisible`.
 * @param items A composable function that defines the breadcrumb items. The scope of this function is
 *   [OverflowRowScope], which provides functions for creating breadcrumb items and managing their overflow.
 */
@Composable
fun BreadcrumbBar(
    modifier: Modifier = Modifier,
    overflow: OverflowPosition = OverflowPosition.Start,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    overflowAction: @Composable OverflowActionScope.() -> Unit = {
        OverflowFlyoutContainer {
            BreadcrumbBarDefaults.OverflowTag(onClick = {
                isFlyoutVisible = true
            })
        }
    },
    items: OverflowRowScope.() -> Unit
) {
    OverflowRow(
        overflow = overflow,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        overflowAction = overflowAction,
        content = items,
        modifier = modifier
    )
}

/**
 * Contains the default values used by [BreadcrumbBar].
 *
 * This includes:
 * - Default colors for regular breadcrumb items ([breadcrumbItemColors] and [breadcrumbItemInactiveColors])
 * - Default colors for large breadcrumb items ([largeBreadcrumbItemColors] and [largeBreadcrumbItemInactiveColors])
 * - Default colors for the overflow tag ([overflowTagColors])
 * - Default composable for the overflow tag ([OverflowTag] and [LargeOverflowTag])
 */
object BreadcrumbBarDefaults {

    /**
     * Creates a [PentaVisualScheme] for the colors of a breadcrumb item.
     *
     * @param default The default color of the breadcrumb item.
     * @param hovered The color of the breadcrumb item when it is hovered.
     * @param pressed The color of the breadcrumb item when it is pressed.
     * @param disabled The color of the breadcrumb item when it is disabled.
     * @return A [PentaVisualScheme] containing the specified colors for the breadcrumb item.
     */
    @Composable
    fun breadcrumbItemColors(
        default: Color = FluentTheme.colors.text.text.primary,
        hovered: Color = FluentTheme.colors.text.text.secondary,
        pressed: Color = FluentTheme.colors.text.text.tertiary,
        disabled: Color = FluentTheme.colors.text.text.disabled,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [PentaVisualScheme] for inactive breadcrumb bar items.
     *
     * This function defines the colors for the different visual states of an inactive breadcrumb item,
     * including its default, hovered, pressed, and disabled states.
     *
     * @param default The default color of the text.
     * @param hovered The color of the text when hovered over.
     * @param pressed The color of the text when pressed.
     * @param disabled The color of the text when disabled.
     * @return A [PentaVisualScheme] containing the specified colors for each state.
     */
    @Composable
    fun breadcrumbItemInactiveColors(
        default: Color = FluentTheme.colors.text.text.primary,
        hovered: Color = FluentTheme.colors.text.text.secondary,
        pressed: Color = FluentTheme.colors.text.text.tertiary,
        disabled: Color = FluentTheme.colors.text.text.disabled,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [PentaVisualScheme] for large breadcrumb items with the specified colors.
     *
     * @param default The default color of the text.
     * @param hovered The color of the text when hovered.
     * @param pressed The color of the text when pressed.
     * @param disabled The color of the text when disabled.
     * @return A [PentaVisualScheme] that defines the color for each visual state of the breadcrumb item.
     */
    @Composable
    fun largeBreadcrumbItemColors(
        default: Color = FluentTheme.colors.text.text.primary,
        hovered: Color = FluentTheme.colors.text.text.secondary,
        pressed: Color = FluentTheme.colors.text.text.tertiary,
        disabled: Color = FluentTheme.colors.text.text.disabled,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [PentaVisualScheme] for inactive large breadcrumb items.
     *
     * This function defines the colors for a large breadcrumb item when it is inactive,
     * meaning it is not the currently active breadcrumb in the navigation path.
     * It provides colors for the default, hovered, pressed, and disabled states.
     *
     * @param default The color of the item in its default state. Defaults to `FluentTheme.colors.text.text.secondary`.
     * @param hovered The color of the item when the pointer is over it. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param pressed The color of the item when it is pressed. Defaults to `FluentTheme.colors.text.text.tertiary`.
     * @param disabled The color of the item when it is disabled. Defaults to `FluentTheme.colors.text.text.disabled`.
     * @return A [PentaVisualScheme] that defines the colors for each state of the inactive large breadcrumb item.
     */
    @Composable
    fun largeBreadcrumbItemInactiveColors(
        default: Color = FluentTheme.colors.text.text.secondary,
        hovered: Color = FluentTheme.colors.text.text.primary,
        pressed: Color = FluentTheme.colors.text.text.tertiary,
        disabled: Color = FluentTheme.colors.text.text.disabled,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Defines the color scheme for the overflow tag in the breadcrumb bar.
     *
     * @param default The default color of the overflow tag. Defaults to `FluentTheme.colors.text.text.secondary`.
     * @param hovered The color of the overflow tag when it is hovered over. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param pressed The color of the overflow tag when it is pressed. Defaults to `FluentTheme.colors.text.text.tertiary`.
     * @param disabled The color of the overflow tag when it is disabled. Defaults to `FluentTheme.colors.text.text.disabled`.
     * @return A [PentaVisualScheme] representing the color scheme for the overflow tag.
     */
    @Composable
    fun overflowTagColors(
        default: Color = FluentTheme.colors.text.text.secondary,
        hovered: Color = FluentTheme.colors.text.text.primary,
        pressed: Color = FluentTheme.colors.text.text.tertiary,
        disabled: Color = FluentTheme.colors.text.text.disabled,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * A composable function that displays an overflow tag in a breadcrumb bar.
     *
     * This tag is typically used when there are more breadcrumb items than can fit
     * in the available space. Clicking this tag will usually reveal the hidden items
     * in a flyout or dropdown menu.
     *
     * @param modifier The modifier to be applied to the overflow tag.
     * @param colorScheme The color scheme to be used for the overflow tag. Defaults to [overflowTagColors].
     * @param chevronVisible Whether the chevron icon should be visible. Defaults to `true`.
     * @param enabled Whether the overflow tag is enabled. Defaults to `true`.
     * @param onClick The callback to be invoked when the overflow tag is clicked.
     */
    @Composable
    fun OverflowTag(
        modifier: Modifier = Modifier,
        colorScheme: VisualStateScheme<Color> = overflowTagColors(),
        chevronVisible: Boolean = true,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        BreadcrumbBarItem(
            onClick = onClick,
            colorScheme = colorScheme,
            chevronVisible = chevronVisible,
            modifier = modifier,
            enabled = enabled
        ) {
            FontIcon(
                type = FontIconPrimitive.More,
                contentDescription = null,
                size = FontIconSize.Small
            )
        }
    }

    /**
     * A large overflow tag that can be used in a [BreadcrumbBar] to indicate that there are
     * more items than can be displayed. When clicked, it typically shows a flyout menu containing
     * the hidden items.
     *
     * @param modifier The modifier to be applied to the overflow tag.
     * @param colorScheme The color scheme for the overflow tag. Defaults to [overflowTagColors].
     * @param chevronVisible Whether the chevron icon should be visible. Defaults to `true`.
     * @param enabled Whether the overflow tag is enabled. Defaults to `true`.
     * @param onClick The callback to be invoked when the overflow tag is clicked.
     */
    @Composable
    fun LargeOverflowTag(
        modifier: Modifier = Modifier,
        colorScheme: VisualStateScheme<Color> = overflowTagColors(),
        chevronVisible: Boolean = true,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) {
        LargeBreadcrumbBarItem(
            onClick = onClick,
            colorScheme = colorScheme,
            chevronVisible = chevronVisible,
            modifier = modifier,
            enabled = enabled
        ) {
            FontIcon(
                type = FontIconPrimitive.More,
                contentDescription = null,
                size = FontIconSize.Standard
            )
        }
    }
}

/**
 * A composable function that represents a single item within a [BreadcrumbBar].
 *
 * @param onClick The callback to be invoked when the breadcrumb item is clicked.
 * @param modifier The modifier to be applied to the breadcrumb item.
 * @param chevronVisible Whether the chevron (right-arrow) should be visible. Defaults to `true`.
 * @param enabled Whether the breadcrumb item is enabled for interaction. Defaults to `true`.
 * @param inactive Whether the breadcrumb item should be displayed as inactive (different color scheme). Defaults to `false`.
 * @param colorScheme The color scheme to be used for the breadcrumb item's text and content. Defaults to [BreadcrumbBarDefaults.breadcrumbItemInactiveColors] if [inactive] is true, otherwise [BreadcrumbBarDefaults.breadcrumbItemColors].
 * @param chevronColors The color scheme to be used for the chevron. Defaults to [BreadcrumbBarDefaults.overflowTagColors].
 * @param content The composable content to be displayed within the breadcrumb item (e.g., text, icon).
 */
@Composable
fun BreadcrumbBarItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    chevronVisible: Boolean = true,
    enabled: Boolean = true,
    inactive: Boolean = false,
    colorScheme: VisualStateScheme<Color> = if (inactive) {
        BreadcrumbBarDefaults.breadcrumbItemInactiveColors()
    } else {
        BreadcrumbBarDefaults.breadcrumbItemColors()
    },
    chevronColors: VisualStateScheme<Color> = BreadcrumbBarDefaults.overflowTagColors(),
    content: @Composable () -> Unit
) {
    BasicBreadcrumbBarItem(
        onClick = onClick,
        colorScheme = colorScheme,
        chevronColors = chevronColors,
        textStyle = FluentTheme.typography.body,
        chevronSize = FontIconSize.Small,
        modifier = modifier,
        chevronVisible = chevronVisible,
        enabled = enabled,
        content = content
    )
}

/**
 * A composable function that displays a large breadcrumb bar item, which is a
 * navigation aid that shows the user's current location within a hierarchy.
 * This item is designed to be used with a larger font size and icon size.
 *
 * @param onClick The callback to be invoked when this breadcrumb item is clicked.
 * @param modifier The modifier to be applied to the breadcrumb item.
 * @param chevronVisible Determines whether the chevron (arrow) is visible. Defaults to `true`.
 * @param enabled Determines whether this breadcrumb item is enabled for interaction. Defaults to `true`.
 * @param inactive Determines whether this breadcrumb item is in an inactive state, such as being a non-clickable part of the path. Defaults to `false`.
 * @param colorScheme The color scheme to use for the text of the breadcrumb item.
 *  If [inactive] is `true`, [BreadcrumbBarDefaults.largeBreadcrumbItemInactiveColors] will be used,
 *  otherwise [BreadcrumbBarDefaults.largeBreadcrumbItemColors] will be used.
 * @param chevronColors The color scheme to use for the chevron of the breadcrumb item.
 *  Defaults to [BreadcrumbBarDefaults.overflowTagColors].
 * @param content The content to be displayed inside the breadcrumb item (e.g., Text, Icon).
 */
@Composable
fun LargeBreadcrumbBarItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    chevronVisible: Boolean = true,
    enabled: Boolean = true,
    inactive: Boolean = false,
    colorScheme: VisualStateScheme<Color> = if (inactive) {
        BreadcrumbBarDefaults.largeBreadcrumbItemInactiveColors()
    } else {
        BreadcrumbBarDefaults.largeBreadcrumbItemColors()
    },
    chevronColors: VisualStateScheme<Color> = BreadcrumbBarDefaults.overflowTagColors(),
    content: @Composable () -> Unit
) {
    BasicBreadcrumbBarItem(
        onClick = onClick,
        colorScheme = colorScheme,
        chevronColors = chevronColors,
        textStyle = FluentTheme.typography.title,
        chevronSize = FontIconSize.Standard,
        modifier = modifier,
        chevronVisible = chevronVisible,
        enabled = enabled,
        content = content
    )
}

/**
 * A breadcrumb bar item designed to be used within an overflow context.
 * This item is displayed in the overflow menu/flyout.
 *
 * @param onClick The callback to be invoked when this item is clicked.
 * @param modifier The modifier to be applied to this item.
 * @param enabled Controls the enabled state of this item. When `false`, this item will not be clickable.
 * @param interaction The [MutableInteractionSource] representing the stream of [Interaction]s for this item.
 * @param colors The colors used to represent the different states of this item.
 * @param content The content of this breadcrumb item.
 */
@Composable
fun OverflowBreadcrumbBarItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interaction: MutableInteractionSource? = null,
    colors: VisualStateScheme<ListItemColor> = ListItemDefaults.defaultListItemColors(),
    content: @Composable () -> Unit
) {
    ListItem(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interaction = interaction,
        text = content,
        colors = colors
    )
}

@Composable
private fun BasicBreadcrumbBarItem(
    onClick: () -> Unit,
    colorScheme: VisualStateScheme<Color>,
    chevronColors: VisualStateScheme<Color>,
    textStyle: TextStyle,
    chevronSize: FontIconSize,
    modifier: Modifier = Modifier,
    chevronVisible: Boolean = true,
    enabled: Boolean = true,
    interaction: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    val targetInteraction = interaction ?: remember { MutableInteractionSource() }
    val visualState = targetInteraction.collectVisualState(!enabled)
    val currentColor = colorScheme.schemeFor(visualState)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.clickable(
                onClick = onClick,
                indication = null,
                interactionSource = targetInteraction
            )
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentColor,
                LocalContentAlpha provides currentColor.alpha,
                LocalTextStyle provides LocalTextStyle.current.merge(textStyle.copy(color = currentColor)),
                content = content
            )
        }
        if (chevronVisible) {
            val chevronColor = if (enabled) {
                chevronColors.schemeFor(VisualState.Default)
            } else {
                chevronColors.schemeFor(VisualState.Disabled)
            }
            CompositionLocalProvider(
                LocalContentColor provides chevronColor,
                LocalContentAlpha provides chevronColor.alpha,
                LocalTextStyle provides LocalTextStyle.current.copy(chevronColor)
            ) {
                FontIcon(
                    type = FontIconPrimitive.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    size = chevronSize
                )
            }
        }
    }
}