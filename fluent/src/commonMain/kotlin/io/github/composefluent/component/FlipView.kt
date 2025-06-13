package io.github.composefluent.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.Material
import io.github.composefluent.background.MaterialContainer
import io.github.composefluent.background.MaterialContainerScope
import io.github.composefluent.background.MaterialDefaults
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import kotlinx.coroutines.launch

/**
 * A composable function that creates a horizontal flip view, allowing users to navigate through pages
 * horizontally. It provides controls for moving to the next and previous pages, as well as customization
 * options for styling and behavior.
 *
 * @param state The state object to be used to control or observe the list's state.
 * @param modifier Modifier to be applied to the layout.
 * @param enabled Whether the flip view is enabled or not.
 * @param pageButtonColors Colors for the next and previous page buttons.
 * @param contentPadding The padding to be applied to the page content.
 * @param pageSize The size of the pages.
 * @param beyondViewportPageCount The number of pages to be kept beyond the viewport.
 * @param pageSpacing The spacing between pages.
 * @param verticalAlignment The vertical alignment of pages within the viewport.
 * @param flingBehavior The fling behavior for the page scrolling.
 * @param userScrollEnabled Whether user scrolling is enabled.
 * @param reverseLayout Whether to reverse the layout of the pages.
 * @param key A factory of stable and unique keys representing the item.
 * @param pageNestedScrollConnection A [NestedScrollConnection] that receives nested scroll events from the pages.
 * @param snapPosition The snap position for the page.
 * @param pageContent The content to be displayed on each page.
 */
@OptIn(ExperimentalFluentApi::class)
@Composable
fun HorizontalFlipView(
    state: PagerState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    pageButtonColors: VisualStateScheme<PageButtonColor> = FlipViewDefaults.pageButtonColors(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    flingBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(state = state),
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    key: ((index: Int) -> Any)? = null,
    pageNestedScrollConnection: NestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
        state,
        Orientation.Horizontal
    ),
    snapPosition: SnapPosition = SnapPosition.Start,
    pageContent: @Composable PagerScope.(index: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    HorizontalFlipViewContainer(
        nextPageVisible = remember(state) { derivedStateOf { state.currentPage < state.pageCount - 1 } }.value,
        previousPageVisible = remember(state) { derivedStateOf { state.currentPage > 0 } }.value,
        onNextPageClick = {
            scope.launch {
                state.animateScrollToPage(
                    page = state.currentPage + 1,
                    animationSpec = FlipViewDefaults.scrollAnimationSpec()
                )
            }
        },
        onPreviousPageClick = {
            scope.launch {
                state.animateScrollToPage(
                    page = state.currentPage - 1,
                    animationSpec = FlipViewDefaults.scrollAnimationSpec()
                )
            }
        },
        modifier = modifier,
        enabled = enabled,
        pageButtonColors = pageButtonColors
    ) {
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            pageSize = pageSize,
            beyondViewportPageCount = beyondViewportPageCount,
            pageSpacing = pageSpacing,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            reverseLayout = reverseLayout,
            key = key,
            pageNestedScrollConnection = pageNestedScrollConnection,
            snapPosition = snapPosition,
            pageContent = pageContent
        )
    }
}

/**
 * A composable function that creates a horizontal flip view, allowing users to navigate through pages
 * horizontally. It provides controls for moving to the next and previous pages, as well as customization
 * options for styling and behavior.
 *
 * @param state The [PagerState] to be used by the [VerticalPager]. This state
 *  object manages the current page index and scrolling behavior.
 * @param modifier Modifier to be applied to the layout.
 * @param enabled Controls whether the flip view is enabled for interaction.
 * @param pageButtonColors A [VisualStateScheme] defining the colors for the next/previous page buttons.
 * @param contentPadding The padding to be applied to the content area of the pager.
 * @param pageSize The size of each page in the pager, defined as a [PageSize].
 * @param beyondViewportPageCount The number of pages to be composed beyond the viewport in each direction.
 * @param pageSpacing The space between pages in the pager.
 * @param horizontalAlignment The horizontal alignment of pages within the pager.
 * @param flingBehavior The fling behavior to use for scrolling.
 * @param userScrollEnabled Controls whether the user can scroll the pager manually.
 * @param reverseLayout Whether to reverse the layout of the pages.
 * @param key A function to provide a unique key for each page based on its index.
 * @param pageNestedScrollConnection The [NestedScrollConnection] to be used for nested scrolling.
 * @param snapPosition The position at which pages should snap to.
 * @param pageContent The composable function to render the content of each page. It receives the page index as an argument.
 */
