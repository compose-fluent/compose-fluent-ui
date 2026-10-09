package io.github.composefluent.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.selectAll
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentColor
import io.github.composefluent.math.NumberBoxParser
import io.github.composefluent.scheme.VisualStateScheme

/** Controls where a [NumberBox] displays its increment and decrement buttons. */
enum class NumberBoxSpinButtonPlacement {
    /** Does not display spin buttons. */
    Hidden,

    /** Displays a compact indicator that opens spin buttons in a flyout. */
    Compact,

    /** Displays spin buttons inline at the trailing edge of the text field. */
    Inline,
}

/** Controls how a [NumberBox] responds to invalid or out-of-range text. */
enum class NumberBoxValidationMode {
    /** Replaces invalid text and coerces out-of-range values when editing is committed. */
    InvalidInputOverwritten,

    /** Preserves invalid text and allows committed values outside the configured range. */
    Disabled,
}

/**
 * Converts [Double] values to and from the localized text displayed by a [NumberBox].
 *
 * [parse] returns `null` only when the complete input cannot be parsed as a finite number.
 */
@Stable
interface NumberBoxFormatter {
    /** Returns the text displayed for [value]. */
    fun format(value: Double): String

    /** Returns the finite value represented by all of [text], or `null` when parsing fails. */
    fun parse(text: String): Double?
}

/**
 * Displays and edits a numeric value using WinUI-style commit, validation, and stepping behavior.
 *
 * The caller owns [value]. [Double.NaN] represents an empty field. Text is committed when the user
 * presses Enter, performs the IME action, or moves focus away from the field.
 *
 * @param value The current controlled value. Use [Double.NaN] for an empty field.
 * @param onValueChange Called with a committed value that differs from [value].
 * @param modifier Modifier applied to the complete control, including [header].
 * @param enabled Whether the field and spin buttons accept user input.
 * @param valueRange Range used for validation, clamping, button state, and optional wrapping.
 * @param smallChange Change applied by arrow keys, the pointer wheel, and spin buttons.
 * @param largeChange Change applied by Page Up and Page Down.
 * @param acceptsExpression Whether input may contain WinUI-compatible arithmetic expressions.
 * @param validationMode How invalid and out-of-range input is handled when committed.
 * @param spinButtonPlacement Where increment and decrement buttons are displayed.
 * @param isWrapEnabled Whether stepping past one finite range bound wraps to the opposite bound.
 * @param isClearable Whether a clear button is shown while the focused field contains text.
 * @param formatter Formatter used for display and for every numeric token accepted from input.
 * @param header Optional content displayed above the text field.
 * @param placeholder Optional content displayed while the editing text is empty.
 * @param interactionSource Interaction stream used by the underlying text field.
 */
