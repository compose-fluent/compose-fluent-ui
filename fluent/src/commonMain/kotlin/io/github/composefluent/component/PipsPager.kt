package io.github.composefluent.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualState
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * A horizontal pager that displays a series of pips, allowing users to navigate between pages.
 *
 * This component provides a visual indicator of the current page and allows users to navigate
 * through the pages by clicking on the pips or using the associated page buttons.
 *
 * @param state The state object to be used to control or observe the pager state.
 * @param modifier The modifier to be applied to the pager.
 * @param scrollAnimationSpec The animation specification for scrolling between pages. Defaults to [FlipViewDefaults.scrollAnimationSpec].
 * @param visibleCount The number of pips that are visible at any given time.
 * @param enabled Controls the enabled state of the pips pager. When `false`, the pips and page
 *   buttons will be disabled.
 * @param pageButtonVisibleStrategy The strategy to determine when the page buttons (e.g., arrows) should be visible.
 *   Defaults to [PageButtonVisibleStrategy.Never].
 * @param pipsColors The color scheme for the pips in different visual states (e.g., default, hovered, pressed).
 *   Defaults to [PipsPagerDefaults.pipsColors].
 */
@Composable
fun HorizontalPipsPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    scrollAnimationSpec: AnimationSpec<Float> = FlipViewDefaults.scrollAnimationSpec(),
    visibleCount: Int = 5,
    enabled: Boolean = true,
    pageButtonVisibleStrategy: PageButtonVisibleStrategy = PageButtonVisibleStrategy.Never,
    pipsColors: VisualStateScheme<Color> = PipsPagerDefaults.pipsColors(),
) {
    val scope = rememberCoroutineScope()
    PipsPager(
        selectedIndex = state.currentPage,
        onSelectedIndexChange = {
            scope.launch {
                if (abs(state.currentPage - it) == 1) {
                    state.animateScrollToPage(it, animationSpec = scrollAnimationSpec)
                } else {
                    state.scrollToPage(it)
                }
            }
        },
        count = state.pageCount,
        isVertical = false,
        pipsColors = pipsColors,
        visibleCount = visibleCount,
        pageButtonVisibleStrategy = pageButtonVisibleStrategy,
        enabled = enabled,
        modifier = modifier
    )
}

/**
 * A vertical pips pager that displays a series of pips, allowing the user to navigate between pages.
 * This is a wrapper for [PipsPager] and uses [PagerState] for the current page and the page count.
 *
 * @param state The [PagerState] that holds the current page and the number of pages.
 * @param modifier Modifier to be applied to the pips pager.
 * @param scrollAnimationSpec The animation spec used when scrolling between pages. Defaults to [FlipViewDefaults.scrollAnimationSpec].
 * @param visibleCount The maximum number of pips that should be visible at once.
 * @param enabled Whether the pips pager is enabled. Defaults to `true`.
 * @param pageButtonVisibleStrategy The strategy for showing/hiding the page buttons. Defaults to [PageButtonVisibleStrategy.Never].
 * @param pipsColors The color scheme for the pips. Defaults to [PipsPagerDefaults.pipsColors].
 */
@Composable
fun VerticalPipsPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    scrollAnimationSpec: AnimationSpec<Float> = FlipViewDefaults.scrollAnimationSpec(),
    visibleCount: Int = 5,
    enabled: Boolean = true,
    pageButtonVisibleStrategy: PageButtonVisibleStrategy = PageButtonVisibleStrategy.Never,
    pipsColors: VisualStateScheme<Color> = PipsPagerDefaults.pipsColors(),
) {
    val scope = rememberCoroutineScope()
    PipsPager(
        selectedIndex = state.currentPage,
        onSelectedIndexChange = {
            scope.launch {
                if (abs(state.currentPage - it) == 1) {
                    state.animateScrollToPage(it, animationSpec = scrollAnimationSpec)
                } else {
                    state.scrollToPage(it)
                }
            }
        },
        count = state.pageCount,
        isVertical = true,
        pipsColors = pipsColors,
        visibleCount = visibleCount,
        pageButtonVisibleStrategy = pageButtonVisibleStrategy,
        enabled = enabled,
        modifier = modifier
    )
}

/**
 * A horizontal pager that displays a series of pips (dots) to represent pages.
 *
 * @param selectedIndex The index of the currently selected page.
 * @param onSelectedIndexChange A callback that is invoked when the selected page changes.
 * The parameter is the new selected index.
 * @param count The total number of pages.
 * @param modifier Modifier for styling and layout customization.
 * @param visibleCount The number of pips that are visible at one time. The pager will
 * scroll if the count exceeds this value.
 * @param enabled Controls the enabled state of the pips pager. When `false`, the user
 * cannot interact with the pager and it will be displayed in a disabled state.
 * @param pageButtonVisibleStrategy Determines the visibility of the page navigation buttons.
 * See [PageButtonVisibleStrategy] for available options.
 * @param pipsColors A [VisualStateScheme] that provides the color for each visual state of the pips.
 */
