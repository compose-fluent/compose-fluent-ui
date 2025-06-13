package io.github.composefluent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import com.konyaco.fluent.util.ARGB
import com.konyaco.fluent.util.ColorScale

/**
 * Represents the color palette for the Fluent UI design system.
 *
 * This class provides a comprehensive set of colors used throughout the Fluent design system,
 * including text colors, control colors, background colors, and more. It supports both light
 * and dark modes, with colors dynamically generated based on the current theme.
 *
 * @property darkMode Whether the color set should be in dark mode.
 * @property shades The base color shades to be used.
 * @property text Colors used for text elements, including primary, secondary, and disabled text colors.
 * @property control Colors used for controls, including default, secondary, and disabled states.
 * @property controlAlt Alternate colors used for controls.
 * @property controlSolid Solid colors used for controls.
 * @property controlStrong Strong colors used for controls.
 * @property subtleFill Colors for subtle fill effects.
 * @property controlOnImage Colors for controls displayed on top of images.
 * @property fillAccent Colors for accent fills.
 * @property background Colors for backgrounds, including cards, layers, and solid backgrounds.
 * @property stroke Colors for strokes and borders around elements.
 * @property borders Colors for borders around elements.
 * @property system System colors, such as attention, success, caution, and critical.
 *
 * All color properties are mutable states, which means they will automatically recompose
 * when their values change.
 *
 * @param shades The [Shades] object defining the base, light, and dark shades of the accent color.
 * @param darkMode A boolean indicating whether to use the dark mode color scheme.
 */
@Stable
class Colors(
    shades: Shades,
    darkMode: Boolean
) {
    var darkMode by mutableStateOf(darkMode)
        internal set
    var shades by mutableStateOf(shades)
        internal set
    var text by mutableStateOf(generateTextColors(shades, darkMode))
        internal set
    var control by mutableStateOf(generateControlColors(shades, darkMode))
        internal set
    var controlAlt by mutableStateOf(generateControlAltColors(shades, darkMode))
        internal set
    var controlSolid by mutableStateOf(generateControlSolidColors(shades, darkMode))
        internal set
    var controlStrong by mutableStateOf(generateControlStrongColors(shades, darkMode))
        internal set
    var subtleFill by mutableStateOf(generateSubtleFillColors(shades, darkMode))
        internal set
    var controlOnImage by mutableStateOf(generateControlOnImageColors(darkMode))
        internal set
    var fillAccent by mutableStateOf(generateFillAccentColors(shades, darkMode))
        internal set
    var background by mutableStateOf(generateBackground(shades, darkMode))
        internal set
    var stroke by mutableStateOf(generateStroke(shades, darkMode))
        internal set
    var borders by mutableStateOf(generateBorders(fillAccent, stroke, darkMode))
        internal set
    var system by mutableStateOf(generateSystemColors(darkMode))
        internal set
}

/**
 * Represents the border brushes used for various UI elements.
 *
 * @property control The brush used for general control borders.
 * @property accentControl The brush used for borders of accented controls.
 * @property circle The brush used for circular borders.
 * @property textControl The brush used for borders around text controls.
 * @property textControlFocused The brush used for borders around focused text controls.
 */
data class Borders(
    val control: Brush,
    val accentControl: Brush,
    val circle: Brush,
    val textControl: Brush,
    val textControlFocused: Brush
)

/**
 * Represents a set of color shades based on a base color.
 *
 * Each shade is a variation of the [base] color, providing a range of light and dark tones.
 * This set of colors is designed to provide a harmonious palette for UI elements.
 *
 * @property base The core, or primary, color of the shade set.
 * @property light1 A lighter variation of the base color.
 * @property light2 A second, even lighter variation of the base color.
 * @property light3 The lightest variation of the base color.
 * @property dark1 A darker variation of the base color.
 * @property dark2 A second, even darker variation of the base color.
 * @property dark3 The darkest variation of the base color.
 */
data class Shades(
    val base: Color,
    val light1: Color,
    val light2: Color,
    val light3: Color,
    val dark1: Color,
    val dark2: Color,
    val dark3: Color,
) {
    companion object {
        /**
         * Generates shades from `accentColor`
         */
        @Stable
        fun generate(accentColor: Color, darkMode: Boolean): Shades {
            val scale = ColorScale.getPaletteScale(ARGB.fromColor(accentColor))
            val steps = 11
            val entries = mutableListOf<Color>()
            for (i in 0 until steps) {
                val position = i.toDouble() / (steps.toDouble() - 1)
                val c = scale.getColor(position)
                entries.add(c.toColor())
            }

            if (darkMode) {
                val light3 = entries[2]
                val light2 = entries[3]
                val light1 = entries[4]
                val dark1 = entries[6]
                val dark2 = entries[7]
                val dark3 = entries[8]
                return Shades(accentColor, light1, light2, light3, dark1, dark2, dark3)
            } else {
                val dark3 = entries[2]
                val dark2 = entries[3]
                val dark1 = entries[4]
                val light1 = entries[6]
                val light2 = entries[7]
                val light3 = entries[8]
                return Shades(accentColor, light1, light2, light3, dark1, dark2, dark3)
            }

        }
    }
}