@OptIn(ExperimentalFluentApi::class)
@Composable
fun VerticalFlipView(
    state: PagerState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    pageButtonColors: VisualStateScheme<PageButtonColor> = FlipViewDefaults.pageButtonColors(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
    pageSpacing: Dp = FlipViewDefaults.verticalPageSpacing,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    flingBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(state = state),
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    key: ((index: Int) -> Any)? = null,
    pageNestedScrollConnection: NestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
        state,
        Orientation.Horizontal
    ),
    snapPosition: SnapPosition = SnapPosition.Start,
    pageContent: @Composable PagerScope.(index: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    VerticalFlipViewContainer(
        nextPageVisible = remember(state) { derivedStateOf { state.currentPage < state.pageCount - 1 } }.value,
        previousPageVisible = remember(state) { derivedStateOf { state.currentPage > 0 } }.value,
        onNextPageClick = {
            scope.launch {
                state.animateScrollToPage(
                    page = state.currentPage + 1,
                    animationSpec = FlipViewDefaults.scrollAnimationSpec()
                )
            }
        },
        onPreviousPageClick = {
            scope.launch {
                state.animateScrollToPage(
                    page = state.currentPage - 1,
                    animationSpec = FlipViewDefaults.scrollAnimationSpec()
                )
            }
        },
        modifier = modifier,
        enabled = enabled,
        pageButtonColors = pageButtonColors
    ) {
        VerticalPager(
            state = state,
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            pageSize = pageSize,
            beyondViewportPageCount = beyondViewportPageCount,
            pageSpacing = pageSpacing,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            reverseLayout = reverseLayout,
            key = key,
            pageNestedScrollConnection = pageNestedScrollConnection,
            snapPosition = snapPosition,
            pageContent = pageContent
        )
    }
}

/**
 * A composable function that creates a vertical flip view container.
 * This container provides the layout and controls for navigating between pages vertically.
 *
 * @param onNextPageClick Callback function invoked when the next page button is clicked.
 * @param onPreviousPageClick Callback function invoked when the previous page button is clicked.
 * @param modifier Modifier to be applied to the layout.
 * @param enabled Controls whether the flip view container is enabled for interaction.
 * @param nextPageVisible Determines if the next page button is visible.
 * @param previousPageVisible Determines if the previous page button is visible.
 * @param pageButtonColors Colors for the next and previous page buttons.
 * @param content The content to be displayed within the flip view container.
 */
@ExperimentalFluentApi
@Composable
fun VerticalFlipViewContainer(
    onNextPageClick: () -> Unit,
    onPreviousPageClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    nextPageVisible: Boolean = true,
    previousPageVisible: Boolean = true,
    pageButtonColors: VisualStateScheme<PageButtonColor> = FlipViewDefaults.pageButtonColors(),
    content: @Composable () -> Unit
) {
    FlipViewContainer(
        onNextPageClick = onNextPageClick,
        onPreviousPageClick = onPreviousPageClick,
        modifier = modifier,
        isVertical = true,
        enabled = enabled,
        nextPageVisible = nextPageVisible,
        previousPageVisible = previousPageVisible,
        pageButtonColors = pageButtonColors,
        content = content
    )
}

/**
 * A composable function that provides a container for a horizontally oriented flip view.
 * This container includes controls for navigating to the next and previous pages, which
 * are displayed as buttons. The content displayed within the container is determined by
 * the `content` parameter.
 *
 * @param onNextPageClick Callback invoked when the next page button is clicked.
 * @param onPreviousPageClick Callback invoked when the previous page button is clicked.
 * @param modifier Modifier for styling and layout of the container.
 * @param enabled Controls whether the flip view's navigation is enabled.
 * @param nextPageVisible Determines if the next page button should be visible.
 * @param previousPageVisible Determines if the previous page button should be visible.
 * @param pageButtonColors The colors used for the next and previous page buttons, defined by a [VisualStateScheme].
 * @param content The composable content to be displayed within the flip view container.
 */
@ExperimentalFluentApi
@Composable
fun HorizontalFlipViewContainer(
    onNextPageClick: () -> Unit,
    onPreviousPageClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    nextPageVisible: Boolean = true,
    previousPageVisible: Boolean = true,
    pageButtonColors: VisualStateScheme<PageButtonColor> = FlipViewDefaults.pageButtonColors(),
    content: @Composable () -> Unit
) {
    FlipViewContainer(
        onNextPageClick = onNextPageClick,
        onPreviousPageClick = onPreviousPageClick,
        modifier = modifier,
        isVertical = false,
        enabled = enabled,
        pageButtonColors = pageButtonColors,
        nextPageVisible = nextPageVisible,
        previousPageVisible = previousPageVisible,
        content = content
    )
}

@ExperimentalFluentApi
@Composable
private fun FlipViewContainer(
    onNextPageClick: () -> Unit,
    onPreviousPageClick: () -> Unit,
    nextPageVisible: Boolean,
    previousPageVisible: Boolean,
    modifier: Modifier,
    isVertical: Boolean,
    enabled: Boolean,
    pageButtonColors: VisualStateScheme<PageButtonColor>,
    content: @Composable () -> Unit
) {
    MaterialContainer(modifier = modifier.clip(FluentTheme.shapes.overlay)) {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered = interactionSource.collectIsHoveredAsState()
        Box(
            propagateMinConstraints = true,
            modifier = Modifier.behindMaterial()
                .hoverable(interactionSource, enabled = enabled)
        ) {
            content()
        }
        if (isVertical) {
            PageButton(
                colors = pageButtonColors,
                isVertical = true,
                type = FontIconPrimitive.CaretDown,
                onClick = onNextPageClick,
                visible = nextPageVisible && isHovered.value,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 1.dp)
                    .hoverable(interactionSource),
            )
            PageButton(
                colors = pageButtonColors,
                isVertical = true,
                type = FontIconPrimitive.CaretUp,
                onClick = onPreviousPageClick,
                visible = previousPageVisible && isHovered.value,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 1.dp)
                    .hoverable(interactionSource),
            )
        } else {
            PageButton(
                colors = pageButtonColors,
                isVertical = false,
                type = FontIconPrimitive.CaretRight,
                onClick = onNextPageClick,
                visible = nextPageVisible && isHovered.value,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 1.dp)
                    .hoverable(interactionSource),
            )

            PageButton(
                colors = pageButtonColors,
                isVertical = false,
                type = FontIconPrimitive.CaretLeft,
                onClick = onPreviousPageClick,
                visible = previousPageVisible && isHovered.value,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 1.dp)
                    .hoverable(interactionSource),
            )
        }
    }
}