@Composable
fun NumberBox(
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Double> = -Double.MAX_VALUE..Double.MAX_VALUE,
    smallChange: Double = 1.0,
    largeChange: Double = 10.0,
    acceptsExpression: Boolean = false,
    validationMode: NumberBoxValidationMode = NumberBoxValidationMode.InvalidInputOverwritten,
    spinButtonPlacement: NumberBoxSpinButtonPlacement = NumberBoxSpinButtonPlacement.Hidden,
    isWrapEnabled: Boolean = false,
    isClearable: Boolean = true,
    formatter: NumberBoxFormatter = NumberBoxDefaults.numberFormatter(),
    header: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    require(valueRange.start <= valueRange.endInclusive) {
        "NumberBox valueRange must not be empty."
    }
    require(
        !isWrapEnabled || valueRange.start.isFinite() && valueRange.endInclusive.isFinite()
    ) {
        "NumberBox requires a finite valueRange when wrapping is enabled."
    }

    val textFieldState = rememberTextFieldState(numberBoxText(value, formatter))
    val editProperties = rememberUpdatedState(
        NumberBoxEditProperties(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            acceptsExpression = acceptsExpression,
            validationMode = validationMode,
            isWrapEnabled = isWrapEnabled,
            formatter = formatter,
        )
    )
    val editState = remember(textFieldState) {
        NumberBoxEditState(
            textFieldState = textFieldState,
            currentProperties = editProperties,
        )
    }

    LaunchedEffect(value.toBits(), formatter, editState.pendingValue?.toBits()) {
        editState.synchronizeExternalValue()
    }
    LaunchedEffect(editState.isFocused) {
        if (editState.isFocused) {
            withFrameNanos { }
            editState.selectAll()
        }
    }

    val increaseEnabled = numberBoxStepEnabled(
        enabled = enabled,
        value = value,
        change = smallChange,
        valueRange = valueRange,
        validationMode = validationMode,
        isWrapEnabled = isWrapEnabled,
    )
    val decreaseEnabled = numberBoxStepEnabled(
        enabled = enabled,
        value = value,
        change = -smallChange,
        valueRange = valueRange,
        validationMode = validationMode,
        isWrapEnabled = isWrapEnabled,
    )
    val progressInfo = numberBoxProgressInfo(value, valueRange)
    val rangeSemantics = if (progressInfo != null) {
        Modifier.semantics {
            progressBarRangeInfo = progressInfo
            setProgress { requestedValue ->
                if (!enabled) {
                    false
                } else {
                    editState.setValue(
                        requestedValue.toDouble().coerceIn(
                            valueRange.start,
                            valueRange.endInclusive,
                        )
                    )
                    true
                }
            }
        }
    } else {
        Modifier
    }
    val density = LocalDensity.current
    val popupPositionProvider = remember(density) {
        NumberBoxPopupPositionProvider(density)
    }
    val fieldModifier = modifier
        .then(
            if (spinButtonPlacement == NumberBoxSpinButtonPlacement.Inline) {
                Modifier.defaultMinSize(minWidth = NumberBoxInlineMinWidth)
            } else {
                Modifier
            }
        )
        .then(rangeSemantics)
        .onPreviewKeyEvent { event ->
            if (!enabled || event.type != KeyEventType.KeyDown) {
                false
            } else {
                when (event.key) {
                    Key.Enter -> {
                        editState.commit()
                        true
                    }

                    Key.Escape -> {
                        editState.restoreExternalValue()
                        true
                    }

                    Key.DirectionUp -> editState.step(smallChange)
                    Key.DirectionDown -> editState.step(-smallChange)
                    Key.PageUp -> editState.step(largeChange)
                    Key.PageDown -> editState.step(-largeChange)
                    else -> false
                }
            }
        }
        .onFocusChanged { focusState ->
            editState.onFocusChanged(focusState.isFocused)
        }
        .pointerInput(enabled, editState.isFocused, smallChange) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    if (enabled && editState.isFocused && event.type == PointerEventType.Scroll) {
                        val scrollY = event.changes.firstOrNull()?.scrollDelta?.y ?: 0f
                        val stepped = when {
                            scrollY < 0f -> editState.step(smallChange)
                            scrollY > 0f -> editState.step(-smallChange)
                            else -> false
                        }
                        if (stepped) {
                            event.changes.forEach { it.consume() }
                        }
                    }
                }
            }
        }

    TextField(
        state = editState.textFieldState,
        modifier = fieldModifier,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (acceptsExpression) KeyboardType.Text else KeyboardType.Decimal,
            imeAction = ImeAction.Done,
        ),
        onKeyboardAction = { editState.commit() },
        lineLimits = TextFieldLineLimits.SingleLine,
        header = header,
        trailing = when (spinButtonPlacement) {
            NumberBoxSpinButtonPlacement.Hidden -> null
            NumberBoxSpinButtonPlacement.Inline -> {
                {
                    NumberBoxSpinButton(
                        increase = true,
                        enabled = increaseEnabled,
                        onClick = { editState.step(smallChange) },
                        modifier = Modifier.width(NumberBoxInlineButtonWidth),
                    )
                    NumberBoxSpinButton(
                        increase = false,
                        enabled = decreaseEnabled,
                        onClick = { editState.step(-smallChange) },
                        modifier = Modifier.width(NumberBoxInlineButtonWidth),
                    )
                }
            }

            NumberBoxSpinButtonPlacement.Compact -> {
                {
                    NumberBoxCompactSpinButtons(
                        enabled = enabled,
                        visible = enabled && editState.isFocused,
                        increaseEnabled = increaseEnabled,
                        decreaseEnabled = decreaseEnabled,
                        onIncrease = { editState.step(smallChange) },
                        onDecrease = { editState.step(-smallChange) },
                        positionProvider = popupPositionProvider,
                    )
                }
            }
        },
        placeholder = placeholder,
        isClearable = isClearable,
        interactionSource = interactionSource,
    )
}

