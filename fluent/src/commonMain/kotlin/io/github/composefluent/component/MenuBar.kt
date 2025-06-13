package io.github.composefluent.component

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.layout.overflow.OverflowRow
import io.github.composefluent.layout.overflow.OverflowRowScope
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A composable function that represents a menu bar.
 *
 * The `MenuBar` is a horizontal container that displays a set of [MenuBarItem]s.
 * It is typically used at the top of an application window to provide access to
 * various commands and functionalities.
 *
 * @param modifier The modifier to be applied to the menu bar.
 * @param content The composable content of the menu bar, defined using a [MenuBarScope].
 *
 * Example usage:
 * ```kotlin
 * MenuBar {
 *     MenuBarItem(
 *         items = {
 *             // Menu flyout items
 *         }
 *     ) {
 *         // Menu bar item content
 *     }
 *     // More MenuBarItem
 * }
 * ```
 */
@Composable
fun MenuBar(
    modifier: Modifier = Modifier,
    content: @Composable MenuBarScope.() -> Unit
) {
    val scope = remember { MenuBarItemScopeImpl() }
    Row(
        modifier = modifier.height(MenuBarHeight),
        horizontalArrangement = Arrangement.spacedBy(MenuBarItemSpacing),
        verticalAlignment = Alignment.CenterVertically,
        content = { scope.content() }
    )
}

/**
 * Creates a menu bar item within a [MenuBar].
 *
 * This function represents an individual item within a menu bar. It can be associated with a
 * [MenuFlyout] that displays a dropdown of additional menu items when the item is clicked.
 *
 * @param items A composable lambda function defining the content of the [MenuFlyout] associated
 * with this menu bar item. This lambda will receive a [MenuFlyoutContainerScope] instance.
 * @param modifier The [Modifier] to be applied to the container of this menu bar item.
 * @param content A composable lambda function defining the content of the menu bar item itself,
 * typically a [Text] or an [Icon].
 */
@Composable
fun MenuBarScope.MenuBarItem(
    items: @Composable MenuFlyoutContainerScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val item = registerMenuBarItem(interactionSource)
    Box(modifier = modifier, propagateMinConstraints = true) {
        val containerScope = rememberNavigationItemsFlyoutScope(
            expanded = currentItem == item,
            onExpandedChanged = {
                if (it) {
                    currentItem = item
                } else if (currentItem == item) {
                    currentItem = null
                }
            }
        )
        MenuFlyout(
            visible = containerScope.isFlyoutVisible,
            onDismissRequest = { containerScope.isFlyoutVisible = false },
            positionProvider = rememberFlyoutPositionProvider(
                initialPlacement = FlyoutPlacement.BottomAlignedStart,
                adaptivePlacement = true,
                paddingToAnchor = PaddingValues()
            ),
            content = { items(containerScope) }
        )

        MenuBarButton(
            selected = currentItem == item,
            onClick = { currentItem = item },
            interaction = interactionSource,
            content = { content() }
        )
    }
}

/**
 * Adds a menu bar item to an overflow menu bar.
 *
 * This composable represents a single item within a [OverflowMenuBar]. It displays content
 * defined by the `content` parameter. When clicked, it shows a flyout menu containing
 * items defined by the `items` parameter. The appearance and behavior of the item differ
 * slightly depending on whether it is displayed as a normal item or within the overflow
 * area.
 *
 * When displayed normally, the item is a button that triggers the flyout menu.
 * When in the overflow area, it appears as a list item with a cascading icon and
 * behaves similarly to a nested menu.
 *
 * @param items A composable lambda that defines the items within the flyout menu.
 *              These items are displayed when the menu bar item is clicked.
 *              It provides a [MenuFlyoutContainerScope] for managing the flyout.
 * @param modifier Modifier to be applied to the menu bar item.
 * @param content A composable lambda that defines the content of the menu bar item
 *                (e.g., text, icon). This content is displayed as a button or list
 *                item, depending on whether it is in the overflow area.
 */
