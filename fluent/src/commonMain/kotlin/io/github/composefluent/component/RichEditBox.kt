package io.github.composefluent.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.layout.overflow.OverflowRowScope
import io.github.composefluent.scheme.collectVisualState
import kotlin.math.roundToInt
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

/**
 * A multiline Fluent rich text editor with a contextual bold, italic, and underline toolbar.
 *
 * Text and formatting changes are undoable through [state]. Standard clipboard actions are
 * available as plain text; this component does not implement formatted clipboard or RTF conversion.
 * Ctrl/Cmd+B, I, U, Z, Shift+Z and Ctrl+Y operate on the editor's formatting and history.
 *
 * [showFormattingToolbar] controls the floating toolbar shown for a nonempty selection. A
 * persistent [RichEditBoxToolbar] can also be composed separately, including for cursor formatting.
 * [readOnly] allows selection and copying but disables editing commands.
 * [primary] appends commands after Underline. [secondary] appends menu items after Select All
 * and receives a callback to dismiss the menu after a command runs.
 */
@Composable
fun RichEditBox(
    state: RichEditBoxState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    header: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    showFormattingToolbar: Boolean = true,
    lineLimits: TextFieldLineLimits.MultiLine = TextFieldLineLimits.MultiLine(minHeightInLines = 5),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColorScheme = TextFieldDefaults.defaultTextFieldColors(),
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = FluentTheme.shapes.control,
    primary: OverflowRowScope.(expanded: Boolean) -> Unit = {},
    secondary: @Composable MenuFlyoutScope.(dismiss: () -> Unit) -> Unit = {}
) {
    val focused by interactionSource.collectIsFocusedAsState()
    val color = colors.schemeFor(interactionSource.collectVisualState(!enabled, focusFirst = true))
    var layout by remember { mutableStateOf<TextLayoutResult?>(null) }
    var viewport by remember { mutableStateOf(Rect.Zero) }
    var textOrigin by remember { mutableStateOf(Offset.Zero) }
    val selection = state.selection
    var dismissed by remember(state) { mutableStateOf(false) }
    LaunchedEffect(selection, focused) { dismissed = false }
    val editable = enabled && !readOnly
    var pointerDown by remember { mutableStateOf(false) }
    var touchInput by remember { mutableStateOf(false) }
    LaunchedEffect(state) {
        snapshotFlow { state.selection }.collect { state.selectionChanged() }
    }

    val focusRequester = remember { FocusRequester() }
    var fieldCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    var contextAnchor by remember(state, enabled) { mutableStateOf<Rect?>(null) }
    LaunchedEffect(focused) { if (!focused) contextAnchor = null }

    Box(modifier = modifier, propagateMinConstraints = true) {
        RichEditFieldContext {
            BasicTextField(
                state = state.textFieldState,
                modifier = Modifier.defaultMinSize(minWidth = 160.dp)
                    .focusRequester(focusRequester)
                    .onGloballyPositioned { fieldCoordinates = it }
                    .pointerInput(state, enabled) {
                        var secondaryGesture = false
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent(PointerEventPass.Initial)
                                if (enabled && event.type == PointerEventType.Press) {
                                    secondaryGesture = event.buttons.isSecondaryPressed
                                    if (secondaryGesture) {
                                        focusRequester.requestFocus()
                                        val point = fieldCoordinates?.localToWindow(event.changes.first().position)
                                        if (point != null && viewport.contains(point)) {
                                            val offset = layout?.getOffsetForPosition(
                                                point - textOrigin + Offset(0f, scrollState.value.toFloat())
                                            )?.coerceIn(0, state.text.length)
                                            val range = state.selection
                                            if (offset != null && (range.collapsed || offset !in range.min until range.max)) {
                                                state.selection = TextRange(offset)
                                            }
                                        }
                                        contextAnchor = point?.let { Rect(it, it) }
                                        dismissed = false
                                    } else contextAnchor = null
                                }
                                if (secondaryGesture) {
                                    event.changes.forEach { it.consume() }
                                    if (event.type == PointerEventType.Release) secondaryGesture = false
                                }
                                pointerDown = event.changes.any { it.pressed }
                                if (pointerDown) touchInput = event.changes.any { it.type != PointerType.Mouse }
                            }
                        }
                    }
                    .onPreviewKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown && event.key == Key.Escape &&
                            (contextAnchor != null || (!selection.collapsed && showFormattingToolbar && !dismissed))) {
                            contextAnchor = null
                            dismissed = true
                            true
                        } else {
                            state.handleKeyEvent(event, editable)
                        }
                    }
                    .semantics {
                        if (editable) customActions = buildList {
                            add(CustomAccessibilityAction("Bold") { state.toggleBold(); true })
                            add(CustomAccessibilityAction("Italic") { state.toggleItalic(); true })
                            add(CustomAccessibilityAction("Underline") { state.toggleUnderline(); true })
                            if (state.canUndo) add(CustomAccessibilityAction("Undo") { state.undo(); true })
                            if (state.canRedo) add(CustomAccessibilityAction("Redo") { state.redo(); true })
                        }
                    },
                enabled = enabled,
                readOnly = readOnly,
                textStyle = LocalTextStyle.current.copy(color = color.contentColor),
                cursorBrush = color.cursorBrush,
                interactionSource = interactionSource,
                inputTransformation = state.inputTransformation,
                lineLimits = lineLimits,
                scrollState = scrollState,
                keyboardOptions = keyboardOptions,
                onKeyboardAction = onKeyboardAction,
                onTextLayout = { getResult -> layout = getResult() },
                decorator = decorationBoxWorkaround { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        state = state.textFieldState,
                        isClearable = false,
                        outputTransformation = null,
                        interactionSource = interactionSource,
                        enabled = enabled,
                        color = color,
                        shape = shape,
                        header = header,
                        placeholder = placeholder,
                        leadingIcon = null,
                        trailing = null,
                        innerTextField = {
                            Box(Modifier.onGloballyPositioned {
                                viewport = it.boundsInWindow()
                                textOrigin = it.localToWindow(Offset.Zero)
                            }) { innerTextField() }
                        }
                    )
                }
            )
        }

        val anchor = when {
            !focused || dismissed || pointerDown -> null
            contextAnchor != null -> contextAnchor
            !editable || !showFormattingToolbar || selection.collapsed -> null
            else -> {
                val currentLayout = layout
                val scroll = scrollState.value
                val length = state.text.length
                remember(currentLayout, selection, textOrigin, viewport, scroll, length) {
                    currentLayout?.takeIf { it.layoutInput.text.length == length }
                        ?.getPathForRange(selection.min, selection.max)?.getBounds()
                        ?.translate(textOrigin - Offset(0f, scroll.toFloat()))
                        ?.intersect(viewport)
                        ?.takeUnless { it.isEmpty }
                }
            }
        }
        if (anchor != null) {
            val density = LocalDensity.current
            val positionProvider = remember(density, anchor, touchInput) {
                // Leave the area above a touch selection available for the platform clipboard toolbar.
                RichEditBoxPositionProvider(density, anchor, preferBelow = touchInput)
            }
            var expanded by remember(contextAnchor) { mutableStateOf(contextAnchor != null) }
            val dismiss = { contextAnchor = null; dismissed = true }
            BasicCommandBarFlyout(
                visible = true,
                onDismissRequest = dismiss,
                expanded = expanded,
                onExpandedChanged = { expanded = it },
                modifier = Modifier,
                isLarge = expanded,
                focusable = false,
                positionProvider = positionProvider,
                secondary = {
                    RichEditCommands(state, editable, true, dismiss)
                    secondary(dismiss)
                }
            ) {
                richEditFormatItems(state, expanded, editable, true)
                primary(expanded)
            }
        }
    }
}