@Composable
private fun RowScope.NumberBoxCompactSpinButtons(
    enabled: Boolean,
    visible: Boolean,
    increaseEnabled: Boolean,
    decreaseEnabled: Boolean,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    positionProvider: NumberBoxPopupPositionProvider,
) {
    val flyoutButtonColors = ButtonDefaults.subtleButtonColors()
    Box(
        modifier = Modifier.size(width = 28.dp, height = 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides if (enabled) {
                FluentTheme.colors.text.text.secondary
            } else {
                FluentTheme.colors.text.text.disabled
            }
        ) {
            FontIcon(
                type = FontIconPrimitive.ChevronUpDown,
                contentDescription = null,
                size = FontIconSize.Small,
                fallbackSize = FontIconSize.Small,
            )
        }
        BasicFlyout(
            visible = visible,
            onDismissRequest = {},
            contentPadding = PaddingValues(0.dp),
            positionProvider = positionProvider,
            focusable = false,
        ) {
            Column(
                modifier = Modifier.padding(NumberBoxCompactFlyoutMargin),
            ) {
                NumberBoxSpinButton(
                    increase = true,
                    enabled = increaseEnabled,
                    onClick = onIncrease,
                    modifier = Modifier.padding(4.dp).size(32.dp),
                    iconSize = FontIconSize.Standard,
                    colors = flyoutButtonColors,
                )
                NumberBoxSpinButton(
                    increase = false,
                    enabled = decreaseEnabled,
                    onClick = onDecrease,
                    modifier = Modifier.padding(4.dp).size(32.dp),
                    iconSize = FontIconSize.Standard,
                    colors = flyoutButtonColors,
                )
            }
        }
    }
}

@Composable
private fun NumberBoxSpinButton(
    increase: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: FontIconSize = FontIconSize.Small,
    colors: VisualStateScheme<ButtonColor> = TextBoxButtonDefaults.buttonColors(),
) {
    val description = if (increase) "Increase" else "Decrease"
    RepeatTextBoxButton(
        onClick = onClick,
        modifier = modifier.semantics { contentDescription = description },
        enabled = enabled,
        colors = colors,
    ) {
        FontIcon(
            type = if (increase) FontIconPrimitive.ChevronUp else FontIconPrimitive.ChevronDown,
            contentDescription = null,
            size = iconSize,
            fallbackSize = iconSize,
        )
    }
}

private class NumberBoxEditProperties(
    val value: Double,
    val onValueChange: (Double) -> Unit,
    val valueRange: ClosedFloatingPointRange<Double>,
    val acceptsExpression: Boolean,
    val validationMode: NumberBoxValidationMode,
    val isWrapEnabled: Boolean,
    val formatter: NumberBoxFormatter,
)

