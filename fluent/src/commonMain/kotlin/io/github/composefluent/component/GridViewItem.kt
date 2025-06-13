package io.github.composefluent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.background.LayerShapeHelper
import io.github.composefluent.background.calculateBorderPadding
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.VisualStateScheme
import io.github.composefluent.scheme.collectVisualState
import kotlin.jvm.JvmInline

/**
 * A composable that represents an item in a grid view with multi-selection capabilities.
 *
 * This item can be selected or deselected, and it displays a checkbox overlay to indicate its selection state.
 *
 * @param selected Whether this item is currently selected.
 * @param onSelectedChange A callback that is invoked when the selection state of the item changes.
 *  It receives the new selection state (`true` for selected, `false` for deselected).
 * @param modifier The modifier to be applied to the item.
 * @param enabled Controls the enabled state of the item. When `false`, the item is not selectable and visually appears disabled.
 * @param colors The [VisualStateScheme] that defines the colors for the grid view item in different interaction states (default, hovered, pressed, disabled).
 *  Defaults to [GridViewItemDefaults.selectedColors] if selected, otherwise [GridViewItemDefaults.defaultColors].
 * @param checkBoxColorScheme The [VisualStateScheme] that defines the colors for the checkbox overlay in different interaction states.
 *  Defaults to [GridViewItemDefaults.selectedCheckBoxColors] if selected, otherwise [GridViewItemDefaults.defaultCheckBoxColors].
 * @param interactionSource The [MutableInteractionSource] representing the stream of [androidx.compose.foundation.interaction.Interaction]s
 *  for this item. You can create and pass in your own remembered [MutableInteractionSource] if you want to observe
 *  [androidx.compose.foundation.interaction.Interaction]s and customize the item's appearance in different [androidx.compose.foundation.interaction.Interaction]s.
 * @param content The content to be displayed within the grid view item.
 */