/**
 * Formatting commands and an expandable editing menu for a [RichEditBoxState].
 * [retainEditorFocus] prevents pointer clicks from taking focus away from the text input.
 * Leave it false for a standalone toolbar that should participate in keyboard traversal.
 * [primary] appends commands after Underline. [secondary] appends menu items after Select All
 * and receives a callback to dismiss the menu after a command runs.
 */
@Composable
fun RichEditBoxToolbar(
    state: RichEditBoxState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    retainEditorFocus: Boolean = false,
    primary: OverflowRowScope.(expanded: Boolean) -> Unit = {},
    secondary: @Composable MenuFlyoutScope.(dismiss: () -> Unit) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val dismiss = { expanded = false }
    CommandBar(
        expanded = expanded,
        onExpandedChanged = { expanded = it },
        modifier = if (retainEditorFocus) modifier.focusProperties { canFocus = false } else modifier,
        secondary = {
            RichEditCommands(state, enabled, retainEditorFocus, dismiss)
            secondary(dismiss)
        }
    ) {
        richEditFormatItems(state, false, enabled, retainEditorFocus)
        primary(expanded)
    }
}

private fun OverflowRowScope.richEditFormatItems(
    state: RichEditBoxState,
    large: Boolean,
    enabled: Boolean,
    retainEditorFocus: Boolean
) {
    fun format(label: String, checked: () -> Boolean, toggle: () -> Unit, icon: FontIconPrimitive) {
        item(key = label) {
            val modifier = Modifier
                .size(if (large) 60.dp else 32.dp, if (large) 52.dp else 32.dp)
                .focusProperties { canFocus = !retainEditorFocus }
            RichEditBoxFormatButton(label, checked(), toggle, modifier, enabled) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    FontIcon(icon, contentDescription = null)
                    if (large) Text(label, style = FluentTheme.typography.caption)
                }
            }
        }
    }
    format("Bold", { state.isBold }, state::toggleBold, FontIconPrimitive.Bold)
    format("Italic", { state.isItalic }, state::toggleItalic, FontIconPrimitive.Italic)
    format("Underline", { state.isUnderline }, state::toggleUnderline, FontIconPrimitive.Underline)
}