/**
 * Represents the text colors used in the Fluent Design System.
 *
 * This data class groups together different categories of text colors,
 * including the standard text colors, accent text colors, and text colors
 * specifically designed to be displayed on accent backgrounds.
 *
 * @property text A [ColorCompound] object containing the primary, secondary,
 *   tertiary, and disabled colors for standard text.
 * @property accent A [ColorCompound] object containing the primary, secondary,
 *   tertiary, and disabled colors for text displayed with an accent treatment.
 * @property onAccent A [TextOnAccentColorCompound] object containing the primary,
 *   secondary, disabled, and selected text colors for text displayed
 *   on accent backgrounds.
 */
data class TextColor(
    val text: ColorCompound,
    val accent: ColorCompound,
    val onAccent: TextOnAccentColorCompound
)

/**
 * Represents a compound color with different levels of emphasis.
 *
 * This data class groups together four colors: primary, secondary, tertiary, and disabled.
 * This is useful for defining color palettes for elements that have multiple states
 * or levels of visual hierarchy, such as text or controls.
 *
 * @property primary The primary color, typically used for the most important elements.
 * @property secondary The secondary color, used for less important elements or supporting text.
 * @property tertiary The tertiary color, used for subtle details or low-emphasis text.
 * @property disabled The color used for disabled or inactive elements.
 */
data class ColorCompound(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val disabled: Color
)

/**
 * Represents the compound color set for text on an accent background.
 *
 * This data class defines the different color states for text elements when placed
 * on a background with an accent color. It includes colors for primary, secondary,
 * disabled, and selected text states.
 *
 * @property primary The primary color for text on an accent background.
 * @property secondary The secondary color for text on an accent background.
 * @property disabled The color for disabled text on an accent background.
 * @property selectedText The color for selected text on an accent background.
 */
data class TextOnAccentColorCompound(
    val primary: Color,
    val secondary: Color,
    val disabled: Color,
    val selectedText: Color,
)

/**
 * Represents the colors used for various controls in the UI.
 *
 * @property default The default color for controls.
 * @property secondary The secondary color for controls, often used for hover or secondary states.
 * @property tertiary The tertiary color for controls, often used for pressed or disabled states.
 * @property quaternary The quaternary color for controls, used for specific visual distinctions.
 * @property disabled The color for disabled controls.
 * @property transparent A transparent color for controls.
 * @property inputActive The color for controls that are active input fields.
 */
data class ControlColors(
    val default: Color,
    val secondary: Color,
    val tertiary: Color,
    val quaternary: Color,
    val disabled: Color,
    val transparent: Color,
    val inputActive: Color,
)

/**
 * Represents a set of alternate control colors.
 *
 * These colors are used for controls in specific contexts where a different visual
 * style is required compared to the default [ControlColors].
 *
 * @property transparent A transparent color for controls.
 * @property secondary A secondary color for alternate controls.
 * @property tertiary A tertiary color for alternate controls.
 * @property quaternary A quaternary color for alternate controls.
 * @property disabled A disabled state color for alternate controls.
 */
data class ControlAltColors(
    val transparent: Color,
    val secondary: Color,
    val tertiary: Color,
    val quaternary: Color,
    val disabled: Color
)

/**
 * Represents a set of solid control colors.
 *
 * These colors are used for controls that have a solid background fill.
 *
 * @property default The default solid background color for controls.
 */
data class ControlSolidColors(
    val default: Color
)

/**
 * Represents the strong colors used for controls.
 *
 * Strong colors are typically used for elements that require higher emphasis,
 * such as borders of focused controls or specific states.
 *
 * @property default The default strong color for controls.
 * @property disabled The strong color for controls in a disabled state.
 */
data class ControlStrongColors(
    val default: Color,
    val disabled: Color
)

/**
 * Represents the colors used for accent fills in the Fluent Design System.
 *
 * These colors are typically used to highlight interactive elements or provide visual emphasis.
 * They are derived from the main accent color and adjusted for different states.
 *
 * @property default The default color for accent fills.
 * @property secondary A secondary color for accent fills, slightly less prominent than the default.
 * @property tertiary A tertiary color for accent fills, even less prominent than the secondary.
 * @property disabled The color used for accent fills when the element is disabled.
 * @property selectedTextBackground The background color for selected text when using accent colors.
 */
