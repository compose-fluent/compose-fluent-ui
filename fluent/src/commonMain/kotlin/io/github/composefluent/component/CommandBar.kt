package io.github.composefluent.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentColor
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.layout.alignLast
import io.github.composefluent.layout.overflow.OverflowActionScope
import io.github.composefluent.layout.overflow.OverflowRow
import io.github.composefluent.layout.overflow.OverflowRowScope
import io.github.composefluent.layout.overflow.rememberOverflowRowState

/**
 * A standard command bar that can be expanded to show more options.
 *
 * @param expanded Whether the command bar is expanded.
 * @param onExpandedChanged Callback to be invoked when the expanded state changes.
 * @param modifier Modifier for the command bar.
 * @param color The background color of the command bar. When not expanded, this color is transparent.
 * @param border The border of the command bar. When not expanded, this is null.
 * @param placement The placement of the expanded flyout relative to the command bar.
 * @param secondary The secondary content of the expanded flyout. This is typically used for overflow actions.
 *  It receives a [MenuFlyoutScope] and a boolean indicating whether there are overflow items.
 * @param spacing The spacing between items in the command bar.
 * @param content The content of the command bar, using the [OverflowRowScope].
 */
@Composable
fun CommandBar(
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = if (expanded) {
        FluentTheme.colors.background.acrylic.default
    } else {
        FluentTheme.colors.background.acrylic.default.copy(0f)
    },
    border: BorderStroke? = if (expanded) {
        BorderStroke(1.dp, FluentTheme.colors.stroke.surface.flyout)
    } else {
        null
    },
    placement: FlyoutPlacement = FlyoutPlacement.BottomAlignedEnd,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)? = null,
    spacing: Dp = 0.dp,
    content: OverflowRowScope.() -> Unit
) {
    BasicCommandBar(
        expanded = expanded,
        onExpandedChanged = onExpandedChanged,
        modifier = modifier,
        color = color,
        border = border,
        placement = placement,
        secondary = secondary,
        isLarge = false,
        content = content,
        spacing = spacing
    )
}

/**
 * A large command bar that can be expanded to show more options, typically used when more vertical space is available.
 *
 * @param expanded Whether the command bar is expanded.
 * @param onExpandedChanged Callback to be invoked when the expanded state changes.
 * @param modifier Modifier for the command bar.
 * @param color The background color of the command bar. When not expanded, this color is transparent.
 * @param border The border of the command bar. When not expanded, this is null.
 * @param placement The placement of the expanded flyout relative to the command bar.
 * @param secondary The secondary content of the expanded flyout. This is typically used for overflow actions.
 *  It receives a [MenuFlyoutScope] and a boolean indicating whether there are overflow items.
 * @param spacing The spacing between items in the command bar.
 * @param content The content of the command bar, using the [OverflowRowScope].
 */
@Composable
fun LargeCommandBar(
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = if (expanded) {
        FluentTheme.colors.background.acrylic.default
    } else {
        FluentTheme.colors.background.acrylic.default.copy(0f)
    },
    border: BorderStroke? = if (expanded) {
        BorderStroke(1.dp, FluentTheme.colors.stroke.surface.flyout)
    } else {
        null
    },
    placement: FlyoutPlacement = FlyoutPlacement.BottomAlignedEnd,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)? = null,
    spacing: Dp = 0.dp,
    content: OverflowRowScope.() -> Unit
) {
    BasicCommandBar(
        expanded = expanded,
        onExpandedChanged = onExpandedChanged,
        modifier = modifier,
        color = color,
        border = border,
        placement = placement,
        secondary = secondary,
        isLarge = true,
        content = content,
        spacing = spacing
    )
}

@Composable
private fun BasicCommandBar(
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier,
    color: Color,
    border: BorderStroke?,
    placement: FlyoutPlacement,
    isLarge: Boolean,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)?,
    spacing: Dp,
    content: OverflowRowScope.() -> Unit
) {
    val currentColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
    )
    val state = rememberOverflowRowState()
    val scope = remember(content) { mutableStateOf<OverflowActionScope?>(null) }
    Layer(
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        modifier = modifier.height(
            if (isLarge) {
                CommandBarDefaults.CommandBarLargeHeight
            } else {
                CommandBarDefaults.CommandBarStandardHeight
            }
        ),
        color = currentColor,
        contentColor = LocalContentColor.current,
        border = border
    ) {
        val arrangement = Arrangement.spacedBy(spacing, Alignment.Start)
        OverflowRow(
            state = state,
            horizontalArrangement = if (!state.overflowRange.isEmpty() || secondary != null) {
                Arrangement.alignLast(arrangement, Alignment.End)
            } else {
                arrangement
            },
            overflowAction = {
                CommandBarMoreButton(isLarge = isLarge) {
                    onExpandedChanged(true)
                }
                SideEffect {
                    scope.value = this@OverflowRow
                }
            },
            content = content,
            alwaysShowOverflowAction = secondary != null,
            contentPadding = PaddingValues(horizontal = 8.dp)
        )
        MenuFlyout(
            visible = expanded,
            onDismissRequest = {
                onExpandedChanged(false)
            },
            positionProvider = rememberFlyoutPositionProvider(
                initialPlacement = placement,
                paddingToAnchor = PaddingValues()
            ),
            content = {
                scope.value?.apply {
                    repeat(overflowItemCount) {
                        overflowItem(it)
                    }
                }
                secondary?.invoke(this, state.overflowRange.isEmpty().not())
            }
        )
    }

}

