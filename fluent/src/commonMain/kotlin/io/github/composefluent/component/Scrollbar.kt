package io.github.composefluent.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import kotlinx.coroutines.launch

/*
* fork from Scrollbar.desktop
 */
expect interface ScrollbarAdapter {

    // We use `Double` values here in order to allow scrolling both very large (think LazyList with
    // millions of items) and very small (think something whose natural coordinates are less than 1)
    // content.

    /**
     * Scroll offset of the content inside the scrollable component.
     *
     * For example, a value of `100` could mean the content is scrolled by 100 pixels from the
     * start.
     */
    val scrollOffset: Double

    /**
     * The size of the scrollable content, on the scrollable axis.
     */
    val contentSize: Double

    /**
     * The size of the viewport, on the scrollable axis.
     */
    val viewportSize: Double

    /**
     * Instantly jump to [scrollOffset].
     *
     * @param scrollOffset target offset to jump to, value will be coerced to the valid
     * scroll range.
     */
    suspend fun scrollTo(scrollOffset: Double)

}

/**
 * A scrollbar that can be used to control the scrolling of a scrollable component.
 *
 * @param isVertical `true` if the scrollbar is vertical, `false` if horizontal.
 * @param adapter The [ScrollbarAdapter] that provides the information about the scrollable
 *   component and allows to control its scrolling.
 * @param modifier Modifier to be applied to the scrollbar.
 * @param reverseLayout `true` if the scrollable component's layout is reversed, `false`
 *   otherwise.
 * @param colors The [ScrollbarColors] that will be used to draw the scrollbar.
 */
@Composable
fun Scrollbar(
    isVertical: Boolean,
    adapter: ScrollbarAdapter,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    colors: ScrollbarColors = ScrollbarDefaults.colors()
) {
    PlatformScrollBar(
        isVertical,
        adapter,
        modifier,
        reverseLayout,
        colors
    )
}

@Composable
internal expect fun PlatformScrollBar(
    isVertical: Boolean,
    adapter: ScrollbarAdapter,
    modifier: Modifier,
    reverseLayout: Boolean,
    colors: ScrollbarColors
)

/**
 * Creates and remembers a [ScrollbarAdapter] for the given [ScrollState].
 *
 * @param state the [ScrollState] to create a [ScrollbarAdapter] for.
 */
@Composable
expect fun rememberScrollbarAdapter(
    state: ScrollState
): ScrollbarAdapter

/**
 * Creates and remembers a [ScrollbarAdapter] for a [LazyListState].
 *
 * @param state the [LazyListState] to be used with the [ScrollbarAdapter].
 * @return a [ScrollbarAdapter] that can be used with a scrollbar.
 */
@Composable
expect fun rememberScrollbarAdapter(
    state: LazyListState
): ScrollbarAdapter

/**
 * Creates and remembers a [ScrollbarAdapter] for a [LazyGridState].
 *
 * @param state The [LazyGridState] to observe.
 * @return A [ScrollbarAdapter] that is coupled to the given [LazyGridState].
 */
@Composable
expect fun rememberScrollbarAdapter(
    state: LazyGridState
): ScrollbarAdapter

/**
 * Represents the color scheme for the scrollbar.
 *
 * @property contentColor The color of the scrollbar content (e.g., the thumb) in its default state.
 * @property contentColorDisabled The color of the scrollbar content when it's disabled.
 * @property contentColorHovered The color of the scrollbar content when the mouse is hovering over it.
 * @property contentColorPressed The color of the scrollbar content when it's being pressed.
 * @property backgroundColor The background color of the scrollbar track.
 */
@Immutable
data class ScrollbarColors(
    val contentColor: Color,
    val contentColorDisabled: Color,
    val contentColorHovered: Color,
    val contentColorPressed: Color,
    val backgroundColor: Color
)

/**
 * Contains the default values used by [Scrollbar].
 */
