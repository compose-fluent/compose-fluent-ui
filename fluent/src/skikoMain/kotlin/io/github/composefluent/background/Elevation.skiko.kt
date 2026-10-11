package io.github.composefluent.background

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.skiaPaint
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

internal actual fun Paint.applyShadowMaskFilter(radius: Float) {
    // Match Skia's blur radius to Gaussian sigma conversion.
    val sigma = if (radius > 0f) 0.57735f * radius + 0.5f else 0f
    skiaPaint.maskFilter = MaskFilter.makeBlur(
        mode = FilterBlurMode.NORMAL,
        sigma = sigma
    )
}

internal actual fun supportFluentElevation(): Boolean {
    return true
}