data class FillAccentColors(
    val default: Color,
    val secondary: Color,
    val tertiary: Color,
    val disabled: Color,
    val selectedTextBackground: Color
)

/**
 * Represents the colors used for controls displayed on top of images.
 *
 * These colors are specifically designed to provide good contrast and visibility when
 * rendered over varying image content.
 *
 * @property default The default color for controls on images.
 * @property secondary A secondary color for controls on images, typically a lighter shade.
 * @property tertiary A tertiary color for controls on images, typically an even lighter shade.
 * @property disabled The color for disabled controls on images.
 */
data class ControlOnImageColors(
    val default: Color,
    val secondary: Color,
    val tertiary: Color,
    val disabled: Color
)

/**
 * Represents the stroke colors used for borders and outlines of various UI elements.
 *
 * This includes strokes for controls, surfaces, cards, dividers, and focus indicators.
 *
 * @property control Colors for strokes around controls.
 * @property controlStrong Colors for strong strokes around controls.
 * @property surface Colors for strokes around surfaces.
 * @property card Colors for strokes around cards.
 * @property divider Color for divider lines.
 * @property focus Colors for focus indicators.
 */
data class Stroke(
    val control: Control,
    val controlStrong: ControlStrong,
    val surface: Surface,
    val card: Card,
    val divider: Divider,
    val focus: Focus
) {
    /**
     * Represents the control stroke colors used for various UI elements.
     *
     * @property default The default stroke color for controls.
     * @property secondary The secondary stroke color for controls.
     * @property onAccentDefault The default stroke color for controls placed on an accent background.
     * @property onAccentSecondary The secondary stroke color for controls placed on an accent background.
     * @property onAccentTertiary The tertiary stroke color for controls placed on an accent background.
     * @property onAccentDisabled The disabled stroke color for controls placed on an accent background.
     * @property forStrongFillWhenOnImage The stroke color for strong fill controls placed on images.
     */
    data class Control(
        val default: Color,
        val secondary: Color,
        val onAccentDefault: Color,
        val onAccentSecondary: Color,
        val onAccentTertiary: Color,
        val onAccentDisabled: Color,
        val forStrongFillWhenOnImage: Color
    )

    /**
     * Represents a set of strong control colors.
     *
     * These colors are typically used for controls that require a more pronounced visual presence.
     *
     * @property default The default color for strong controls.
     * @property disabled The color for strong controls in a disabled state.
     */
    data class ControlStrong(
        val default: Color,
        val disabled: Color
    )

    /**
     * Surface stroke colors.
     *
     * @property default The default surface stroke color.
     * @property flyout The surface stroke color for flyout elements.
     */
    data class Surface(
        val default: Color,
        val flyout: Color
    )
    
    /**
     * Colors used for strokes and borders around cards.
     *
     * @property default The default stroke color for cards.
     * @property defaultSolid The default solid stroke color for cards.
     */
    data class Card(
        val default: Color,
        val defaultSolid: Color
    )

    /**
     * Represents the color of dividers.
     *
     * @property default The default color for dividers.
     */
    data class Divider(
        val default: Color
    )

    /**
     * Represents the focus colors for UI elements.
     *
     * This data class defines the colors used to indicate when an element has keyboard focus.
     * It typically consists of an outer and inner color for a focus ring or indicator.
     *
     * @property outer The color of the outer focus indicator.
     * @property inner The color of the inner focus indicator.
     */
    data class Focus(
        val outer: Color,
        val inner: Color
    )
}

/**
 * Represents colors used for subtle fill effects, often for subtle buttons or other elements.
 *
 * Subtle fills are semi-transparent colors used to provide a subtle visual distinction
 * for elements like cards or containers without being overly prominent.
 *
 * @property transparent A fully transparent color.
 * @property secondary A secondary subtle fill color.
 * @property tertiary A tertiary subtle fill color.
 * @property disabled A subtle fill color for disabled states.
 */
data class SubtleFillColors(
    val transparent: Color,
    val secondary: Color,
    val tertiary: Color,
    val disabled: Color
)

/**
 * Represents the various background colors used in the Fluent Design System.
 *
 * This includes colors for cards, smoke effects, layered surfaces, solid backgrounds,
 * Mica and Acrylic materials, and accent-colored Acrylic materials.
 */
