package io.github.composefluent.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlin.jvm.JvmInline

/**
 * Applies a [Material] effect to the content.
 *
 * This composable allows you to apply a material overlay to its content.
 * The material effect is defined by the [material] parameter and can be
 * enabled or disabled using the [enabled] lambda. When enabled, the
 * composable applies a haze effect based on the material's style.
 * When disabled, it fills the background with the first tint color defined in
 * the material's style.
 *
 * @param material The [Material] to apply to the content.
 * @param modifier The modifier to be applied to the container.
 * @param enabled A lambda that returns true if the material effect should be
 *   enabled, false otherwise. Defaults to `supportMaterial()`.
 * @param border An optional [BorderStroke] to draw around the material.
 * @param content The content to be displayed with the material effect.
 */
@ExperimentalFluentApi
@Composable
fun MaterialContainerScope.Material(
    material: Material,
    modifier: Modifier = Modifier,
    enabled: () -> Boolean = { supportMaterial() },
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Layer(
        modifier = modifier.materialOverlay(material = material, enabled = enabled),
        color = if (enabled()) Color.Transparent else material.style.tints.first().color,
        border = border,
        backgroundSizing = BackgroundSizing.InnerBorderEdge
    ) {
        content()
    }
}

/**
 * A composable function that provides a container for applying material effects.
 *
 * This function creates a [Box] that acts as a container for material-related components.
 * It utilizes the [MaterialContainerScope] to provide access to material-specific modifiers and properties
 * within the content lambda.
 *
 * @param modifier The modifier to be applied to the container.
 * @param content The composable content to be placed inside the material container.
 *                This content lambda has access to the [MaterialContainerScope].
 */
@ExperimentalFluentApi
@Composable
fun MaterialContainer(
    modifier: Modifier = Modifier,
    content: @Composable MaterialContainerScope.() -> Unit
) {
    Box(modifier) {
        val scope = remember(this) { MaterialContainerScopeImpl(this) }
        scope.content()
    }
}

@OptIn(ExperimentalFluentApi::class)
private class MaterialContainerScopeImpl(boxScope: BoxScope) : MaterialContainerScope,
    BoxScope by boxScope {
    private val hazeState = HazeState()

    override fun Modifier.behindMaterial(): Modifier {
        return then(Modifier.hazeSource(state = hazeState))
    }

    override fun Modifier.materialOverlay(material: Material, enabled: () -> Boolean): Modifier {
        return when {
            !enabled() -> this
            else -> hazeEffect(
                state = hazeState,
                style = material.style
            )
        }
    }
}

/**
 * A scope interface for applying material-related modifiers within a [MaterialContainer].
 *
 * This interface provides a set of functions that can be used to apply material design
 * effects, such as behind-material blurring and material overlays, to composable
 * elements within a [MaterialContainer].
 *
 * The [MaterialContainerScope] is designed to be used as the receiver scope within the
 * content lambda of [MaterialContainer]. This allows you to directly use the provided
 * extension functions (e.g., [behindMaterial], [materialOverlay]) on [Modifier] instances
 * within the content lambda, making it easier to apply material effects to individual
 * components.
 *
 * Example:
 * ```
 * MaterialContainer {
 *     // Background layer
 *     Column(
 *         Modifier
 *             .fillMaxSize()
 *             .behindMaterial() // Applies a behind-material effect
 *     ) {
 *         // Content goes here
 *     }
 *
 *     // Popup layer
 *     Layer(
 *         Modifier.materialOverlay(MaterialDefaults.acrylicDefault()) // Applies a material overlay
 *     ) {
 *        // Content goes here
 *     }
 * ```
 */
@ExperimentalFluentApi
interface MaterialContainerScope : BoxScope {

    /**
     * Places the content behind the material source.
     *
     * By using this modifier, the content it's applied to will be
     * treated as a source for the [HazeState]. It allows for the
     * content behind to contribute to the material effect.
     *
     * @return A [Modifier] that positions the content as a source behind the material.
     */
    fun Modifier.behindMaterial(): Modifier

    @Deprecated("Use materialOverlay instead")
    fun Modifier.acrylicOverlay(
        tint: Color,
        shape: Shape,
        enabled: () -> Boolean = { true }
    ): Modifier {
        return materialOverlay(
            material = MaterialDefaults.customAcrylic(tint),
            enabled = enabled
        )
    }

