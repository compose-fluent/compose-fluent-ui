package io.github.composefluent.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.collectVisualState

/**
 * Use a combo box (also known as a drop-down list) to present a list of items that a user can select from. A combo box starts in a compact state and expands to show a list of selectable items.
 */
/*
@Composable
fun <T> ComboBox(
    header: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    open: Boolean,
    editable: Boolean,
    items: List<T>,
    selected: T?,
    onSelectionChange: (T) -> Unit,
    contentScope: ComboBoxScope<T>.() -> Unit
) {
}
*/

/**
 * Use a combo box (also known as a drop-down list) to present a list of items that a user can select from.
 * A combo box starts in a compact state, displaying either a selected item or a placeholder, and expands to show a list of selectable items when interacted with.
 *
 * @param modifier Modifier to apply to the component.
 * @param header Optional header text to display above the combo box.
 * @param placeholder Optional placeholder text to display when no item is selected.
 * @param disabled Whether the combo box is disabled.
 * @param items The list of items to display in the combo box.
 * @param selected The index of the currently selected item, or null if no item is selected.
 * @param onSelectionChange Callback function triggered when a new item is selected. Provides the index and the item's string value.
 *
 * TODO: Editable ComboBox
 */
@Composable
fun ComboBox(
    modifier: Modifier = Modifier,
    header: String? = null,
    placeholder: String? = null,
    disabled: Boolean = false,
    items: List<String>,
    selected: Int?,
    onSelectionChange: (index: Int, item: String) -> Unit
) {
    var open by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf<IntSize>(IntSize(0, 0)) }
    Column(modifier) {
        if (header != null) {
            Text(text = header)
            Spacer(Modifier.height(8.dp))
        }
        DropDownButton(
            modifier = Modifier.defaultMinSize(128.dp).onSizeChanged { size = it },
            onClick = { open = true },
            disabled = disabled,
            contentArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val text = if (selected != null) items[selected]
                else placeholder ?: ""

                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = text,
                    color = if (selected != null) FluentTheme.colors.text.text.primary
                    else FluentTheme.colors.text.text.secondary
                )
            }
        }
        // TODO: Use new flyout popup
        // TODO: Set transform center to currently selected item
        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) { size.width.toDp() + 4.dp }),
            expanded = open,
            onDismissRequest = { open = false },
            offset = DpOffset(x = 0.dp, y = with(LocalDensity.current) { -(size.height.toDp() + 6.dp) })
        ) {
            items.fastForEachIndexed { i, s ->
                ComboBoxItem(selected = i == selected, label = s, onClick = {
                    onSelectionChange(i, s)
                    open = false
                })
            }
        }
    }
}

/**
 * Represents the colors used for an item in a ComboBox.
 *
 * @property fillColor The background color of the item.
 * @property contentColor The color of the text or other content within the item.
 */
data class ItemColor(
    val fillColor: Color,
    val contentColor: Color
)


private val unselectedItemColors: PentaVisualScheme<ItemColor>
    @Composable
    get() = PentaVisualScheme<ItemColor>(
        default = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        hovered = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.secondary,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        pressed = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        disabled = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.disabled
        )
    )

private val selectedItemColors: PentaVisualScheme<ItemColor>
    @Composable
    get() = PentaVisualScheme<ItemColor>(
        default = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.secondary,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        hovered = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.tertiary,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        pressed = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.secondary,
            contentColor = FluentTheme.colors.text.text.primary
        ),
        disabled = ItemColor(
            fillColor = FluentTheme.colors.subtleFill.transparent,
            contentColor = FluentTheme.colors.text.text.disabled
        )
    )


/**
 * Composable function that represents a single item within a ComboBox dropdown.
 *
 * @param selected Indicates whether the item is currently selected.
 * @param label The text label to display for the item.
 * @param onClick Callback function invoked when the item is clicked.
 */
@Composable
fun ComboBoxItem(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val colors = if (selected) selectedItemColors else unselectedItemColors
    val color = colors.schemeFor(interactionSource.collectVisualState(false))

    Layer(
        modifier = Modifier.fillMaxWidth().height(36.dp)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        shape = FluentTheme.shapes.control,
        color = animateColorAsState(
            color.fillColor,
            tween(FluentDuration.QuickDuration, easing = FluentEasing.FastInvokeEasing)
        ).value,
        contentColor = color.contentColor,
        border = null,
        backgroundSizing = BackgroundSizing.OuterBorderEdge
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            val pressed by interactionSource.collectIsPressedAsState()
            val height by animateDpAsState(if (pressed) 12.dp else 16.dp)
            // Indicator
            if (selected) Box(
                Modifier.size(height = height, width = 3.dp)
                    .align(Alignment.CenterStart)
                    .background(FluentTheme.colors.fillAccent.default, CircleShape)
            )
            Text(modifier = Modifier.padding(horizontal = 12.dp), text = label)
        }
    }
}

/**
 * Scope for the content of a ComboBox.
 *
 * This scope provides functions to define the items within a [ComboBox].
 */
interface ComboBoxScope<T> {
    /**
     * Adds an item to the ComboBox.
     *
     * @param key A unique key to identify this item. This is not currently used, but may be used in the future for item management.
     * @param content The composable content to display for this item. It receives the item as a parameter.
     */
    fun Item(key: String, content: @Composable (item: T) -> Unit)
    /**
     * A [ComboBoxScope] item representing a string.
     *
     * @param label The string to display for this item.
     */
    fun StringItem(label: String)
}