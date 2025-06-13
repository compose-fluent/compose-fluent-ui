package io.github.composefluent.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.scheme.VisualStateScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A composable function that creates a text box button.
 *
 * @param modifier The modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable.
 * @param onClick The callback to be invoked when this button is clicked. If `null`, the button will not be clickable.
 * @param colors The colors to use for the button in different visual states. Defaults to [ButtonDefaults.subtleButtonColors].
 * @param outsideBorder Determines whether to display the border outside the button. Defaults to `false`.
 * @param interactionSource The [MutableInteractionSource] representing the stream of Interactions for this button.
 * @param focusable Whether the button should be focusable.
 * @param content The content of the button. This is a composable function that takes a [RowScope] as its receiver.
 */
@Composable
fun TextBoxButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    colors: VisualStateScheme<ButtonColor> = ButtonDefaults.subtleButtonColors(),
    outsideBorder: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    focusable: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val interaction = interactionSource ?: remember { MutableInteractionSource() }
    ButtonLayer(
        shape = FluentTheme.shapes.control,
        displayBorder = true,
        buttonColors = colors,
        interaction = interaction,
        disabled = !enabled,
        accentButton = !outsideBorder,
        modifier = modifier.defaultMinSize(minWidth = 28.dp, minHeight = 24.dp)
    ) {
        Row(
            Modifier
                .then(
                    if (onClick != null) {
                        Modifier.focusProperties { canFocus = focusable }
                            .clickable(
                                onClick = onClick,
                                interactionSource = interaction,
                                indication = null,
                                enabled = enabled
                            ).pointerHoverIcon(PointerIcon.Default, !enabled)

                    } else {
                        Modifier
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}

/**
 * A toggle button that displays text and can be toggled on or off.
 *
 * @param checked Whether the button is currently checked.
 * @param onCheckedChanged Callback that is triggered when the checked state changes.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable.
 * @param colors The colors to use for this button in different states.
 * @param outsideBorder Whether the button should have an outside border when checked.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this button.
 * @param focusable Whether this button should be focusable.
 * @param content The content of the button, typically text.
 */
@Composable
fun ToggleTextButton(
    checked: Boolean,
    onCheckedChanged: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: VisualStateScheme<ButtonColor> = if (checked) {
        ButtonDefaults.accentButtonColors()
    } else {
        ButtonDefaults.subtleButtonColors()
    },
    outsideBorder: Boolean = checked,
    interactionSource: MutableInteractionSource? = null,
    focusable: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val interaction = interactionSource ?: remember { MutableInteractionSource() }
    TextBoxButton(
        enabled = enabled,
        onClick = null,
        colors = colors,
        outsideBorder = outsideBorder,
        interactionSource = interaction,
        focusable = focusable,
        content = content,
        modifier = modifier
            .focusProperties { canFocus = focusable }
            .pointerHoverIcon(PointerIcon.Default, !enabled)
            .selectable(
                selected = checked,
                onClick = { onCheckedChanged(!checked) },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interaction,
                indication = null
            )
    )
}

/**
 * A button that repeats the [onClick] action when long-pressed.
 *
 * This button triggers the [onClick] action immediately when pressed, then again after a [delay],
 * and then repeatedly at [interval] intervals as long as the button remains pressed. It also
 * triggers the [onClick] twice when double-clicked.
 *
 * @param onClick The action to perform when the button is clicked, long-pressed, or double-clicked.
 * @param modifier The modifier to apply to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be clickable.
 * @param delay The initial delay (in milliseconds) before the repeat action starts after a long press.
 * @param interval The interval (in milliseconds) between repeated actions after the initial [delay].
 * @param colors The color scheme for this button, defined by [VisualStateScheme].
 * @param outsideBorder Whether to display an outside border on the button.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s for this button.
 * @param focusable Whether the button is focusable.
 * @param content The content of the button.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepeatTextBoxButton(
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    delay: Long = 200,
    interval: Long = 50,
    colors: VisualStateScheme<ButtonColor> = ButtonDefaults.subtleButtonColors(),
    outsideBorder: Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    focusable: Boolean = false,
    content: @Composable RowScope.() -> Unit
) {
    val interaction = interactionSource ?: remember { MutableInteractionSource() }
    val pressed = interaction.collectIsPressedAsState()
    val scope = rememberCoroutineScope()

    TextBoxButton(
        modifier = modifier
            .focusProperties { canFocus = focusable }
            .pointerHoverIcon(PointerIcon.Default, !enabled)
            .combinedClickable(
                interactionSource = interaction,
                indication = null,
                enabled = enabled,
                onClick = onClick,
                onLongClick = {
                    onClick()
                    scope.launch {
                        delay(delay)
                        do {
                            onClick()
                            delay(interval)
                        } while (pressed.value)
                    }
                },
                onDoubleClick = {
                    onClick()
                    onClick()
                }
            ),
        interactionSource = interaction,
        enabled = enabled,
        colors = colors,
        outsideBorder = outsideBorder,
        onClick = null,
        content = content
    )
}

/**
 * Contains the default values used for [TextBoxButton], [ToggleTextButton] and [RepeatTextBoxButton].
 */
object TextBoxButtonDefaults {

    /**
     * A composable function that displays a search icon.
     *
     * @param size The size of the icon. Defaults to [FontIconSize.Small].
     * @param modifier The modifier to be applied to the icon.
     */
    @Composable
    fun SearchIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.Search,
            contentDescription = "Search",
            size = size,
            modifier = modifier
        )
    }

    /**
     * A composable function that displays a clear icon.
     *
     * @param size The size of the icon. Defaults to [FontIconSize.Small].
     * @param modifier The modifier to be applied to the icon.
     */
    @Composable
    fun ClearIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.Close,
            contentDescription = "Clear",
            size = size,
            modifier = modifier
        )
    }

    /**
     * Displays an icon representing the "reveal password" action.
     *
     * @param size The size of the icon, defaults to [FontIconSize.Small].
     * @param modifier Modifier to apply to the icon.
     */
    @Composable
    fun RevealPasswordIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.RevealPassword,
            contentDescription = "Reveal Password",
            size = size,
            modifier = modifier
        )
    }

    /**
     * Creates an icon that displays an arrow pointing to the right.
     *
     * @param size The size of the icon. Defaults to [FontIconSize.Small].
     * @param modifier Modifiers to be applied to the icon.
     */
    @Composable
    fun ArrowRightIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.ArrowRight,
            contentDescription = "Arrow Right",
            size = size,
            modifier = modifier
        )
    }

    /**
     * A composable function that displays a Chevron Up icon.
     *
     * @param size The size of the icon, defaults to [FontIconSize.Small].
     * @param modifier The modifier to apply to the icon.
     */
    @Composable
    fun ChevronUpIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.ChevronUp,
            contentDescription = "Chevron Up",
            size = size,
            modifier = modifier
        )
    }

    /**
     * A composable function that displays a chevron down icon.
     *
     * @param size The size of the icon, defaults to [FontIconSize.Small].
     * @param modifier The modifier to be applied to the icon.
     */
    @Composable
    fun ChevronDownIcon(
        size: FontIconSize = FontIconSize.Small,
        modifier: Modifier = Modifier,
    ) {
        FontIcon(
            type = FontIconPrimitive.ChevronDown,
            contentDescription = "Chevron Down",
            size = size,
            modifier = modifier
        )
    }
}