@Composable
fun OverflowMenuBarItemScope.MenuBarItem(
    items: @Composable MenuFlyoutContainerScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val item = registerMenuBarItem(interactionSource)
    Box(modifier = modifier, propagateMinConstraints = true) {
        val containerScope = rememberNavigationItemsFlyoutScope(
            expanded = currentItem == item,
            onExpandedChanged = {
                if (it) {
                    currentItem = item
                } else if (currentItem == item) {
                    currentItem = null
                }
            }
        )
        if (!isOverflow) {
            MenuFlyout(
                visible = containerScope.isFlyoutVisible,
                onDismissRequest = { containerScope.isFlyoutVisible = false },
                positionProvider = rememberFlyoutPositionProvider(
                    initialPlacement = FlyoutPlacement.BottomAlignedStart,
                    adaptivePlacement = true,
                    paddingToAnchor = PaddingValues()
                ),
                content = { items(containerScope) }
            )

            MenuBarButton(
                selected = currentItem == item,
                onClick = { currentItem = item },
                interaction = interactionSource,
                content = { content() }
            )
        } else {

            Box(modifier = modifier, propagateMinConstraints = true) {
                val containerScope = rememberNavigationItemsFlyoutScope(
                    expanded = currentItem == item,
                    onExpandedChanged = {
                        if (it) {
                            currentItem = item
                        } else if (currentItem == item) {
                            currentItem = null
                        }
                    }
                )
                val paddingTop = with(LocalDensity.current) { flyoutPopPaddingFixShadowRender.roundToPx() }
                MenuFlyout(
                    visible = containerScope.isFlyoutVisible,
                    onDismissRequest = { containerScope.isFlyoutVisible = false },
                    positionProvider = rememberSubMenuFlyoutPositionProvider(),
                    enterPlacementAnimation = {
                        defaultMenuFlyoutEnterPlacementAnimation(it, paddingTop)
                    },
                    content = { items(containerScope) }
                )

                ListItem(
                    onClick = { currentItem = item },
                    trailing = { ListItemDefaults.CascadingIcon() },
                    text = { content() },
                    interaction = interactionSource,
                    colors = if (currentItem == item) {
                        ListItemDefaults.selectedListItemColors()
                    } else {
                        ListItemDefaults.defaultListItemColors()
                    }
                )
            }
        }
    }
}

/**
 * A composable function that displays a menu bar with overflow functionality.
 *
 * This function creates a menu bar that can handle a variable number of menu items.
 * When the available space is insufficient to display all items, it automatically
 * moves the overflowing items into an overflow menu, accessible via a "More" button.
 *
 * @param modifier The modifier to apply to the menu bar.
 * @param content The composable content to display within the menu bar.
 *                This lambda receives an [OverflowMenuBarScope] which provides
 *                functions to add menu items to the bar, including items that
 *                may be placed in the overflow menu.
 */
@Composable
fun OverflowMenuBar(
    modifier: Modifier = Modifier,
    content: OverflowMenuBarScope.() -> Unit
) {
    val menuBarScope = remember { MenuBarItemScopeImpl() }
    val overflowItems = remember { mutableListOf<MenuBarItem>() }
    OverflowRow(
        modifier = modifier.height(MenuBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MenuBarItemSpacing),
        overflowAction = {
            var expanded by remember { mutableStateOf(false) }
            Box {
                MenuFlyout(
                    visible = expanded,
                    onDismissRequest = { expanded = false },
                    positionProvider = rememberFlyoutPositionProvider(
                        initialPlacement = FlyoutPlacement.BottomAlignedEnd,
                        adaptivePlacement = false,
                        paddingToAnchor = PaddingValues()
                    ),
                    content = {
                        repeat(overflowItemCount) { index ->
                            overflowItem(index)
                        }
                    }
                )
                val interactionSource = remember { MutableInteractionSource() }
                val item = menuBarScope.registerMenuBarItem(interactionSource)
                DisposableEffect(item, overflowItems) {
                    overflowItems.add(item)
                    onDispose {
                        overflowItems.remove(item)
                    }
                }
                LaunchedEffect(menuBarScope.currentItem, overflowItems) {
                    if (menuBarScope.currentItem != null) {
                        expanded = menuBarScope.currentItem in overflowItems
                    }
                }
                MenuBarButton(
                    selected = expanded,
                    onClick = { menuBarScope.currentItem = item },
                    interaction = interactionSource,
                    content = {
                        FontIcon(
                            type = FontIconPrimitive.More,
                            contentDescription = null,
                        )
                    },
                )
            }
        }
    ) {
        OverflowMenuBarScopeImpl(this, overflowItems, menuBarScope).content()
    }
}