@Composable
fun HorizontalPipsPager(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    enabled: Boolean = true,
    pageButtonVisibleStrategy: PageButtonVisibleStrategy = PageButtonVisibleStrategy.Never,
    pipsColors: VisualStateScheme<Color> = PipsPagerDefaults.pipsColors(),
) {
    PipsPager(
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        count = count,
        isVertical = false,
        pipsColors = pipsColors,
        visibleCount = visibleCount,
        pageButtonVisibleStrategy = pageButtonVisibleStrategy,
        enabled = enabled,
        modifier = modifier
    )
}

/**
 * A vertical pager that displays a series of pips, allowing the user to navigate between
 * different pages.
 *
 * @param selectedIndex The index of the currently selected page.
 * @param onSelectedIndexChange A callback that is invoked when the selected page changes.
 *   It provides the new index.
 * @param count The total number of pages.
 * @param modifier The modifier to be applied to the pager.
 * @param enabled Controls the enabled state of the pager. When `false`, the user cannot
 *   interact with the pager.
 * @param pageButtonVisibleStrategy Determines when the page navigation buttons should be visible.
 *   See [PageButtonVisibleStrategy] for details.
 * @param visibleCount The maximum number of pips to display at once. If the total number of pages
 *   exceeds this value, the pager will be scrollable.
 * @param pipsColors The color scheme for the pips, determining their appearance in different states.
 *   See [PipsPagerDefaults.pipsColors] for details.
 */
@Composable
fun VerticalPipsPager(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    pageButtonVisibleStrategy: PageButtonVisibleStrategy = PageButtonVisibleStrategy.Never,
    visibleCount: Int = 5,
    pipsColors: VisualStateScheme<Color> = PipsPagerDefaults.pipsColors(),
) {
    PipsPager(
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        count = count,
        isVertical = true,
        pipsColors = pipsColors,
        visibleCount = visibleCount,
        pageButtonVisibleStrategy = pageButtonVisibleStrategy,
        enabled = enabled,
        modifier = modifier
    )
}

@Composable
private fun PipsPager(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    count: Int,
    isVertical: Boolean,
    pipsColors: VisualStateScheme<Color>,
    pageButtonVisibleStrategy: PageButtonVisibleStrategy,
    enabled: Boolean,
    visibleCount: Int,
    modifier: Modifier,
) {
    val size = PipsItemWidth * minOf(visibleCount, count)
    val listState = rememberLazyListState()
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered = interactionSource.collectIsHoveredAsState()
    val pageButtonVisible = when (pageButtonVisibleStrategy) {
        PageButtonVisibleStrategy.Always -> true
        PageButtonVisibleStrategy.VisibleOnHovered -> isHovered.value
        PageButtonVisibleStrategy.Never -> false
    }
    if (isVertical) {
        Column(modifier = modifier.width(PipsPagerContainerHeight).hoverable(interactionSource)) {

            PageButton(
                colors = pipsColors,
                type = FontIconPrimitive.CaretUp,
                onClick = { onSelectedIndexChange(selectedIndex - 1) },
                enabled = enabled && selectedIndex > 0,
                visible = pageButtonVisible,
                modifier = Modifier.hoverable(interactionSource)
            )
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size)
            ) {
                items(count) { index ->
                    Pips(
                        selected = index == selectedIndex,
                        onSelectedChange = { onSelectedIndexChange(index) },
                        isVertical = isVertical,
                        colors = pipsColors,
                        enabled = enabled,
                        modifier = Modifier
                    )
                }
            }
            PageButton(
                colors = pipsColors,
                type = FontIconPrimitive.CaretDown,
                onClick = { onSelectedIndexChange(selectedIndex + 1) },
                enabled = enabled && selectedIndex < count - 1,
                visible = pageButtonVisible,
                modifier = Modifier
            )
        }
    } else {
        Row(modifier = modifier.height(PipsPagerContainerHeight).hoverable(interactionSource)) {

            PageButton(
                colors = pipsColors,
                type = FontIconPrimitive.CaretLeft,
                onClick = { onSelectedIndexChange(selectedIndex - 1) },
                enabled = enabled && selectedIndex > 0,
                visible = pageButtonVisible,
                modifier = Modifier.hoverable(interactionSource)
            )

            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(size)
            ) {
                items(count) { index ->
                    Pips(
                        selected = index == selectedIndex,
                        onSelectedChange = { onSelectedIndexChange(index) },
                        isVertical = isVertical,
                        colors = pipsColors,
                        enabled = enabled,
                        modifier = Modifier
                    )
                }
            }

            PageButton(
                colors = pipsColors,
                type = FontIconPrimitive.CaretRight,
                onClick = { onSelectedIndexChange(selectedIndex + 1) },
                enabled = enabled && selectedIndex < count - 1,
                visible = pageButtonVisible,
                modifier = Modifier.hoverable(interactionSource)
            )
        }
    }
    LaunchedEffect(selectedIndex, listState.layoutInfo.visibleItemsInfo) {
        val item = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == selectedIndex }
        if (item == null) {
            listState.animateScrollToItem(selectedIndex)
        } else {
            val itemSize = item.size
            val viewportSize = if (isVertical) {
                listState.layoutInfo.viewportSize.height
            } else {
                listState.layoutInfo.viewportSize.width
            }
            val centerOffset = (viewportSize - itemSize) / 2f
            val scrollOffset = item.offset - centerOffset
            listState.animateScrollBy(
                scrollOffset,
                animationSpec = FlipViewDefaults.scrollAnimationSpec()
            )
        }
    }
}