@Composable
private fun MenuFlyoutScope.RichEditCommands(
    state: RichEditBoxState,
    enabled: Boolean,
    retainEditorFocus: Boolean,
    onCommand: () -> Unit
) {
    val pasteScope = key(state) { rememberCoroutineScope() }
    val editable by rememberUpdatedState(enabled)
    val clipboard = LocalClipboardManager.current
    val paste = if (enabled) rememberPlainTextPaste() else null
    val selection = state.selection
    val selected = !selection.collapsed
    fun replaceSelection(value: String) {
        state.edit {
            val start = selection.min
            replace(start, selection.max, value)
            this.selection = TextRange(start + value.length)
        }
    }
    fun command(action: () -> Unit) {
        action()
        onCommand()
    }
    val item = Modifier.fillMaxWidth().focusProperties { canFocus = !retainEditorFocus }
    if (selected) {
        RichEditCommand("Cut", "X", item, enabled, FontIconPrimitive.Cut) {
            command {
                clipboard.setText(AnnotatedString(state.text.substring(selection.min, selection.max)))
                replaceSelection("")
            }
        }
        RichEditCommand("Copy", "C", item, true, FontIconPrimitive.Copy) {
            command { clipboard.setText(AnnotatedString(state.text.substring(selection.min, selection.max))) }
        }
    }
    RichEditCommand("Paste", "V", item, enabled && paste != null, FontIconPrimitive.Paste) {
        if (paste != null) pasteScope.launch(start = CoroutineStart.UNDISPATCHED) {
            if (state.pastePlainText(paste) { editable }) onCommand()
        }
    }
    if (enabled && state.canUndo) {
        RichEditCommand("Undo", "Z", item, true, FontIconPrimitive.Undo) { command(state::undo) }
    }
    if (enabled && state.canRedo) {
        RichEditCommand("Redo", if (usesCommandKey) "Z" else "Y", item, true, FontIconPrimitive.Redo, shift = usesCommandKey) { command(state::redo) }
    }
    RichEditCommand("Select All", "A", item, state.text.isNotEmpty()) {
        command { state.selection = TextRange(0, state.text.length) }
    }
}