data class Background(
    val card: Card,
    val smoke: Smoke,
    val mica: Mica,
    val layer: Layer,
    val layerOnAcrylic: LayerOnAcrylic,
    val layerOnMicaBaseAlt: LayerOnMicaBaseAlt,
    val solid: Solid,
    val acrylic: Acrylic,
    val accentAcrylic: AccentAcrylic
) {
    /**
     * Used to create ‘cards’ - content blocks that live on  page and layer backgrounds.
     */
    data class Card(
        /**
         * Default card color
         */
        val default: Color,
        /**
         * Alternate card color: slightly darker
         */
        val secondary: Color,
        /**
         * Default card hover and pressed color
         */
        val tertiary: Color
    )

    /**
     * Used over windows and desktop to block them out as inaccessible.
     */
    data class Smoke(
        /**
         * Dims backgrounds behinds dialogs
         */
        val default: Color
    )

    /**
     * Used on background colors of any material to create layering.
     */
    data class Layer(
        /**
         * Content layer color
         */
        val default: Color,
        /**
         * Alternate content layer color
         */
        val alt: Color
    )

    /**
     * Used on background colors of any material to create layering.
     */
    data class LayerOnAcrylic(
        /**
         * Content layer color on acrylic surfaces
         */
        val default: Color
    )

    /**
     * Used for fills on Tab control.
     */
    data class LayerOnMicaBaseAlt(
        /**
         * Active Tab Rest
         * Content layer
         */
        val default: Color,
        /**
         * Active Tab Drag
         */
        val tertiary: Color,
        /**
         * Inactive Tab Rest
         */
        val transparent: Color,
        /**
         * Inactive Tab Hover
         */
        val secondary: Color
    )

    /**
     * Solid background colors to place layers, cards, or controls on.
     */
    data class Solid(
        /**
         * Used for the bottom most layer of an experience.
         */
        val base: Color,
        /**
         * Used for the bottom most layer of an experience.
         */
        val baseAlt: Color,
        /**
         * Alternate base color for those who need a darker background color.
         */
        val secondary: Color,
        /**
         * Content layer color
         */
        val tertiary: Color,
        /**
         * Alt content layer color
         */
        val quaternary: Color,
        /**
         * Used for solid default card colors
         */
        val quinary: Color,
        /**
         * Used for solid default card color
         */
        val senary: Color
    )

    /**
     * Mica background colors to place layers, cards, or controls on.
     */
    data class Mica(
        /**
         * Used for the bottom most layer of an experience.
         *
         * Light: #F3F3F3 (FF, 100%), 50% Tint Opacity, 100% Luminosity Opacity
         *
         * Dark: #202020, 80% Tint Opacity, 100% Luminosity opacity
         */
        val base: Color,
        /**
         * Used for the bottom most layer of an experience.
         *
         * Fallback Light: Solid Background / Base (#F3F3F3, 100%)
         *
         * Fallback Dark: Solid Background / Base (#202020, 100%)
         */
        val baseFallback: Color,
        /**
         * Default tab band background color。
         *
         * Light: #DADADA(80, 50%), 100% Luminosity Opacity
         *
         * Dark: #0A0A0A (00, 0%), 100% Luminosity Opacity
         */
        val baseAlt: Color,
        /**
         * Default tab band background color.
         *
         * Fallback Light: Solid Background / Base Alt (#DADADA, 100%)
         *
         * Fallback Dark: Solid Background / Base Alt (#0A0A0A, 100%)
         */
        val baseAltFallback: Color
    )

    /**
     * Acrylic background colors to place layers, cards, or controls on.
     */
    data class Acrylic(
        /**
         * Used for the bottom most layer of an acrylic surface only when the surface will use layers.
         *
         * Light: #F3F3F3 (FF, 100%), 0% Tint Opacity, 90% Luminosity Opacity
         *
         * Dark: #202020, 50% TInt Opacity, 96% Luminosity Opacity
         */
        val base: Color,
        /**
         * Used for the bottom most layer of an acrylic surface only when the surface will use layers.
         *
         * Light Fallback: #EEEEEE (FF, 100%)
         *
         * Dark Fallback: #1C1C1C
         */
        val baseFallback: Color,
        /**
         * Default acrylic recipe used for control flyouts and surfaces that live with in the context of an app.
         *
         * Light: #FCFCFC (FF, 100%), 0% Tint Opacity, 85% Luminosity Opacity
         *
         * Dark: #2C2C2C, 15% Tint Opacity, 96% Luminosity Opacity
         */
        val default: Color,
        /**
         * Default acrylic recipe used for control flyouts and surfaces that live with in the context of an app.
         *
         * Light Fallback: #F9F9F9 (FF, 100%)
         *
         * Dark Fallback: #2C2C2C
         */
        val defaultFallback: Color
    )

    /**
     * Acrylic background colors to place layers, cards, or controls on.
     */
    data class AccentAcrylic(
        /**
         * Used for the bottom most layer of an acrylic surface only when the surface will use layers.
         *
         * Light: Light 3, 80% Tint Opacity, 80% Luminosity Opacity
         *
         * Dark: Dark 2, 80% Tint Opacity, 80% Luminosity Opacity
         */
        val base: Color,
        val baseFallback: Color,
        /**
         * Default acrylic recipe used for control flyouts and surfaces that live with in the context of an app.
         *
         * Light: Light 3, 80% Tint Opacity, 90% Luminosity Opacity
         *
         * Dark: Dark 1, 80% Tint Opacity, 80% Luminosity Opacity
         */
        val default: Color,
        val defaultFallback: Color
    )
}