/**
 * A button for use within a [MenuBar].
 *
 * @param selected whether the button is currently selected. This should be used in conjunction with the [onClick]
 *  to control when to change the selected state.
 * @param onClick callback to be invoked when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param interaction the [MutableInteractionSource] representing the stream of [androidx.compose.foundation.interaction.Interaction]s
 *  for this button. You can create and pass in your own remembered [MutableInteractionSource] if desired.
 * @param buttonColors the [ButtonColorScheme] that will be used to resolve the background and content colors for this button in different states.
 * See [MenuBarDefaults.selectedItemColors] and [MenuBarDefaults.defaultItemColors] for the default values.
 * @param enabled controls the enabled state of this button. When `false`, this component will not respond to user input, and it will appear visually disabled and disabled to accessibility services.
 * @param content the content of this button, such as a [Text] or [Icon]
 */
@Composable
fun MenuBarButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interaction: MutableInteractionSource = remember { MutableInteractionSource() },
    buttonColors: ButtonColorScheme = if (selected) {
        MenuBarDefaults.selectedItemColors()
    } else {
        MenuBarDefaults.defaultItemColors()
    },
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    SubtleButton(
        interaction = interaction,
        buttonColors = buttonColors,
        onClick = onClick,
        content = { content() },
        disabled = !enabled,
        modifier = modifier
    )
}

/**
 * Contains the default values used for [MenuBarButton].
 */
object MenuBarDefaults {

    /**
     * Creates a [ButtonColorScheme] with colors for a selected menu bar item.
     *
     * @param default The color to use in the default state.
     * @param hovered The color to use when the button is hovered.
     * @param pressed The color to use when the button is pressed.
     * @param disabled The color to use when the button is disabled.
     * @return A [ButtonColorScheme] containing the specified colors.
     */
    @Stable
    @Composable
    fun selectedItemColors(
        default: ButtonColor = ButtonColor(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.primary,
            borderBrush = SolidColor(Color.Transparent)
        ),
        hovered: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.secondary,
        ),
        pressed: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.secondary
        ),
        disabled: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.disabled,
            contentColor = FluentTheme.colors.text.text.disabled
        )
    ) = ButtonColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled,
    )

    /**
     * Creates a [ButtonColorScheme] with the default colors used for a [MenuBarButton] when it is not
     * selected.
     *
     * @param default The color to use in the default state.
     * @param hovered The color to use when the button is hovered.
     * @param pressed The color to use when the button is pressed.
     * @param disabled The color to use when the button is disabled.
     * @return A [ButtonColorScheme] containing the specified colors.
     */
    @Stable
    @Composable
    fun defaultItemColors(
        default: ButtonColor = ButtonColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.primary,
            borderBrush = SolidColor(Color.Transparent)
        ),
        hovered: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.secondary,
        ),
        pressed: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.secondary
        ),
        disabled: ButtonColor = default.copy(
            fillColor = FluentTheme.colors.subtleFill.disabled,
            contentColor = FluentTheme.colors.text.text.disabled
        )
    ) = ButtonColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled,
    )
}

/**
 * Scope for the content of a [MenuBar].
 *
 * This scope is used within the `content` lambda of the [MenuBar] composable
 * function to define the items that will appear in the menu bar.
 * It provides the context needed to add [MenuBarItem]s to the bar.
 */
interface MenuBarScope : MenuBarItemScope

/**
 * Scope for adding items to an [OverflowMenuBar].
 *
 * This scope provides the ability to add individual items using [item] and
 * collections of items using [items]. These items will be displayed in the
 * menu bar, and when there is not enough space, they will be moved to an
 * overflow menu, accessible via a "More" button.
 */
interface OverflowMenuBarScope {
    fun item(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable OverflowMenuBarItemScope.() -> Unit
    )

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable OverflowMenuBarItemScope.(index: Int) -> Unit
    )
}