@Stable
private class NumberBoxEditState(
    val textFieldState: TextFieldState,
    private val currentProperties: State<NumberBoxEditProperties>,
) {
    var isFocused by mutableStateOf(false)
        private set

    var pendingValue by mutableStateOf<Double?>(null)
        private set

    fun synchronizeExternalValue() {
        val properties = currentProperties.value
        pendingValue = null
        val synchronizedText = numberBoxText(properties.value, properties.formatter)
        if (textFieldState.text.toString() != synchronizedText) {
            textFieldState.setTextAndPlaceCursorAtEnd(synchronizedText)
        }
    }

    fun restoreExternalValue() {
        restoreExternalValue(currentProperties.value)
    }

    fun setValue(newValue: Double) {
        setValue(newValue, currentProperties.value)
    }

    fun commit() {
        val properties = currentProperties.value
        when (val input = parseCurrentInput(properties)) {
            NumberBoxInputResult.Empty -> setValue(Double.NaN, properties)
            NumberBoxInputResult.Invalid -> {
                if (properties.validationMode == NumberBoxValidationMode.InvalidInputOverwritten) {
                    restoreExternalValue(properties)
                }
            }

            is NumberBoxInputResult.Valid -> {
                val committedValue = if (
                    properties.validationMode == NumberBoxValidationMode.InvalidInputOverwritten
                ) {
                    input.value.coerceIn(
                        properties.valueRange.start,
                        properties.valueRange.endInclusive,
                    )
                } else {
                    input.value
                }
                setValue(committedValue, properties)
            }
        }
    }

    fun step(change: Double): Boolean {
        val properties = currentProperties.value
        val parsedBase = when (val input = parseCurrentInput(properties)) {
            is NumberBoxInputResult.Valid -> input.value
            NumberBoxInputResult.Empty, NumberBoxInputResult.Invalid -> properties.value
        }
        if (parsedBase.isNaN()) {
            return false
        }
        val base = if (
            properties.validationMode == NumberBoxValidationMode.InvalidInputOverwritten
        ) {
            parsedBase.coerceIn(
                properties.valueRange.start,
                properties.valueRange.endInclusive,
            )
        } else {
            parsedBase
        }
        val steppedValue = calculateNumberBoxStep(
            value = base,
            change = change,
            valueRange = properties.valueRange,
            validationMode = properties.validationMode,
            isWrapEnabled = properties.isWrapEnabled,
        ) ?: return false
        setValue(steppedValue, properties)
        return true
    }

    fun onFocusChanged(focused: Boolean) {
        val lostFocus = isFocused && !focused
        isFocused = focused
        if (lostFocus) {
            commit()
        }
    }

    fun selectAll() {
        textFieldState.edit { selectAll() }
    }

    private fun restoreExternalValue(properties: NumberBoxEditProperties) {
        pendingValue = null
        textFieldState.setTextAndPlaceCursorAtEnd(
            numberBoxText(properties.value, properties.formatter)
        )
    }

    private fun setValue(
        newValue: Double,
        properties: NumberBoxEditProperties,
    ) {
        textFieldState.setTextAndPlaceCursorAtEnd(
            numberBoxText(newValue, properties.formatter)
        )
        if (!sameNumberBoxValue(newValue, properties.value)) {
            pendingValue = newValue
            properties.onValueChange(newValue)
        } else {
            pendingValue = null
        }
    }

    private fun parseCurrentInput(
        properties: NumberBoxEditProperties,
    ): NumberBoxInputResult = parseNumberBoxInput(
        text = textFieldState.text.toString(),
        acceptsExpression = properties.acceptsExpression,
        formatter = properties.formatter,
    )
}

private sealed interface NumberBoxInputResult {
    data object Empty : NumberBoxInputResult
    data class Valid(val value: Double) : NumberBoxInputResult
    data object Invalid : NumberBoxInputResult
}

private fun parseNumberBoxInput(
    text: String,
    acceptsExpression: Boolean,
    formatter: NumberBoxFormatter,
): NumberBoxInputResult {
    val input = text.trim()
    if (input.isEmpty()) {
        return NumberBoxInputResult.Empty
    }
    val value = if (acceptsExpression) {
        NumberBoxParser.compute(input, formatter::parse)
    } else {
        formatter.parse(input)?.takeIf { it.isFinite() }
    }
    return if (value == null) {
        NumberBoxInputResult.Invalid
    } else {
        NumberBoxInputResult.Valid(value)
    }
}

private fun calculateNumberBoxStep(
    value: Double,
    change: Double,
    valueRange: ClosedFloatingPointRange<Double>,
    validationMode: NumberBoxValidationMode,
    isWrapEnabled: Boolean,
): Double? {
    val candidate = value + change
    if (candidate.isNaN()) {
        return null
    }
    if (!candidate.isFinite()) {
        if (!isWrapEnabled && validationMode == NumberBoxValidationMode.Disabled) {
            return null
        }
        return if (candidate > 0.0) {
            if (isWrapEnabled) valueRange.start else valueRange.endInclusive
        } else {
            if (isWrapEnabled) valueRange.endInclusive else valueRange.start
        }
    }
    return when {
        isWrapEnabled && candidate > valueRange.endInclusive -> valueRange.start
        isWrapEnabled && candidate < valueRange.start -> valueRange.endInclusive
        validationMode == NumberBoxValidationMode.InvalidInputOverwritten -> {
            candidate.coerceIn(valueRange.start, valueRange.endInclusive)
        }

        else -> candidate
    }
}