/**
 * Represents system-specific colors used for various states and feedback.
 *
 * These colors are typically used to indicate status or provide feedback to the user,
 * such as attention, success, caution, and critical states.
 *
 * @property attention The color used to draw attention to an element or state.
 * @property attentionBackground The background color associated with the attention state.
 * @property solidAttentionBackground The solid background color associated with the attention state.
 * @property success The color used to indicate a successful action or state.
 * @property successBackground The background color associated with the success state.
 * @property caution The color used to indicate a cautionary state or potential issue.
 * @property cautionBackground The background color associated with the caution state.
 * @property critical The color used to indicate a critical error or severe issue.
 * @property criticalBackground The background color associated with the critical state.
 * @property neutral The color used for neutral states or elements.
 * @property neutralBackground The background color associated with the neutral state.
 * @property solidNeutral The solid color used for neutral states or elements.
 * @property solidNeutralBackground The solid background color associated with the neutral state.
 */
data class SystemColors(
    val attention: Color,
    val attentionBackground: Color,
    val solidAttentionBackground: Color,
    val success: Color,
    val successBackground: Color,
    val caution: Color,
    val cautionBackground: Color,
    val critical: Color,
    val criticalBackground: Color,
    val neutral: Color,
    val neutralBackground: Color,
    val solidNeutral: Color,
    val solidNeutralBackground: Color,
)

/**
 * Generates a set of [Shades] based on the provided accent color.
 *
 * If the provided accent color has a pre-defined set of shades in [getAccentShades],
 * that set is returned. Otherwise, the default shades are returned.
 *
 * @param accent The base accent color to generate shades from.
 * @return A [Shades] object containing the base color and its light and dark variations.
 */
fun generateShades(accent: Color): Shades {
    return getAccentShades()[accent] ?: getDefaultShades()
}

internal fun getDefaultShades(): Shades = getAccentShades().entries.first().value

internal fun getAccentShades() = mapOf<Color, Shades>(
    Color(0xFF0078D4) to Shades(
        base = Color(0xFF0078D4),
        light1 = Color(0xFF0093F9),
        light2 = Color(0xFF60CCFE),
        light3 = Color(0xFF98ECFE),
        dark1 = Color(0xFF005EB7),
        dark2 = Color(0xFF003D92),
        dark3 = Color(0xFF001968)
    ),
)


/**
 * Returns the content color for a given background color.
 *
 * This function determines an appropriate color for text or other content to be
 * displayed on top of the provided `backgroundColor` to ensure sufficient contrast
 * and readability according to the Fluent design system.
 *
 * If no specific content color is defined for the given background color within the
 * current [FluentTheme.colors], it falls back to the [LocalContentColor.current].
 *
 * @param backgroundColor The background color for which to determine the content color.
 * @return The content color that provides sufficient contrast with the background.
 */
@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color) =
    FluentTheme.colors.contentColorFor(backgroundColor).takeOrElse { LocalContentColor.current }

/**
 * Returns the default content color for the given background color.
 *
 * This function provides a suggested content color (like text color) that ensures sufficient
 * contrast and legibility when placed on the provided [backgroundColor]. The determination
 * is based on the Fluent UI color palette and aims to follow accessibility guidelines.
 *
 * @param backgroundColor The background color for which to determine the content color.
 * @return The suggested content color for the given [backgroundColor].
 */
fun Colors.contentColorFor(backgroundColor: Color): Color {
    // TODO: Remove this
    return when (backgroundColor) {
        shades.base, shades.dark1, shades.dark2, shades.dark3,
        shades.light1, shades.light2, shades.light3,
        system.caution, system.attention, system.success,
        system.critical, system.solidNeutral -> text.onAccent.primary

        else -> text.text.primary
    }
}

