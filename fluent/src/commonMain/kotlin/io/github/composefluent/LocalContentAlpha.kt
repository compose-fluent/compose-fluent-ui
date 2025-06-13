package io.github.composefluent

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal used to pass the default content alpha down the tree.
 *
 * This is used to provide a default alpha for text, icons and other content that can
 * be changed by a parent component.
 */
val LocalContentAlpha = compositionLocalOf { 1f }