private fun numberBoxStepEnabled(
    enabled: Boolean,
    value: Double,
    change: Double,
    valueRange: ClosedFloatingPointRange<Double>,
    validationMode: NumberBoxValidationMode,
    isWrapEnabled: Boolean,
): Boolean {
    if (!enabled || value.isNaN()) {
        return false
    }
    if (validationMode == NumberBoxValidationMode.Disabled) {
        return true
    }
    val base = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val result = calculateNumberBoxStep(
        value = base,
        change = change,
        valueRange = valueRange,
        validationMode = validationMode,
        isWrapEnabled = isWrapEnabled,
    ) ?: return false
    return !sameNumberBoxValue(base, result)
}

private fun numberBoxProgressInfo(
    value: Double,
    valueRange: ClosedFloatingPointRange<Double>,
): ProgressBarRangeInfo? {
    if (!value.isFinite() || value !in valueRange) {
        return null
    }
    val start = valueRange.start.toFloat()
    val end = valueRange.endInclusive.toFloat()
    val current = value.toFloat()
    if (!start.isFinite() || !end.isFinite() || !current.isFinite()) {
        return null
    }
    if (valueRange.start != valueRange.endInclusive && start >= end) {
        return null
    }
    return ProgressBarRangeInfo(current = current, range = start..end)
}

private fun numberBoxText(value: Double, formatter: NumberBoxFormatter): String =
    if (value.isNaN()) "" else formatter.format(value)

private fun sameNumberBoxValue(first: Double, second: Double): Boolean =
    first == second || first.isNaN() && second.isNaN()

@Stable
private class NumberBoxPopupPositionProvider(density: Density) : FlyoutPositionProvider(density) {
    private val windowPadding = with(density) { flyoutDefaultPadding.roundToPx() }
    private val shadowPadding = with(density) { flyoutPopPaddingFixShadowRender.roundToPx() }

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val popupSurfaceSize = IntSize(
            width = (popupContentSize.width - shadowPadding * 2).coerceAtLeast(0),
            height = (popupContentSize.height - shadowPadding * 2).coerceAtLeast(0),
        )
        val minX = windowPadding.coerceAtMost(
            (windowSize.width - popupSurfaceSize.width).coerceAtLeast(0)
        )
        val maxX = (windowSize.width - windowPadding - popupSurfaceSize.width)
            .coerceAtLeast(minX)
        val minY = windowPadding.coerceAtMost(
            (windowSize.height - popupSurfaceSize.height).coerceAtLeast(0)
        )
        val maxY = (windowSize.height - windowPadding - popupSurfaceSize.height)
            .coerceAtLeast(minY)
        val alignedSurfaceOffset = Alignment.CenterStart.align(
            size = popupSurfaceSize,
            space = anchorBounds.size,
            layoutDirection = layoutDirection,
        )
        val surfacePosition = anchorBounds.topLeft + alignedSurfaceOffset
        val popupPosition = IntOffset(
            x = surfacePosition.x.coerceIn(minX, maxX) - shadowPadding,
            y = surfacePosition.y.coerceIn(minY, maxY) - shadowPadding,
        )
        targetPlacement = FlyoutPlacement.Full
        applyAnimation = true
        return popupPosition
    }
}

/** Default values and factories used by [NumberBox]. */
object NumberBoxDefaults {
    /** Returns a formatter that uses the current platform locale and up to ten fractional digits. */
    @Composable
    fun numberFormatter(): NumberBoxFormatter = remember { createPlatformNumberBoxFormatter() }
}

internal expect fun createPlatformNumberBoxFormatter(): NumberBoxFormatter

private val NumberBoxInlineMinWidth = 120.dp
private val NumberBoxInlineButtonWidth = 32.dp
private val NumberBoxCompactFlyoutMargin = 4.dp