internal fun generateTextColors(shades: Shades, darkMode: Boolean): TextColor =
    if (darkMode) TextColor(
        text = ColorCompound(
            primary = Color(0xFFFFFFFF),
            secondary = Color(0xC8FFFFFF),
            tertiary = Color(0x8BFFFFFF),
            disabled = Color(0x5DFFFFFF)
        ),
        accent = ColorCompound(
            primary = shades.light3,
            secondary = shades.light3,
            tertiary = shades.light2,
            disabled = Color(0x5DFFFFFF)
        ),
        onAccent = TextOnAccentColorCompound(
            primary = Color(0xFF000000),
            secondary = Color(0x80000000),
            disabled = Color(0x87FFFFFF),
            selectedText = Color(0xFFFFFFFF)
        )
    )
    else TextColor(
        text = ColorCompound(
            primary = Color(0xE4000000),
            secondary = Color(0x9B000000),
            tertiary = Color(0x72000000),
            disabled = Color(0x5C000000)
        ),
        accent = ColorCompound(
            shades.dark2,
            shades.dark3,
            shades.dark1,
            Color(0x5C000000)
        ),
        onAccent = TextOnAccentColorCompound(
            primary = Color(0xFFFFFFFF),
            secondary = Color(0xB3FFFFFF),
            disabled = Color(0xFFFFFFFF),
            selectedText = Color(0xFFFFFFFF)
        )
    )

internal fun generateControlColors(shades: Shades, darkMode: Boolean): ControlColors =
    if (darkMode) ControlColors(
        default = Color(0x0FFFFFFF),
        secondary = Color(0x15FFFFFF),
        tertiary = Color(0x08FFFFFF),
        quaternary = Color(0x0FFFFFFF),
        disabled = Color(0x0BFFFFFF),
        transparent = Color(0x00FFFFFF),
        inputActive = Color(0xB31E1E1E)
    )
    else ControlColors(
        default = Color(0xB3FFFFFF),
        secondary = Color(0x80F9F9F9),
        tertiary = Color(0x4DF9F9F9),
        quaternary = Color(0xC2F3F3F3),
        disabled = Color(0x4DF9F9F9),
        transparent = Color(0x00FFFFFF),
        inputActive = Color(0xFFFFFFFF)
    )

internal fun generateControlAltColors(shades: Shades, darkMode: Boolean): ControlAltColors =
    if (darkMode) ControlAltColors(
        transparent = Color(0x00FFFFFF),
        secondary = Color(0x19000000),
        tertiary = Color(0x0BFFFFFF),
        quaternary = Color(0x12FFFFFF),
        disabled = Color(0x00FFFFFF)
    ) else ControlAltColors(
        transparent = Color(0x00FFFFFF),
        secondary = Color(0x06000000),
        tertiary = Color(0x0F000000),
        quaternary = Color(0x18000000),
        disabled = Color(0x00FFFFFF)
    )

internal fun generateControlSolidColors(shades: Shades, darkMode: Boolean): ControlSolidColors =
    if (darkMode) ControlSolidColors(default = Color(0xFF454545))
    else ControlSolidColors(default = Color(0xFFFFFFFF))

internal fun generateControlStrongColors(shades: Shades, darkMode: Boolean): ControlStrongColors =
    if (darkMode) ControlStrongColors(
        default = Color(0x8BFFFFFF),
        disabled = Color(0x3FFFFFFF)
    )
    else ControlStrongColors(
        default = Color(0x72000000),
        disabled = Color(0x51000000)
    )

internal fun generateSubtleFillColors(shades: Shades, darkMode: Boolean): SubtleFillColors =
    if (darkMode) SubtleFillColors(
        transparent = Color(0x00FFFFFF),
        secondary = Color(0x0FFFFFFF),
        tertiary = Color(0x0BFFFFFF),
        disabled = Color(0x00FFFFFF)
    ) else SubtleFillColors(
        transparent = Color(0x00000000),
        secondary = Color(0x09000000),
        tertiary = Color(0x06000000),
        disabled = Color(0x00000000)
    )

internal fun generateControlOnImageColors(darkMode: Boolean): ControlOnImageColors {
    return if (darkMode) {
        ControlOnImageColors(
            default = Color(0xB31C1C1C),
            secondary = Color(0xFF1A1A1A),
            tertiary = Color(0xFF131313),
            disabled = Color(0xFF1E1E1E)
        )
    } else {
        ControlOnImageColors(
            default = Color(0xC9FFFFFF),
            secondary = Color(0xFFF3F3F3),
            tertiary = Color(0xFFEBEBEB),
            disabled = Color(0x00FFFFFF)
        )
    }
}