    /**
     * Applies a material overlay effect to this [Modifier].
     *
     * This function adds a visual effect that simulates a material overlay,
     * such as acrylic or mica, to the composable element. The appearance of the
     * overlay is determined by the provided [material].
     *
     * @param material The [Material] to be applied as an overlay. This determines
     *                 the style and appearance of the overlay.
     * @param enabled A lambda that returns `true` if the material overlay should
     *                be applied, `false` otherwise. This allows for conditional
     *                application of the effect. By default, it's enabled.
     * @return A new [Modifier] that includes the material overlay effect.
     */
    fun Modifier.materialOverlay(
        material: Material,
        enabled: () -> Boolean = { true }
    ): Modifier

}

@JvmInline
@Immutable
value class Material(val style: HazeStyle)

/**
 * Provides default [Material] configurations for common scenarios.
 *
 * This object includes predefined styles for various acrylic and mica materials,
 * as well as utility functions to create custom acrylic materials. These defaults
 * are designed to match the Fluent Design System's material styles.
 */
object MaterialDefaults {

    private const val acrylicNoiseFactor = 0.02f

    private val acrylicBlurRadius = 60.dp

    private val micaBlurRadius = 240.dp

    /**
     * A [Material] representing a thin, translucent acrylic material.
     *
     * This material is designed to provide a subtle, semi-transparent overlay effect,
     * suitable for layering over other content without fully obscuring it.
     * It adapts to dark and light modes, changing its base color and luminosity
     * to ensure readability and aesthetic consistency.
     *
     * @param isDark `true` if the material should be rendered in dark mode,
     *   `false` for light mode. Defaults to the current `darkMode` state of the [FluentTheme].
     * @return A [Material] object representing the thin acrylic material.
     */
    @Composable
    @ReadOnlyComposable
    fun thinAcrylic(
        isDark: Boolean = FluentTheme.colors.darkMode,
    ): Material = acrylicMaterial(
        containerColor = if (isDark) {
            Color(0xFF545454)
        } else {
            Color(0xFFD3D3D3)
        },
        fallbackColor = if (isDark) {
            Color(0x545454, 0.64f)
        } else {
            Color(0xD3D3D3, 0.6f)
        },
        isDark = isDark,
        lightTintOpacity = 0f,
        lightLuminosityOpacity = 0.44f,
        darkTintOpacity = 0f,
        darkLuminosityOpacity = 0.64f,
    )

    /**
     * A [Material] representing an acrylic material with accent color, designed for the most translucent layer.
     *
     * This function creates a material that simulates the appearance of translucent acrylic, incorporating
     * an accent color. It's intended for use in UI layers where a high degree of translucency and
     * visual depth are desired. This material adapts to light and dark color schemes, adjusting its
     * tint and luminosity to maintain a consistent appearance.
     *
     * @param tint The primary accent color for the material. Defaults to the `accentAcrylic.base` color
     *             from the current [FluentTheme].
     * @param fallback The fallback color used when the acrylic effect is not supported. Defaults to
     *                 the `accentAcrylic.baseFallback` color from the current [FluentTheme].
     * @param isDark Determines whether to apply dark mode settings to the material. Defaults to the
     *               current `darkMode` setting from [FluentTheme].
     * @return A [Material] object configured with the specified accent acrylic style.
     */
    @Composable
    @ReadOnlyComposable
    fun accentAcrylicBase(
        tint: Color = FluentTheme.colors.background.accentAcrylic.base,
        fallback: Color = FluentTheme.colors.background.accentAcrylic.baseFallback,
        isDark: Boolean = FluentTheme.colors.darkMode
    ): Material = acrylicMaterial(
        containerColor = tint,
        fallbackColor = fallback,
        isDark = isDark,
        lightTintOpacity = 0.8f,
        lightLuminosityOpacity = 0.8f,
        darkTintOpacity = 0.8f,
        darkLuminosityOpacity = 0.8f,
    )

    /**
     * A [HazeStyle] which implements a translucent material with accent color, intended for use as a popup container background.
     *
     * This material applies an acrylic effect with the specified accent color, providing a subtle
     * visual distinction for popup elements. It adapts to both light and dark themes and provides
     * fallback colors for environments where the acrylic effect may not be supported.
     *
     * @param tint The accent color to use for the acrylic effect. Defaults to the `accentAcrylic.default` color in [FluentTheme].
     * @param fallback The fallback color to use if the acrylic effect is not supported. Defaults to the `accentAcrylic.defaultFallback` color in [FluentTheme].
     * @param isDark `true` if the dark theme is active, `false` otherwise. Determines the opacity levels of the acrylic effect. Defaults to [FluentTheme]'s `darkMode` setting.
     */
    @Composable
    @ReadOnlyComposable
    fun accentAcrylicDefault(
        tint: Color = FluentTheme.colors.background.accentAcrylic.default,
        fallback: Color = FluentTheme.colors.background.accentAcrylic.defaultFallback,
        isDark: Boolean = FluentTheme.colors.darkMode
    ): Material = acrylicMaterial(
        containerColor = tint,
        fallbackColor = fallback,
        isDark = isDark,
        lightTintOpacity = 0.8f,
        lightLuminosityOpacity = 0.9f,
        darkTintOpacity = 0.8f,
        darkLuminosityOpacity = 0.8f,
    )

