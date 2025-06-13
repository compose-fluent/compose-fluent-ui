package io.github.composefluent.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentColor
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

/**
 * A composable that applies the Mica background effect.
 *
 * This function provides a basic Mica background using the `mica.base` color from the Fluent theme.
 * It also sets the content color to the primary text color from the Fluent theme.
 *
 * @param modifier The modifier to be applied to the Mica background.
 * @param content The composable content to be displayed on top of the Mica background.
 */
@Composable
fun Mica(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier.background(FluentTheme.colors.background.mica.base)) {
        CompositionLocalProvider(LocalContentColor provides FluentTheme.colors.text.text.primary) {
            content()
        }
    }
}

/**
 * A composable that creates a Mica effect, providing a translucent, layered appearance.
 *
 * This function renders the content on top of a blurred background, creating the Mica effect.
 *
 * @param background The composable to be used as the background. This will be blurred to create the Mica effect.
 * @param modifier The modifier to be applied to the root container.
 * @param content The content to be displayed on top of the Mica effect.
 */
@Composable
fun Mica(
    background: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        propagateMinConstraints = true
    ) {
        val hazeState = remember { HazeState() }
        Box(
            propagateMinConstraints = true,
            modifier = Modifier.matchParentSize().hazeSource(state = hazeState)
        ) {
            background()
        }
        Box(modifier = Modifier.fillMaxSize().hazeEffect(state = hazeState, style = MaterialDefaults.mica().style)) {
            CompositionLocalProvider(
                LocalContentColor provides FluentTheme.colors.text.text.primary
            ) {
                content()
            }
        }
    }
}