internal fun generateFillAccentColors(shades: Shades, darkMode: Boolean): FillAccentColors =
    if (darkMode) FillAccentColors(
        default = shades.light2,
        secondary = shades.light2.copy(0.9f),
        tertiary = shades.light2.copy(0.8f),
        disabled = Color(0x28FFFFFF),
        selectedTextBackground = shades.base
    )
    else FillAccentColors(
        default = shades.dark1,
        secondary = shades.dark1.copy(0.9f),
        tertiary = shades.dark1.copy(0.8f),
        disabled = Color(0x37000000),
        selectedTextBackground = shades.base
    )

internal fun generateBackground(shades: Shades, darkMode: Boolean): Background =
    if (darkMode) Background(
        card = Background.Card(
            default = Color(0x0DFFFFFF),
            secondary = Color(0x08FFFFFF),
            tertiary = Color(0x12FFFFFF)
        ),
        smoke = Background.Smoke(
            default = Color(0x4D000000)
        ),
        layer = Background.Layer(default = Color(0x4C3A3A3A), alt = Color(0x0EFFFFFF)),
        layerOnAcrylic = Background.LayerOnAcrylic(
            default = Color(0x09FFFFFF)
        ),
        layerOnMicaBaseAlt = Background.LayerOnMicaBaseAlt(
            default = Color(0x733A3A3A),
            tertiary = Color(0xFF2C2C2C),
            transparent = Color.Transparent,
            secondary = Color(0x0FFFFFFF)
        ),
        solid = Background.Solid(
            base = Color(0xFF202020),
            baseAlt = Color(0xFF0A0A0A),
            secondary = Color(0xFF1C1C1C),
            tertiary = Color(0xFF282828),
            quaternary = Color(0xFF2C2C2C),
            quinary = Color(0xFF333333),
            senary = Color(0xFF373737)
        ),
        mica = Background.Mica(
            base = Color(0xFF202020),
            baseFallback = Color(0xFF202020),
            baseAlt = Color(0x000A0A0A),
            baseAltFallback = Color(0xFF0A0A0A),
        ),
        acrylic = Background.Acrylic(
            base = Color(0xFF202020),
            baseFallback = Color(0xFF1C1C1C),
            default = Color(0xFF2C2C2C),
            defaultFallback = Color(0xFF2C2C2C)
        ),
        accentAcrylic = Background.AccentAcrylic(
            base = shades.dark2,
            baseFallback = shades.dark2,
            default = shades.dark1,
            defaultFallback = shades.dark1
        )
    )
    else Background(
        card = Background.Card(
            default = Color(0xB3FFFFFF),
            secondary = Color(0x80F6F6F6),
            tertiary = Color(0xFFFFFFFF)
        ),
        smoke = Background.Smoke(
            default = Color(0x4D000000)
        ),
        layer = Background.Layer(default = Color(0x80FFFFFF), alt = Color(0xFFFFFFFF)),
        layerOnAcrylic = Background.LayerOnAcrylic(
            default = Color(0x40FFFFFF)
        ),
        layerOnMicaBaseAlt = Background.LayerOnMicaBaseAlt(
            default = Color(0xB3FFFFFF),
            tertiary = Color(0xFFF9F9F9),
            transparent = Color.Transparent,
            secondary = Color(0x0A000000)
        ),
        solid = Background.Solid(
            base = Color(0xFFF3F3F3),
            baseAlt = Color(0xFFDADADA),
            secondary = Color(0xFFEEEEEE),
            tertiary = Color(0xFFF9F9F9),
            quaternary = Color(0xFFFFFFFF),
            quinary = Color(0xFFFDFDFD),
            senary = Color(0xFFFFFFFF)
        ),
        mica = Background.Mica(
            base = Color(0xFFF3F3F3),
            baseFallback = Color(0xFFF3F3F3),
            baseAlt = Color(0xFFDADADA),
            baseAltFallback = Color(0xFFDADADA)
        ),
        acrylic = Background.Acrylic(
            base = Color(0xFFF3F3F3),
            baseFallback = Color(0xFFEEEEEE),
            default = Color(0xFFFCFCFC),
            defaultFallback = Color(0xFFF9F9F9)
        ),
        accentAcrylic = Background.AccentAcrylic(
            base = shades.light3,
            baseFallback = shades.light3,
            default = shades.light3,
            defaultFallback = shades.light3
        )
    )