@Composable
fun MultiSelectGridViewItem(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: VisualStateScheme<GridViewItemColor> = if (selected) {
        GridViewItemDefaults.selectedColors()
    } else {
        GridViewItemDefaults.defaultColors()
    },
    checkBoxColorScheme: VisualStateScheme<CheckBoxColor> = if (selected) {
        GridViewItemDefaults.selectedCheckBoxColors()
    } else {
        GridViewItemDefaults.defaultCheckBoxColors()
    },
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    GridViewItem(
        selected = selected,
        onSelectedChange = onSelectedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        content = { content() },
        overlay = {
            CheckBox(
                checked = selected,
                onCheckStateChange = onSelectedChange,
                colors = checkBoxColorScheme,
                enabled = enabled,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }
    )
}

/**
 * A composable that represents an item in a grid view.
 *
 * This composable provides a selectable item that can be used in a grid layout.
 * It supports different states (default, hovered, pressed, disabled) and can be customized
 * with different colors and interaction behaviors.
 *
 * @param selected `true` if the item is currently selected, `false` otherwise.
 * @param onSelectedChange Callback function that is invoked when the selected state of the item changes.
 *   It receives the new selected state as a boolean parameter.
 * @param modifier Modifier to apply to this item.
 * @param enabled `true` if the item is enabled and interactive, `false` otherwise.
 * @param interactionSource Optional [MutableInteractionSource] that will be used to dispatch
 *   [Interaction]s to indicate the item's state. If not provided, a [remembered][remember]
 *   instance will be used internally.
 * @param colors [VisualStateScheme] that provides the colors for different states of the item.
 *   Defaults to [GridViewItemDefaults.selectedColors] when `selected` is true and
 *   [GridViewItemDefaults.defaultColors] otherwise.
 * @param content The content to be displayed inside the grid view item.
 */
@Composable
fun GridViewItem(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    colors: VisualStateScheme<GridViewItemColor> = if (selected) {
        GridViewItemDefaults.selectedColors()
    } else {
        GridViewItemDefaults.defaultColors()
    },
    content: @Composable () -> Unit
) {
    GridViewItem(
        selected = selected,
        onSelectedChange = onSelectedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        colors = colors,
        content = { content() },
        overlay = null
    )
}

/**
 * Represents the colors of a grid view item.
 *
 * @property borderColor The color of the border around the item.
 * @property backgroundColor The background color of the item.
 */
@Stable
data class GridViewItemColor(
    val borderColor: Color,
    val backgroundColor: Color
)

typealias GridViewItemColorScheme = PentaVisualScheme<GridViewItemColor>

/**
 * Contains the default values used for [GridViewItem].
 */
object GridViewItemDefaults {

    /**
     * The default spacing used for padding within the `GridViewItem` container.
     * This spacing is applied around the content and between the content and the border,
     * effectively creating a visual margin.
     */
    val spacing = 4.dp

    /**
     * Creates a [GridViewItemColorScheme] with the default colors for a [GridViewItem].
     *
     * @param default The default colors for the GridViewItem.
     *      * `borderColor`: The color of the border when the item is in its default state. Defaults to transparent.
     *      * `backgroundColor`: The background color when the item is in its default state. Defaults to `subtleFill.transparent` from [FluentTheme.colors].
     * @param hovered The colors for the GridViewItem when hovered.
     *      * `borderColor`: The border color when the item is hovered. Defaults to `stroke.control.onAccentTertiary` from [FluentTheme.colors].
     *      * `backgroundColor`: The background color when the item is hovered. Defaults to `subtleFill.secondary` from [FluentTheme.colors].
     * @param pressed The colors for the GridViewItem when pressed.
     *      * `borderColor`: The border color when the item is pressed. Defaults to transparent.
     *      * `backgroundColor`: The background color when the item is pressed. Defaults to `subtleFill.tertiary` from [FluentTheme.colors].
     * @param disabled The colors for the GridViewItem when disabled. Defaults to the same as `default`.
     * @return A [GridViewItemColorScheme] containing the specified colors.
     */
    @Stable
    @Composable
    fun defaultColors(
        default: GridViewItemColor = GridViewItemColor(
            borderColor = Color.Transparent,
            backgroundColor = FluentTheme.colors.subtleFill.transparent
        ),
        hovered: GridViewItemColor = GridViewItemColor(
            borderColor = FluentTheme.colors.stroke.control.onAccentTertiary,
            backgroundColor = FluentTheme.colors.subtleFill.secondary
        ),
        pressed: GridViewItemColor = GridViewItemColor(
            borderColor = Color.Transparent,
            backgroundColor = FluentTheme.colors.subtleFill.tertiary
        ),
        disabled: GridViewItemColor = default,
    ): GridViewItemColorScheme = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    /**
     * Creates a [GridViewItemColorScheme] with colors for a selected [GridViewItem] in different states.
     *
     * @param default The colors used when the [GridViewItem] is in its default selected state.
     * By default, it uses a [FluentTheme.colors.fillAccent.default] border and a [FluentTheme.colors.subtleFill.tertiary] background.
     * @param hovered The colors used when the [GridViewItem] is selected and hovered over.
     * By default, it uses a [FluentTheme.colors.fillAccent.secondary] border and a [FluentTheme.colors.subtleFill.secondary] background.
     * @param pressed The colors used when the [GridViewItem] is selected and pressed.
     * By default, it uses a [FluentTheme.colors.fillAccent.tertiary] border and a [FluentTheme.colors.subtleFill.tertiary] background.
     * @param disabled The colors used when the [GridViewItem] is selected and disabled.
     * By default, it uses a [FluentTheme.colors.fillAccent.disabled] border and a [FluentTheme.colors.subtleFill.secondary] background.
     * @return A [GridViewItemColorScheme] that defines the colors for a selected [GridViewItem] in various states.
     */
    @Stable
    @Composable
    fun selectedColors(
        default: GridViewItemColor = GridViewItemColor(
            borderColor = FluentTheme.colors.fillAccent.default,
            backgroundColor = FluentTheme.colors.subtleFill.tertiary
        ),
        hovered: GridViewItemColor = GridViewItemColor(
            borderColor = FluentTheme.colors.fillAccent.secondary,
            backgroundColor = FluentTheme.colors.subtleFill.secondary
        ),
        pressed: GridViewItemColor = GridViewItemColor(
            borderColor = FluentTheme.colors.fillAccent.tertiary,
            backgroundColor = FluentTheme.colors.subtleFill.tertiary
        ),
        disabled: GridViewItemColor = GridViewItemColor(
            borderColor = FluentTheme.colors.fillAccent.disabled,
            backgroundColor = FluentTheme.colors.subtleFill.secondary
        ),
    ): GridViewItemColorScheme = PentaVisualScheme(
        default = default,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    //TODO ColorOnImage
    @Stable
    @Composable
    fun defaultCheckBoxColors() = CheckBoxDefaults.defaultCheckBoxColors()

    //TODO ColorOnImage
    @Stable
    @Composable
    fun selectedCheckBoxColors() = CheckBoxDefaults.selectedCheckBoxColors()
}

@Composable
private fun GridViewItem(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    colors: VisualStateScheme<GridViewItemColor>,
    interactionSource: MutableInteractionSource?,
    overlay: (@Composable BoxScope.() -> Unit)?,
    content: @Composable BoxScope.() -> Unit
) {
    val itemShape = FluentTheme.shapes.control
    val targetInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val currentColor = colors.schemeFor(targetInteractionSource.collectVisualState(!enabled))
    Box(
        propagateMinConstraints = true,
        modifier = modifier
            .clip(itemShape)
            .selectable(
                enabled = enabled,
                selected = selected,
                onClick = { onSelectedChange(!selected) },
                indication = null,
                interactionSource = targetInteractionSource
            )
            .border(SelectedItemBorderSize, currentColor.borderColor, shape = itemShape)
    ) {
        Box(
            propagateMinConstraints = true,
            modifier = if (selected) {
                val innerShape = remember(itemShape) {
                    if (itemShape is CornerBasedShape) {
                        GridViewItemInnerShape(itemShape)
                    } else {
                        itemShape
                    }
                }
                Modifier
                    .clip(innerShape)
                    .background(
                        color = currentColor.backgroundColor,
                        shape = innerShape
                    )
            } else {
                Modifier.background(
                    color = currentColor.backgroundColor,
                    shape = itemShape
                )
            }
        ) {
            content()
        }
        if (overlay != null) {
            Box(
                content = overlay,
                modifier = Modifier
                    .matchParentSize()
            )
        }

    }
}

@Stable
@Immutable
@JvmInline
private value class GridViewItemInnerShape(private val itemShape: CornerBasedShape): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return LayerShapeHelper.createInnerOutline(
            outsideShape = itemShape,
            size = size,
            density = density,
            layoutDirection = layoutDirection,
            paddingPx = itemShape.calculateBorderPadding(density, InnerPaddingSize)
        )
    }
}

private val SelectedItemBorderSize = 2.dp
private val InnerPaddingSize = 3.dp