object ScrollbarDefaults {
    /**
     * The thickness of the scrollbar when it is highlighted (e.g., hovered or dragged).
     */
    val thicknessHighlight = 6.dp
    /**
     * The default thickness of the scrollbar when it is not being hovered or pressed.
     */
    val thickness = 2.dp
    /**
    +     * The duration in milliseconds for the time to show the scrollbar highlight.
     */
    const val hoverDurationMillis = 500
    /**
     * The default shape of the scrollbar thumb.
     */
    val shape = CircleShape
    /**
     * The scale factor applied to the scrollbar indicator when it is pressed.
     */
    const val indicatorPressedScale = 0.875f
    /**
     * The offset for the scroll indicator, used to position the indicator away from the end or
     * start of the scrollbar.
     */
    val indicatorScrollOffset = 24.dp

    /**
     * Creates a [ScrollbarColors] that represents the default colors used in a scrollbar.
     *
     * @param contentColor The color of the scrollbar thumb in its default state.
     * @param contentColorDisabled The color of the scrollbar thumb when disabled.
     * @param contentColorHovered The color of the scrollbar thumb when hovered.
     * @param contentColorPressed The color of the scrollbar thumb when pressed.
     * @param backgroundColor The background color of the scrollbar track.
     *
     * @return The resulting [ScrollbarColors] object.
     */
    @Composable
    fun colors(
        contentColor: Color = FluentTheme.colors.controlStrong.default,
        contentColorDisabled: Color = FluentTheme.colors.controlStrong.disabled,
        contentColorHovered: Color = FluentTheme.colors.controlStrong.default,
        contentColorPressed: Color = FluentTheme.colors.controlStrong.default,
        backgroundColor: Color = FluentTheme.colors.background.acrylic.default
    ) = ScrollbarColors(
        contentColor = contentColor,
        contentColorDisabled = contentColorDisabled,
        contentColorHovered = contentColorHovered,
        contentColorPressed = contentColorPressed,
        backgroundColor = backgroundColor
    )
}

/**
 * A clickable indicator for scrolling, usually used alongside a [Scrollbar].
 *
 * @param adapter The [ScrollbarAdapter] that provides information about the scroll state.
 * @param isVertical `true` if the scrollbar is vertical, `false` if it's horizontal.
 * @param modifier Modifier for styling and layout.
 * @param visible `true` if the indicator should be visible, `false` otherwise.
 * @param forward `true` if the indicator is for scrolling towards the start (e.g., up/left), `false` for scrolling towards the end (e.g., down/right).
 * @param enabled `true` if the indicator should respond to clicks, `false` otherwise.
 * @param colors The [ScrollbarColors] to use for the indicator.
 */
@Composable
fun ScrollbarIndicator(
    adapter: ScrollbarAdapter,
    isVertical: Boolean,
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    forward: Boolean = false,
    enabled: Boolean = true,
    colors: ScrollbarColors = ScrollbarDefaults.colors(),
) {
    val interaction = remember {
        MutableInteractionSource()
    }
    val hovered by interaction.collectIsHoveredAsState()
    val pressed by interaction.collectIsPressedAsState()
    val scrollScope = rememberCoroutineScope()
    val offset = with(LocalDensity.current) { ScrollbarDefaults.indicatorScrollOffset.toPx() }
    val animationFraction by animateFloatAsState(
        targetValue = if (visible) {
            1f
        } else {
            0f
        },
        animationSpec = tween(FluentDuration.ShortDuration, easing = FluentEasing.FastInvokeEasing)
    )
    val targetScale by animateFloatAsState(
        if (pressed) {
            ScrollbarDefaults.indicatorPressedScale
        } else {
            1f
        }
    )
    val targetAlpha = animationFraction
    val tint = when {
        pressed -> colors.contentColorPressed
        hovered -> colors.contentColorHovered
        !enabled -> colors.contentColorDisabled
        else -> colors.contentColor
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .then(modifier)
            .then(
                if (isVertical) {
                    Modifier.size(12.dp, 16.dp)
                } else {
                    Modifier.size(16.dp, 12.dp)
                }
            )
            .clickable(
                interactionSource = interaction,
                indication = null,
                enabled = enabled && visible
            ) {
                scrollScope.launch {
                    if (forward) {
                        adapter.scrollTo(-offset + adapter.scrollOffset)
                    } else {
                        adapter.scrollTo(offset + adapter.scrollOffset)
                    }
                }
            }.graphicsLayer {
                scaleX = targetScale
                scaleY = targetScale
                alpha = targetAlpha
            }) {
        CompositionLocalProvider(
            LocalContentColor provides tint,
            LocalContentAlpha provides tint.alpha
        ) {
            FontIconSolid8(
                type = when {
                    isVertical && forward -> FontIconPrimitive.CaretUp
                    isVertical -> FontIconPrimitive.CaretDown
                    forward -> FontIconPrimitive.CaretLeft
                    else -> FontIconPrimitive.CaretRight
                },
                contentDescription = null
            )
        }
    }
}