internal fun generateStroke(shades: Shades, darkMode: Boolean): Stroke =
    if (darkMode) Stroke(
        control = Stroke.Control(
            default = Color(0x12FFFFFF),
            secondary = Color(0x18FFFFFF),
            onAccentDefault = Color(0x14FFFFFF),
            onAccentSecondary = Color(0x23000000),
            onAccentTertiary = Color(0x37000000),
            onAccentDisabled = Color(0x33000000),
            forStrongFillWhenOnImage = Color(0x6B000000)
        ),
        controlStrong = Stroke.ControlStrong(
            default = Color(0x9AFFFFFF),
            disabled = Color(0x28FFFFFF)
        ),
        surface = Stroke.Surface(
            default = Color(0x66757575),
            flyout = Color(0x33000000),
        ),
        card = Stroke.Card(
            default = Color(0x19000000),
            defaultSolid = Color(0xFF1C1C1C)
        ),
        divider = Stroke.Divider(
            default = Color(0x15FFFFFF)
        ),
        focus = Stroke.Focus(
            outer = Color(0xFFFFFFFF),
            inner = Color(0xB3000000)
        )
    )
    else Stroke(
        control = Stroke.Control(
            default = Color(0x0F000000),
            secondary = Color(0x29000000),
            onAccentDefault = Color(0x14FFFFFF),
            onAccentSecondary = Color(0x66000000),
            onAccentTertiary = Color(0x37000000),
            onAccentDisabled = Color(0x0F000000),
            forStrongFillWhenOnImage = Color(0x59FFFFFF)
        ),
        controlStrong = Stroke.ControlStrong(
            default = Color(0x9C000000),
            disabled = Color(0x37000000)
        ),
        surface = Stroke.Surface(
            default = Color(0x66757575),
            flyout = Color(0x0F000000)
        ),
        card = Stroke.Card(
            default = Color(0x0F000000),
            defaultSolid = Color(0xFFEBEBEB)
        ),
        divider = Stroke.Divider(
            default = Color(0x14000000)
        ),
        focus = Stroke.Focus(
            outer = Color(0xE4000000),
            inner = Color(0xB3FFFFFF)
        )
    )

private fun generateBorders(fillAccent: FillAccentColors, stroke: Stroke, darkMode: Boolean): Borders =
    if (darkMode) Borders(
        control = Brush.verticalGradient(
            0.0f to stroke.control.secondary,
            0.0957f to stroke.control.default
        ),
        accentControl = Brush.verticalGradient(
            0.9067f to stroke.control.onAccentDefault,
            1f to stroke.control.onAccentSecondary,
        ),
        circle = Brush.verticalGradient(
            0.0f to stroke.control.secondary,
            0.5002f to stroke.control.default
        ),
        textControl = Brush.verticalGradient(
            1f to stroke.control.default,
            1f to stroke.controlStrong.default
        ),
        textControlFocused = Brush.verticalGradient(
            0.97f to stroke.control.default,
            0.97f to fillAccent.default
        )
    ) else Borders(
        control = Brush.verticalGradient(
            0.9058f to stroke.control.default,
            1f to stroke.control.secondary
        ),
        accentControl = Brush.verticalGradient(
            0.9067f to stroke.control.onAccentDefault,
            1f to stroke.control.onAccentSecondary,
        ),
        circle = Brush.verticalGradient(
            0.5f to stroke.control.default,
            0.95f to stroke.control.secondary
        ),
        textControl = Brush.verticalGradient(
            1f to stroke.control.default,
            1f to stroke.controlStrong.default
        ),
        textControlFocused = Brush.verticalGradient(
            0.97f to stroke.control.default,
            0.97f to fillAccent.default
        )
    )

private fun generateSystemColors(darkMode: Boolean): SystemColors {
    return if (darkMode) {
        SystemColors(
            attention = Color(0xFF60CDFF),
            attentionBackground = Color(0x08FFFFFF),
            solidAttentionBackground = Color(0xFF2E2E2E),
            success = Color(0xFF6CCB5F),
            successBackground = Color(0xFF393D1B),
            caution = Color(0xFFFCE100),
            cautionBackground = Color(0xFF433519),
            critical = Color(0xFFFF99A4),
            criticalBackground = Color(0xFF442726),
            neutral = Color(0x8BFFFFFF),
            neutralBackground = Color(0x08FFFFFF),
            solidNeutral = Color(0xFF9D9D9D),
            solidNeutralBackground = Color(0xFF2E2E2E),
        )
    } else {
        SystemColors(
            attention = Color(0xFF0070CB),
            attentionBackground = Color(0x80F6F6F6),
            solidAttentionBackground = Color(0xFFF7F7F7),
            success = Color(0xFF0F7B0F),
            successBackground = Color(0xFFDFF6DD),
            caution = Color(0xFF9D5D00),
            cautionBackground = Color(0xFFFFF4CE),
            critical = Color(0xFFC42B1C),
            criticalBackground = Color(0xFFFDE7E9),
            neutral = Color(0xFF72000000),
            neutralBackground = Color(0x06000000),
            solidNeutral = Color(0xFF8A8A8A),
            solidNeutralBackground = Color(0xFFF3F3F3),
        )
    }
}