typealias PipsColorScheme = PentaVisualScheme<Color>

/**
 * Contains the default values used for [PipsPager].
 */
object PipsPagerDefaults {

    /**
     * Creates a [PipsColorScheme] with the specified colors.
     *
     * @param default The default color of the pips.
     * @param hovered The color of the pips when hovered.
     * @param pressed The color of the pips when pressed.
     * @param disabled The color of the pips when disabled.
     * @return A [PipsColorScheme] with the specified colors.
     */
    @Stable
    @Composable
    fun pipsColors(
        default: Color = FluentTheme.colors.controlStrong.default,
        hovered: Color = FluentTheme.colors.text.text.secondary,
        pressed: Color = FluentTheme.colors.text.text.secondary,
        disabled: Color = FluentTheme.colors.controlStrong.disabled
    ) = PipsColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Pips(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    isVertical: Boolean,
    colors: VisualStateScheme<Color>,
    enabled: Boolean,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val visualState = interactionSource.collectVisualState(!enabled)
    val currentColor = colors.schemeFor(visualState)
    val size = animateDpAsState(
        targetValue = when (visualState) {
            VisualState.Hovered -> 5.dp
            VisualState.Pressed -> 3.dp
            else -> 4.dp
        } + if (selected) {
            2.dp
        } else {
            0.dp
        }
    )
    LaunchedEffect(selected) {
        if (selected) {
            bringIntoViewRequester.bringIntoView()
        }
    }
    Box(
        modifier = modifier
            .then(
                if (isVertical) {
                    Modifier.fillMaxWidth()
                        .height(PipsItemWidth)
                } else {
                    Modifier.fillMaxHeight()
                        .width(PipsItemWidth)
                }
            )
            .bringIntoViewRequester(bringIntoViewRequester)
            .selectable(
                selected = selected,
                indication = null,
                interactionSource = interactionSource,
                enabled = enabled,
                onClick = { onSelectedChange(!selected) }
            )
            .wrapContentSize(Alignment.Center)
            .size(size.value)
            .background(currentColor, shape = CircleShape)
    )
}

/**
 * Defines the strategy for determining when the page buttons (e.g., previous/next arrows)
 * should be visible in the [PipsPager].
 */
enum class PageButtonVisibleStrategy {
    /**
     * Always show the page buttons.
     */
    Always,
    /**
     * Page buttons are only visible when the pager is hovered.
     */
    VisibleOnHovered,
    /**
     * The page button is never visible.
     */
    Never
}

@OptIn(ExperimentalFluentApi::class)
@Composable
private fun PageButton(
    colors: VisualStateScheme<Color>,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    visible: Boolean = true,
    type: FontIconPrimitive,
    onClick: () -> Unit,
) {
    if (visible) {
        val interactionSource = remember { MutableInteractionSource() }
        val currentColor = colors.schemeFor(interactionSource.collectVisualState(!enabled))
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(PipsPagerContainerHeight)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = onClick,
                    enabled = enabled
                )
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentColor,
                LocalContentAlpha provides currentColor.alpha,
                LocalTextStyle provides LocalTextStyle.current.copy(color = currentColor)
            ) {
                val isPressed = interactionSource.collectIsPressedAsState()
                val scale = animateFloatAsState(
                    targetValue = if (isPressed.value) {
                        7 / 8f
                    } else 1f,
                    animationSpec = tween(
                        FluentDuration.ShortDuration,
                        easing = FluentEasing.FastInvokeEasing
                    )
                )
                FontIconSolid8(
                    contentDescription = null,
                    type = type,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                        }
                )
            }
        }
    } else {
        Box(modifier = modifier.size(PipsPagerContainerHeight))
    }
}

private val PipsPagerContainerHeight = 20.dp
private val PipsItemWidth = 12.dp