@Composable
private fun MenuFlyoutScope.RichEditCommand(
    label: String,
    key: String,
    modifier: Modifier,
    enabled: Boolean,
    icon: FontIconPrimitive? = null,
    shift: Boolean = false,
    action: () -> Unit
) {
    MenuFlyoutItem(
        onClick = action,
        enabled = enabled,
        modifier = modifier,
        icon = { if (icon != null) FontIcon(icon, contentDescription = null) },
        text = { Text(label) },
        trailing = { Text(menuShortcutLabel(key, ctrl = true, shift = shift)) }
    )
}

@Composable
private fun RichEditBoxFormatButton(
    label: String,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    content: @Composable () -> Unit
) {
    ToggleButton(
        checked = checked,
        onCheckedChanged = { onClick() },
        disabled = !enabled,
        colors = if (checked) ButtonDefaults.accentButtonColors() else ButtonDefaults.subtleButtonColors(),
        outsideBorder = false,
        iconOnly = true,
        modifier = modifier.semantics { contentDescription = label }
    ) { content() }
}

internal fun matchesEditorShortcut(commandKey: Boolean, meta: Boolean, ctrl: Boolean, shift: Boolean, key: Key): Boolean =
    (if (commandKey) meta && !ctrl else ctrl && !meta) && (!shift || key == Key.Z) &&
        !(commandKey && key == Key.Y)

private fun RichEditBoxState.handleKeyEvent(event: KeyEvent, editable: Boolean): Boolean {
    if (event.type != KeyEventType.KeyDown || event.isAltPressed ||
        !matchesEditorShortcut(usesCommandKey, event.isMetaPressed, event.isCtrlPressed, event.isShiftPressed, event.key)) return false
    val command: (() -> Unit) = when (event.key) {
        Key.B -> ::toggleBold
        Key.I -> ::toggleItalic
        Key.U -> ::toggleUnderline
        Key.Z -> if (event.isShiftPressed) ::redo else ::undo
        Key.Y -> ::redo
        Key.V, Key.X -> {
            breakUndoGroup()
            return false
        }
        else -> return false
    }
    // Never fall through to BasicTextField's text-only history, even in read-only mode.
    if (editable) command()
    return true
}

private class RichEditBoxPositionProvider(
    private val density: Density,
    private val selectionBounds: Rect,
    private val preferBelow: Boolean
) : FlyoutPositionProvider(density) {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = with(density) {
        applyAnimation = true
        val shadowPadding = flyoutPopPaddingFixShadowRender.roundToPx()
        val gap = 4.dp.roundToPx()
        val above = selectionBounds.top.roundToInt() - popupContentSize.height + shadowPadding - gap
        targetPlacement = if (!preferBelow && above >= -shadowPadding) FlyoutPlacement.Top else FlyoutPlacement.Bottom
        val y = if (targetPlacement == FlyoutPlacement.Top) above
        else selectionBounds.bottom.roundToInt() - shadowPadding + gap
        IntOffset(
            (selectionBounds.center.x - popupContentSize.width / 2f).roundToInt()
                .coerceIn(-shadowPadding, (windowSize.width - popupContentSize.width + shadowPadding).coerceAtLeast(-shadowPadding)),
            y.coerceIn(-shadowPadding, (windowSize.height - popupContentSize.height + shadowPadding).coerceAtLeast(-shadowPadding))
        )
    }
}