    /**
     * A [HazeStyle] representing a translucent acrylic material intended for use as the most translucent background layer.
     *
     * This function provides a material style suitable for backgrounds where a high degree of translucency is desired.
     * It adapts to dark and light modes by adjusting tint and luminosity opacities, creating a subtle visual effect.
     *
     * @param isDark Determines whether the material should be rendered in dark mode.
     *   Defaults to `FluentTheme.colors.darkMode`. When `true`, the material uses darker tint and luminosity settings.
     * @return A [Material] object configured for a highly translucent acrylic effect.
     */
    @Composable
    @ReadOnlyComposable
    fun acrylicBase(
        isDark: Boolean = FluentTheme.colors.darkMode,
    ): Material = acrylicMaterial(
        containerColor = FluentTheme.colors.background.acrylic.base,
        fallbackColor = FluentTheme.colors.background.acrylic.baseFallback,
        isDark = isDark,
        lightTintOpacity = 0f,
        lightLuminosityOpacity = 0.9f,
        darkTintOpacity = 0.5f,
        darkLuminosityOpacity = 0.96f,
    )

    /**
     * A [HazeStyle] which implements a translucent material, suitable for popup container backgrounds.
     *
     * This material provides a semi-transparent effect, ideal for use behind popups or dialogs.
     * It adapts to light and dark themes, adjusting its opacity and luminosity to ensure
     * consistency and readability. In light mode, it has a subtle luminosity effect without any tint, while
     * in dark mode, it uses a slight tint and luminosity.
     *
     * @param isDark `true` if the dark theme is active, `false` otherwise. This determines the
     *               opacity and luminosity levels. Defaults to [FluentTheme]'s `darkMode` setting.
     */
    @Composable
    @ReadOnlyComposable
    fun acrylicDefault(
        isDark: Boolean = FluentTheme.colors.darkMode,
    ): Material = acrylicMaterial(
        containerColor = FluentTheme.colors.background.acrylic.default,
        fallbackColor = FluentTheme.colors.background.acrylic.defaultFallback,
        isDark = isDark,
        lightTintOpacity = 0f,
        lightLuminosityOpacity = 0.85f,
        darkTintOpacity = 0.15f,
        darkLuminosityOpacity = 0.96f,
    )

    /**
     * A [HazeStyle] which implements a translucent application background material.
     *
     * This material provides a subtle translucent effect, suitable for application backgrounds.
     * It adapts its appearance based on whether the app is in dark mode or light mode.
     *
     * @param isDark `true` if dark mode is enabled, `false` otherwise. Determines the colors
     *               and opacities used for the effect. Defaults to the current dark mode
     *               setting from [FluentTheme.colors.darkMode].
     */
    @Composable
    @ReadOnlyComposable
    fun mica(
        isDark: Boolean = FluentTheme.colors.darkMode,
    ): Material = micaMaterial(
        containerColor = FluentTheme.colors.background.mica.base,
        fallbackColor = FluentTheme.colors.background.mica.baseFallback,
        isDark = isDark,
        lightTintOpacity = 0.5f,
        lightLuminosityOpacity = 1f,
        darkTintOpacity = 0.8f,
        darkLuminosityOpacity = 1f,
    )

    /**
     * A [HazeStyle] which implements a translucent application background material specifically designed for
     * tab experiences. It provides an alternative to [mica] with adjusted opacity for improved tab
     * separation and visibility. The dark mode uses no tint, while light mode retains a tint, ensuring
     * appropriate contrast and visual appeal across both themes.
     */
    @Composable
    @ReadOnlyComposable
    fun micaAlt(
        isDark: Boolean = FluentTheme.colors.darkMode,
    ): Material = micaMaterial(
        containerColor = FluentTheme.colors.background.mica.baseAlt,
        fallbackColor = FluentTheme.colors.background.mica.baseAltFallback,
        isDark = isDark,
        lightTintOpacity = 0.5f,
        lightLuminosityOpacity = 1f,
        darkTintOpacity = 0.0f,
        darkLuminosityOpacity = 1f,
    )