/**
 * A container that displays a scrollbar alongside its content.
 *
 * This composable arranges a scrollbar and its associated content within a layout. The scrollbar's
 * position is automatically calculated to be adjacent to the content, either vertically or
 * horizontally, depending on the `isVertical` parameter.
 *
 * @param adapter The [ScrollbarAdapter] that provides scroll-related information and control.
 * @param modifier The [Modifier] to be applied to the container.
 * @param isVertical `true` if the scrollbar is vertical, `false` if horizontal.
 * @param scrollbar A composable lambda that defines the appearance and behavior of the scrollbar.
 *   Defaults to a standard [Scrollbar] using the provided adapter.
 * @param content A composable lambda that defines the content to be displayed alongside the
 *   scrollbar.
 */
@Composable
fun ScrollbarContainer(
    adapter: ScrollbarAdapter,
    modifier: Modifier = Modifier,
    isVertical: Boolean = true,
    scrollbar: @Composable () -> Unit = { Scrollbar(isVertical, adapter) },
    content: @Composable () -> Unit
) {
    ScrollbarContainer(
        modifier = modifier,
        isVertical = isVertical,
        scrollbar = scrollbar,
        content = content
    )
}

/**
 * A composable that arranges a scrollbar and its content side by side.
 *
 * This function uses a custom [Layout] to position the scrollbar and content.
 * It allows you to provide a custom [scrollbar] composable and [content] composable.
 * The scrollbar is positioned on the right side if [isVertical] is true, or on the bottom side if false.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param isVertical Determines if the scrollbar is vertical (true) or horizontal (false). Defaults to true.
 * @param scrollbar The composable to be used as the scrollbar.
 * @param content The composable to be used as the scrollable content.
 */
@Composable
fun ScrollbarContainer(
    modifier: Modifier = Modifier,
    isVertical: Boolean = true,
    scrollbar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            Box(Modifier.layoutId("scrollbar")) { scrollbar() }
            Box(Modifier.layoutId("content")) { content() }
        }
    ) { measurables, constraints ->
        val contentMeasurable =
            measurables.fastFirstOrNull { it.layoutId == "content" }
                ?: return@Layout layout(0, 0) {}
        val contentPlaceable =
            contentMeasurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        val scrollbarMeasurable =
            measurables.fastFirstOrNull { it.layoutId == "scrollbar" } ?: return@Layout layout(
                contentPlaceable.width,
                contentPlaceable.height
            ) { contentPlaceable.place(0, 0) }
        val scrollbarPlaceable = scrollbarMeasurable.measure(
            if (isVertical) {
                Constraints.fixedHeight(contentPlaceable.height)
            } else {
                Constraints.fixedWidth(contentPlaceable.width)
            }
        )
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
            if (isVertical) {
                scrollbarPlaceable.place(contentPlaceable.width - scrollbarPlaceable.width, 0)
            } else {
                scrollbarPlaceable.place(0, contentPlaceable.height - scrollbarPlaceable.height)
            }
        }
    }
}