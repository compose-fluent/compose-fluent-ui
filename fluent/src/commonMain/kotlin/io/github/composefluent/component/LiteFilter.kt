package io.github.composefluent.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.requireLayoutCoordinates
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.relocation.BringIntoViewModifierNode
import androidx.compose.ui.relocation.bringIntoView
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.animation.FluentDuration
import kotlinx.coroutines.launch

/**
 * A composable function that creates a horizontal filter component with scrollable items.
 * It displays navigation buttons (left and right arrows) to indicate scrollability.
 *
 * @param modifier Modifier for styling and layout of the filter.
 * @param state The [ScrollState] to manage the horizontal scroll.
 * @param content The composable content (items) within the filter row.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LiteFilter(
    modifier: Modifier = Modifier,
    state: ScrollState = rememberScrollState(),
    content: @Composable RowScope.() -> Unit
) {
    Box(modifier = modifier.heightIn(40.dp)) {
        val isPreviousVisible = state.canScrollBackward
        val isNextVisible = state.canScrollForward
        val density = LocalDensity.current
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clip(
                PaddingShape(
                    clipStart = isPreviousVisible,
                    clipEnd = isNextVisible
                )
            )
                .horizontalScroll(state)
                .then(LiteFilterModifierNodeElement(state, density))
                .align(Alignment.CenterStart)
        ) {
            content()
        }

        val scope = rememberCoroutineScope()
        AnimatedVisibility(
            visible = isPreviousVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = FluentDuration.ShortDuration)),
            exit = fadeOut(animationSpec = tween(durationMillis = FluentDuration.ShortDuration)),
            modifier = Modifier.padding(start = 2.dp).align(Alignment.CenterStart)
        ) {
            SubtleButton(
                onClick = { scope.launch { state.animateScrollBy(-state.viewportSize / 3f) } },
                content = {
                    FontIconSolid8(
                        type = FontIconPrimitive.CaretLeft,
                        contentDescription = null
                    )
                },
                iconOnly = true
            )
        }

        AnimatedVisibility(
            visible = isNextVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = FluentDuration.ShortDuration)),
            exit = fadeOut(animationSpec = tween(durationMillis = FluentDuration.ShortDuration)),
            modifier = Modifier.padding(end = 2.dp).align(Alignment.CenterEnd)
        ) {
            SubtleButton(
                onClick = { scope.launch { state.animateScrollBy(state.viewportSize / 3f) } },
                content = {
                    FontIconSolid8(
                        type = FontIconPrimitive.CaretRight,
                        contentDescription = null
                    )
                },
                iconOnly = true
            )
        }
    }
}

private data class LiteFilterModifierNodeElement(
    private val state: ScrollState,
    private val density: Density,
) : ModifierNodeElement<LiteFilterBringIntoViewModifierNode>() {
    override fun create(): LiteFilterBringIntoViewModifierNode {
        return LiteFilterBringIntoViewModifierNode(
            state = state,
            density = density
        )
    }

    override fun update(node: LiteFilterBringIntoViewModifierNode) {
        node.state = state
        node.density = density
    }
}

@OptIn(ExperimentalFoundationApi::class)
private class LiteFilterBringIntoViewModifierNode(
    var state: ScrollState,
    var density: Density,
) : Modifier.Node(), BringIntoViewModifierNode {

    override suspend fun bringIntoView(
        childCoordinates: LayoutCoordinates,
        boundsProvider: () -> Rect?
    ) {
        Snapshot.withoutReadObservation {
            if (!childCoordinates.isAttached || !isAttached) return@withoutReadObservation
            val localRect = requireLayoutCoordinates().localBoundingBoxOf(childCoordinates)
            val startSize = with(density) { 44.dp.toPx() }
            val endSize = startSize
            val targetRect = when {
                state.canScrollForward && state.viewportSize - localRect.right - state.value < endSize -> {
                    localRect.copy(
                        right = localRect.right + endSize
                    )
                }

                state.canScrollBackward && localRect.left < state.value + startSize -> {
                    localRect.copy(
                        left = localRect.left - startSize
                    )
                }
                else -> localRect
            }
            bringIntoView { targetRect }
        }
    }
}

@Stable
private class PaddingShape(
    private val clipStart: Boolean,
    private val clipEnd: Boolean,
    private val startSize: Dp = 44.dp,
    private val endSize: Dp = 44.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return with(density) {
            Outline.Rectangle(
                Rect(
                    left = if (clipStart) startSize.toPx() else 0f,
                    top = 0f,
                    right = size.width - if (clipEnd) endSize.toPx() else 0f,
                    bottom = size.height
                )
            )
        }
    }
}