/**
 * A button within a command bar.
 *
 * @param onClick The callback to be invoked when the button is clicked.
 * @param modifier Modifier for the button.
 * @param buttonColors The colors to use for the button.
 * @param outsideBorder Whether the button should have a border that extends beyond the button's bounds.
 * @param enabled Whether the button is enabled.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own remembered [MutableInteractionSource] if
 * you want to observe [Interaction]s and customize the appearance / behavior of this button in
 * different [Interaction]s.
 * @param content The content of the button. Typically this is text and/or an icon.
 */
@Composable
fun CommandBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColorScheme = ButtonDefaults.subtleButtonColors(),
    outsideBorder: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    val targetInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    ButtonLayer(
        buttonColors = buttonColors,
        disabled = !enabled,
        modifier = modifier.defaultMinSize(
            minHeight = CommandBarDefaults.CommandBarLargeButtonHeight,
            minWidth = CommandBarDefaults.CommandBarButtonWidth
        ),
        accentButton = !outsideBorder,
        shape = FluentTheme.shapes.control,
        interaction = targetInteractionSource,
        displayBorder = true,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(
                    interactionSource = targetInteractionSource,
                    indication = null,
                    onClick = onClick,
                    enabled = enabled
                )
            ) {
                content()
            }
        }
    )
}

@Composable
internal fun CommandBarMoreButton(isLarge: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
        SubtleButton(
            onClick = onClick,
            iconOnly = true,
            content = {
                FontIcon(type = FontIconPrimitive.More, contentDescription = null)
            },
            modifier = if (isLarge) {
                Modifier.sizeIn(
                    minHeight = CommandBarDefaults.CommandBarLargeButtonHeight,
                    minWidth = CommandBarDefaults.CommandBarIconButtonWidth
                )
            } else {
                Modifier.commandBarIconButtonSize()
            }
        )
    }
}

/**
 * A vertical separator for use in a [CommandBar].
 *
 * @param modifier Modifier for the separator.
 */
@Composable
fun CommandBarSeparator(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 1.dp, end = 2.dp, top = 8.dp, bottom = 8.dp)
            .width(1.dp)
            .background(FluentTheme.colors.stroke.divider.default)
    )
}

/**
 * Applies the default size constraints for an icon button within a command bar.
 *
 * This modifier sets the minimum height and width for the icon button
 * to match the standard dimensions defined by `CommandBarDefaults`.
 *
 * @return A [Modifier] with the size constraints applied.
 */
fun Modifier.commandBarIconButtonSize() = then(
    Modifier.sizeIn(
        minHeight = CommandBarDefaults.CommandBarButtonHeight,
        minWidth = CommandBarDefaults.CommandBarIconButtonWidth
    )
)

/**
 * Applies the default size constraints for a standard command bar button.
 *
 * This modifier sets the minimum height and width of the button according to the
 * [CommandBarDefaults.CommandBarButtonHeight] and [CommandBarDefaults.CommandBarButtonWidth]
 * values.
 *
 * @see CommandBarDefaults.CommandBarButtonHeight
 * @see CommandBarDefaults.CommandBarButtonWidth
 */
fun Modifier.commandBarButtonSize() = then(
    Modifier.sizeIn(
        minHeight = CommandBarDefaults.CommandBarButtonHeight,
        minWidth = CommandBarDefaults.CommandBarButtonWidth
    )
)

/**
 * Default values for the [CommandBar] and [LargeCommandBar].
 */
object CommandBarDefaults {
    /**
     * The default width for an icon-only button in the command bar.
     */
    val CommandBarIconButtonWidth = 36.dp
    /**
     * The default width for a standard command bar button.
     */
    val CommandBarButtonWidth = 64.dp
    /**
     * The height of a large button in the command bar.
     */
    val CommandBarLargeButtonHeight = 52.dp
    /**
     * The default height for a standard command bar button.
     */
    val CommandBarButtonHeight = 36.dp

    /**
     * The standard height of the command bar.
     */
    val CommandBarStandardHeight = 48.dp

    /**
     * Container height for large command bars, intended for use with icon and text label combinations.
     */
    val CommandBarLargeHeight = 64.dp
}