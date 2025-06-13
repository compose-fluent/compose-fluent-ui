package io.github.composefluent.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.background.Layer
import io.github.composefluent.layout.HorizontalIndicatorContentLayout
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * A composable function that represents a selector bar.
 *
 * The SelectorBar is a horizontal row that can be used to display and manage a list of selectable items.
 * It is typically used as a navigation element or to filter content.
 *
 * @param modifier The [Modifier] to be applied to this selector bar.
 * @param content The content lambda to render within the selector bar.
 */
@Composable
inline fun SelectorBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

/**
 * A single item within a [SelectorBar].
 *
 * @param selected Whether this item is currently selected.
 * @param onSelectedChange The callback to be invoked when this item's selection state is changed.
 * @param text The text content of this item. If null, only the [icon] will be displayed.
 * @param modifier Modifier to be applied to the item.
 * @param icon The icon content of this item. If null, only the [text] will be displayed.
 * @param colors [VisualStateScheme] that provides the colors used for this item.
 * @param enabled Whether this item is enabled.
 * @param indicator The composable that indicates whether this item is selected.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this item.
 */
@Composable
fun SelectorBarItem(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    text: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    colors: VisualStateScheme<SelectorBarItemColor> = if (selected) {
        SelectorBarDefaults.selectedItemColors()
    } else {
        SelectorBarDefaults.defaultItemColors()
    },
    enabled: Boolean = true,
    indicator: @Composable (color: Color) -> Unit = { HorizontalIndicator(color = it, visible = selected) },
    interactionSource: MutableInteractionSource? = null,
) {
    val iconOnly = icon != null && text == null

    val targetInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val currentColor = colors.schemeFor(targetInteractionSource.collectVisualState(!enabled))

    Layer(
        color = currentColor.fillColor,
        contentColor = currentColor.contentColor,
        border = null,
        modifier = modifier.widthIn(if (iconOnly) 40.dp else 49.dp)
            .heightIn(40.dp)
            .selectable(
                selected = selected,
                enabled = enabled,
                interactionSource = targetInteractionSource,
                indication = null,
                onClick = { onSelectedChange(!selected) }
            )
    ) {
        HorizontalIndicatorContentLayout(
            modifier = Modifier.height(40.dp),
            text = text,
            icon = icon,
            trailing = null,
            indicator = { indicator(currentColor.indicatorColor) }
        )
    }
}

/**
 * Contains the default values used for [SelectorBarItem].
 */
object SelectorBarDefaults {

    /**
     * Creates a [SelectorBarItemColorScheme] with the default colors for a [SelectorBarItem].
     *
     * @param default The color scheme for the default state.
     * @param hovered The color scheme for the hovered state.
     * @param pressed The color scheme for the pressed state.
     * @param disabled The color scheme for the disabled state.
     * @return A [SelectorBarItemColorScheme] with the specified colors.
     */
    @Composable
    @Stable
    fun defaultItemColors(
        default: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.primary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        hovered: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.secondary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        pressed: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.tertiary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        disabled: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.disabled,
            indicatorColor = FluentTheme.colors.fillAccent.disabled
        )
    ) = SelectorBarItemColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
    
    /**
     * Creates a [SelectorBarItemColorScheme] with specified colors for different states of a selected item.
     *
     * @param default The color scheme for the default state.
     * @param hovered The color scheme for the hovered state.
     * @param pressed The color scheme for the pressed state.
     * @param disabled The color scheme for the disabled state.
     * @return A [SelectorBarItemColorScheme] with the specified colors.
     */
    @Composable
    @Stable
    fun selectedItemColors(
        default: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.primary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        hovered: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.secondary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        pressed: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.tertiary,
            indicatorColor = FluentTheme.colors.fillAccent.default
        ),
        disabled: SelectorBarItemColor = SelectorBarItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.disabled,
            indicatorColor = FluentTheme.colors.fillAccent.disabled
        )
    ) = SelectorBarItemColorScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )
}

/**
 * Represents the color scheme for a single item in a [SelectorBar].
 *
 * @property fillColor The background fill color of the item.
 * @property contentColor The color of the text and icons within the item.
 * @property indicatorColor The color of the indicator (e.g., a horizontal line) displayed when the item is selected.
 */
@Immutable
data class SelectorBarItemColor(
    val fillColor: Color,
    val contentColor: Color,
    val indicatorColor: Color,
)

typealias SelectorBarItemColorScheme = PentaVisualScheme<SelectorBarItemColor>