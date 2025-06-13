package io.github.composefluent

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * CompositionLocal to pass the content color down the tree. For example, a `Button` may provide
 * a default content color for the text and iconography inside it.
 *
 * This is typically used with the alpha applied to the color to provide a useful default for
 * text and iconography.
 */
val LocalContentColor = compositionLocalOf { Color.Unspecified }