@OptIn(ExperimentalFluentApi::class)
@Composable
private fun MaterialContainerScope.PageButton(
    colors: VisualStateScheme<PageButtonColor>,
    modifier: Modifier = Modifier,
    isVertical: Boolean,
    visible: Boolean,
    onClick: () -> Unit,
    type: FontIconPrimitive,
) {
    if (visible) {
        val interactionSource = remember { MutableInteractionSource() }
        val currentColor = colors.schemeFor(interactionSource.collectVisualState(false))
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(FluentTheme.shapes.control)
                .materialOverlay(currentColor.background)
                .size(if (isVertical) VerticalButtonSize else HorizontalButtonSize)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = onClick
                )
        ) {
            CompositionLocalProvider(
                LocalContentColor provides currentColor.contentColor,
                LocalContentAlpha provides currentColor.contentColor.alpha,
                LocalTextStyle provides LocalTextStyle.current.copy(color = currentColor.contentColor)
            ) {
                val isPressed = interactionSource.collectIsPressedAsState()
                val scale = animateFloatAsState(
                    targetValue = if (isPressed.value) { 7/8f } else 1f,
                    animationSpec = tween(FluentDuration.ShortDuration, easing = FluentEasing.FastInvokeEasing)
                )
                FontIconSolid8(
                    type = type,
                    contentDescription = null,
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                        }
                )
            }
        }
    }
}

/**
 * Represents the colors used for the page navigation buttons in the [FlipView].
 *
 * @property background The background [Material] for the page button.
 * @property contentColor The [Color] of the content (icon) within the page button.
 */
data class PageButtonColor(
    val background: Material,
    val contentColor: Color
)

object FlipViewDefaults {

    /**
     * The default spacing between pages in the [VerticalFlipView].
     */
    val verticalPageSpacing = 4.dp

    /**
     * Creates a [PentaVisualScheme] for the page buttons, defining their colors for different states.
     *
     * @param default The default [PageButtonColor] for the page buttons.
     * @param hovered The [PageButtonColor] for when the page buttons are hovered.
     * @param pressed The [PageButtonColor] for when the page buttons are pressed.
     * @return A [PentaVisualScheme] representing the visual scheme for the page buttons.
     */
    @Composable
    @Stable
    fun pageButtonColors(
        default: PageButtonColor = PageButtonColor(
            background = MaterialDefaults.acrylicDefault(),
            contentColor = FluentTheme.colors.controlStrong.default
        ),
        hovered: PageButtonColor = PageButtonColor(
            background = MaterialDefaults.acrylicDefault(),
            contentColor = FluentTheme.colors.text.text.secondary
        ),
        pressed: PageButtonColor = hovered,
    ) = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = default
    )

    /**
     * Returns the default [AnimationSpec] used for scrolling between pages in the [FlipView].
     * This spec uses a tween animation with a long duration and a fast invoke easing.
     *
     * @return The default [AnimationSpec] for page scrolling.
     */
    fun scrollAnimationSpec() =
        tween<Float>(FluentDuration.LongDuration, easing = FluentEasing.FastInvokeEasing)
}

private val VerticalButtonSize = DpSize(38.dp, 16.dp)
private val HorizontalButtonSize = DpSize(16.dp, 38.dp)