@Immutable
private class OverflowMenuBarScopeImpl(
    private val rowScope: OverflowRowScope,
    overflowItems: MutableList<MenuBarItem>,
    menuBarScope: MenuBarItemScope = MenuBarItemScopeImpl(),
) : OverflowMenuBarScope {
    private val normalItemScope = NormalItemScope(menuBarScope)
    private val overflowItemScope = OverflowItemScope(overflowItems, menuBarScope)

    override fun items(
        count: Int,
        key: ((Int) -> Any)?,
        contentType: (Int) -> Any?,
        itemContent: @Composable (OverflowMenuBarItemScope.(Int) -> Unit)
    ) {
        rowScope.items(count, key, contentType) {
            if (isOverflow) {
                overflowItemScope.itemContent(it)
            } else {
                normalItemScope.itemContent(it)
            }
        }
    }

    override fun item(
        key: Any?,
        contentType: Any?,
        content: @Composable OverflowMenuBarItemScope.() -> Unit
    ) {
        rowScope.item(key, contentType) {
            if (isOverflow) {
                overflowItemScope.content()
            } else {
                normalItemScope.content()
            }
        }
    }

    class NormalItemScope(
        private val itemScopeDelegate: MenuBarItemScope
    ) : OverflowMenuBarItemScope, MenuBarItemScope by itemScopeDelegate {
        override val isOverflow: Boolean
            get() = false
    }

    class OverflowItemScope(
        private val items: MutableList<MenuBarItem>,
        private val itemScopeDelegate: MenuBarItemScope
    ) : OverflowMenuBarItemScope, MenuBarItemScope by itemScopeDelegate {
        override val isOverflow: Boolean
            get() = true

        @Composable
        override fun registerMenuBarItem(interactionSource: InteractionSource): MenuBarItem {
            val item = itemScopeDelegate.registerMenuBarItem(interactionSource)
            DisposableEffect(items, item) {
                items.add(item)
                onDispose {
                    items.remove(item)
                }
            }
            return item
        }
    }
}

/**
 * Scope for items within an [OverflowMenuBar].
 *
 * This scope provides information about whether the current item is within the overflow area.
 * This is useful for customizing the appearance and behavior of [MenuBarItem]s depending on
 * whether they are displayed directly in the menu bar or within the overflow dropdown.
 *
 * @property isOverflow `true` if the current item is in the overflow area, `false` otherwise.
 */
interface OverflowMenuBarItemScope : MenuBarItemScope {
    val isOverflow: Boolean
}

/**
 * Scope for registering items within a [MenuBar].
 *
 * This interface provides functions for managing the state of menu items in a menu bar.
 * It allows for tracking the currently active item and registering new items.
 *
 * The [currentItem] property represents the currently selected or active menu bar item.
 *
 * The [registerMenuBarItem] function is used to create and register a new [MenuBarItem]
 * within the scope of a menu bar. This function also takes an [InteractionSource] to
 * handle user interactions with the menu bar item.
 */
interface MenuBarItemScope {

    /**
     * The currently selected [MenuBarItem].
     *
     * This property represents the [MenuBarItem] that is currently active or selected within the scope.
     * It is used to determine which [MenuBarItem] should display its associated [MenuFlyout] or to
     * highlight the currently selected item in the UI.
     *
     * Setting this to `null` means no item is currently selected.
     */
    var currentItem: MenuBarItem?

    /**
     * Registers a new menu bar item within this scope.
     *
     * This function is used internally to create and manage menu bar items.
     * It associates an [InteractionSource] with a unique [MenuBarItem] instance.
     * When the mouse hovers over a registered item while another is selected, it automatically
     * switches the selected item to the hovered one.
     *
     * @param interactionSource The interaction source associated with the menu bar item.
     * @return A unique [MenuBarItem] instance representing the registered item.
     */
    @Composable
    fun registerMenuBarItem(
        interactionSource: InteractionSource,
    ): MenuBarItem
}

/**
 * Represents a unique item within a [MenuBar].
 *
 * This class is an inline value class that wraps a [Uuid] to uniquely identify
 * each menu bar item. It is used internally to manage the state and behavior
 * of menu items within a [MenuBar], such as tracking which item is currently
 * selected or has an associated flyout expanded.
 *
 * Note: This class is primarily for internal use within the MenuBar implementation
 * and should not typically be used directly by application code.
 */
@JvmInline
@OptIn(ExperimentalUuidApi::class)
value class MenuBarItem internal constructor(private val uuid: Uuid)

@OptIn(ExperimentalUuidApi::class)
private class MenuBarItemScopeImpl() : MenuBarScope {

    override var currentItem: MenuBarItem? by mutableStateOf(null)

    @Composable
    override fun registerMenuBarItem(
        interactionSource: InteractionSource
    ): MenuBarItem {
        val isHovered by interactionSource.collectIsHoveredAsState()
        val menuBarItem = remember {
            MenuBarItem(Uuid.random())
        }
        LaunchedEffect(isHovered, menuBarItem) {
            if (currentItem != null && currentItem != menuBarItem && isHovered) {
                currentItem = menuBarItem
            }
        }
        return menuBarItem
    }
}

private val MenuBarItemSpacing = 8.dp
private val MenuBarHeight = 48.dp