    /**
     * Creates a custom acrylic material with the specified properties.
     *
     * @param tint The base color for the acrylic effect.
     * @param fallback The fallback color to use if the acrylic effect is not supported. Defaults to `tint`.
     * @param backgroundColor The background color behind the material, Defaults to `fallback`.
     * @param isDark Determines if the material should be rendered in dark mode. Defaults to `true` if the luminance of `tint` is less than 0.5, otherwise `false`.
     * @param lightTintOpacity The opacity of the tint color when in light mode. Defaults to `0.8f`.
     * @param lightLuminosityOpacity The opacity of the luminosity effect when in light mode. Defaults to `0.8f`.
     * @param darkTintOpacity The opacity of the tint color when in dark mode. Defaults to `lightTintOpacity`.
     * @param darkLuminosityOpacity The opacity of the luminosity effect when in dark mode. Defaults to `darkTintOpacity`.
     * @return A [Material] object representing the custom acrylic material.
     */
    fun customAcrylic(
        tint: Color,
        fallback: Color = tint,
        backgroundColor: Color = fallback,
        isDark: Boolean = tint.luminance() < 0.5f,
        lightTintOpacity: Float = 0.8f,
        lightLuminosityOpacity: Float = 0.8f,
        darkTintOpacity: Float = lightTintOpacity,
        darkLuminosityOpacity: Float = darkTintOpacity,
    ): Material {
        return acrylicMaterial(
            containerColor = tint,
            fallbackColor = fallback,
            backgroundColor = backgroundColor,
            isDark = isDark,
            lightTintOpacity = lightTintOpacity,
            lightLuminosityOpacity = lightLuminosityOpacity,
            darkTintOpacity = darkTintOpacity,
            darkLuminosityOpacity = darkLuminosityOpacity,
        )
    }

    private fun acrylicMaterial(
        containerColor: Color,
        fallbackColor: Color = containerColor,
        backgroundColor: Color = fallbackColor,
        isDark: Boolean,
        lightTintOpacity: Float,
        lightLuminosityOpacity: Float,
        darkTintOpacity: Float,
        darkLuminosityOpacity: Float,
    ): Material = material(
        containerColor = containerColor,
        fallbackColor = fallbackColor,
        backgroundColor = backgroundColor,
        isDark = isDark,
        lightTintOpacity = lightTintOpacity,
        lightLuminosityOpacity = lightLuminosityOpacity,
        darkTintOpacity = darkTintOpacity,
        darkLuminosityOpacity = darkLuminosityOpacity,
        blurRadius = acrylicBlurRadius,
        noiseFactor = acrylicNoiseFactor,
    )

    @ReadOnlyComposable
    @Composable
    private fun micaMaterial(
        containerColor: Color = FluentTheme.colors.background.mica.base,
        fallbackColor: Color = FluentTheme.colors.background.solid.base,
        isDark: Boolean = FluentTheme.colors.darkMode,
        lightTintOpacity: Float,
        lightLuminosityOpacity: Float,
        darkTintOpacity: Float,
        darkLuminosityOpacity: Float,
    ): Material = material(
        containerColor = containerColor,
        fallbackColor = fallbackColor,
        isDark = isDark,
        lightTintOpacity = lightTintOpacity,
        lightLuminosityOpacity = lightLuminosityOpacity,
        darkTintOpacity = darkTintOpacity,
        darkLuminosityOpacity = darkLuminosityOpacity,
        blurRadius = micaBlurRadius,
        noiseFactor = 0f,
    )

    private fun material(
        containerColor: Color,
        isDark: Boolean,
        fallbackColor: Color = containerColor,
        backgroundColor: Color = fallbackColor,
        blurRadius: Dp,
        noiseFactor: Float,
        lightTintOpacity: Float,
        lightLuminosityOpacity: Float,
        darkTintOpacity: Float,
        darkLuminosityOpacity: Float,
    ): Material = Material(
        HazeStyle(
            blurRadius = blurRadius,
            noiseFactor = noiseFactor,
            backgroundColor = backgroundColor,
            tints = listOf(
                HazeTint(
                    color = containerColor.copy(if (isDark) darkTintOpacity else lightTintOpacity),
                    blendMode = BlendMode.Hardlight,
                ),
                HazeTint(
                    color = containerColor.copy(if (isDark) darkLuminosityOpacity else lightLuminosityOpacity),
                    blendMode = BlendMode.Luminosity,
                )
            ),
            fallbackTint = HazeTint(fallbackColor),
        )
    )

    private fun Color(color: Int, alpha: Float): Color {
        return Color(color).copy(alpha = alpha)
    }
}

internal expect fun supportMaterial(): Boolean