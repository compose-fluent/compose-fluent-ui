package io.github.composefluent.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.layout.FixedWidthLayout
import io.github.composefluent.layout.alignLast
import io.github.composefluent.layout.overflow.OverflowActionScope
import io.github.composefluent.layout.overflow.OverflowRow
import io.github.composefluent.layout.overflow.OverflowRowScope
import io.github.composefluent.layout.overflow.rememberOverflowRowState

/**
 * A flyout that displays a command bar.
 *
 * @param visible Whether the flyout is visible.
 * @param onDismissRequest Called when the user requests to dismiss the flyout.
 * @param expanded Whether the flyout is expanded to show the secondary items.
 * @param onExpandedChanged Called when the expanded state changes.
 * @param modifier The modifier to apply to the flyout.
 * @param secondary The content to display in the secondary area when the flyout is expanded.
 *  the parameter [hasOverFlowItem] is true when [content] has items that overflow.
 * @param spacing The spacing between items in the command bar.
 * @param positionProvider The position provider for the flyout.
 * @param content The primary content to display in the command bar.
 */
@Composable
fun CommandBarFlyout(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)? = null,
    spacing: Dp = 4.dp,
    positionProvider: FlyoutPositionProvider = rememberFlyoutPositionProvider(initialPlacement = FlyoutPlacement.BottomAlignedEnd),
    content: OverflowRowScope.() -> Unit
) {
    BasicCommandBarFlyout(
        visible = visible,
        onDismissRequest = onDismissRequest,
        expanded = expanded,
        onExpandedChanged = onExpandedChanged,
        modifier = modifier,
        secondary = secondary,
        spacing = spacing,
        positionProvider = positionProvider,
        content = content,
        isLarge = false
    )
}

/**
 * A large command bar flyout.
 *
 * @param visible Whether the flyout is visible.
 * @param onDismissRequest Called when the user requests to dismiss the flyout.
 * @param expanded Whether the secondary content of the flyout is expanded.
 * @param onExpandedChanged Called when the expanded state changes.
 * @param modifier The modifier to be applied to the flyout.
 * @param secondary The secondary content to be displayed in the flyout.
 * The lambda receives a boolean indicating if there's any overflow items.
 * @param spacing The spacing between items in the flyout.
 * @param positionProvider The position provider for the flyout.
 * @param content The primary content of the flyout.
 */
@Composable
fun LargeCommandBarFlyout(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)? = null,
    spacing: Dp = 4.dp,
    positionProvider: FlyoutPositionProvider = rememberFlyoutPositionProvider(
        initialPlacement = FlyoutPlacement.BottomAlignedEnd
    ),
    content: OverflowRowScope.() -> Unit
) {
    BasicCommandBarFlyout(
        visible = visible,
        onDismissRequest = onDismissRequest,
        expanded = expanded,
        onExpandedChanged = onExpandedChanged,
        modifier = modifier,
        secondary = secondary,
        spacing = spacing,
        positionProvider = positionProvider,
        content = content,
        isLarge = true
    )
}

@Composable
private fun BasicCommandBarFlyout(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier,
    secondary: (@Composable MenuFlyoutScope.(hasOverFlowItem: Boolean) -> Unit)? = null,
    spacing: Dp ,
    positionProvider: FlyoutPositionProvider,
    isLarge: Boolean,
    content: OverflowRowScope.() -> Unit
) {
    val state = rememberOverflowRowState()
    val scope = remember(content) { mutableStateOf<OverflowActionScope?>(null) }
    BasicFlyout(
        modifier = modifier,
        visible = visible,
        onDismissRequest = onDismissRequest,
        positionProvider = positionProvider,
        contentPadding = PaddingValues()
    ) {
        val menuFlyoutScope = remember { MenuFlyoutScopeImpl() }
        val arrangement = Arrangement.spacedBy(spacing, Alignment.Start)
        FixedWidthLayout(
            header = {
                OverflowRow(
                    state = state,
                    horizontalArrangement = if (!state.overflowRange.isEmpty() || secondary != null) {
                        Arrangement.alignLast(arrangement, Alignment.End)
                    } else {
                        arrangement
                    },
                    overflowAction = {
                        CommandBarMoreButton(isLarge) {
                            onExpandedChanged(!expanded)
                        }
                        SideEffect {
                            scope.value = this@OverflowRow
                        }
                    },
                    content = content,
                    alwaysShowOverflowAction = secondary != null,
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    modifier = Modifier
                        .height(
                            if (isLarge) {
                                CommandBarDefaults.CommandBarLargeHeight
                            } else {
                                CommandBarDefaults.CommandBarStandardHeight
                            }
                        )
                        .animateContentSize(expandSpec())
                )
            },
            content = {
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandIn(expandSpec()),
                    exit = shrinkOut(expandSpec()),
                ) {
                    Column {
                        MenuFlyoutSeparator(modifier = Modifier.padding(horizontal = 1.dp))
                        Column(
                            modifier = Modifier.padding(top = 0.dp, bottom = 3.dp)
                        ) {
                            scope.value?.apply {
                                repeat(overflowItemCount) {
                                    overflowItem(it)
                                }
                            }
                            secondary?.invoke(menuFlyoutScope, state.overflowRange.isEmpty().not())
                        }
                    }
                }
            }
        )
    }
}

private fun <T> expandSpec() =
    tween<T>(FluentDuration.ShortDuration, easing = FluentEasing.FastInvokeEasing)