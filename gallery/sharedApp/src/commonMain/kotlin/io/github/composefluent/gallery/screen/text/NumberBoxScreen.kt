package io.github.composefluent.gallery.screen.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.composefluent.component.NumberBox
import io.github.composefluent.component.NumberBoxFormatter
import io.github.composefluent.component.NumberBoxSpinButtonPlacement
import io.github.composefluent.component.Text
import io.github.composefluent.gallery.annotation.Component
import io.github.composefluent.gallery.annotation.Sample
import io.github.composefluent.gallery.component.ComponentPagePath
import io.github.composefluent.gallery.component.GalleryPage
import io.github.composefluent.source.generated.FluentSourceFile
import kotlin.math.round

@Component(
    index = 16,
    description = "A control that can be used to display and edit numeric values.",
)
@Composable
fun NumberBoxScreen() {
    GalleryPage(
        title = "NumberBox",
        description = "Use a NumberBox to display and edit numeric values.",
        componentPath = FluentSourceFile.NumberBox,
        galleryPath = ComponentPagePath.NumberBoxScreen,
    ) {
        Section(
            title = "A basic NumberBox. Clear the text to set an empty value.",
            sourceCode = sourceCodeOfBasicNumberBoxSample,
        ) {
            BasicNumberBoxSample()
        }

        Section(
            title = "A NumberBox that evaluates arithmetic expressions when committed.",
            sourceCode = sourceCodeOfExpressionNumberBoxSample,
        ) {
            ExpressionNumberBoxSample()
        }

        Section(
            title = "A bounded NumberBox with inline spin buttons.",
            sourceCode = sourceCodeOfInlineNumberBoxSample,
        ) {
            InlineNumberBoxSample()
        }

        Section(
            title = "A NumberBox with compact spin buttons.",
            sourceCode = sourceCodeOfCompactNumberBoxSample,
        ) {
            CompactNumberBoxSample()
        }

        Section(
            title = "A NumberBox that wraps between finite range bounds.",
            sourceCode = sourceCodeOfWrappingNumberBoxSample,
        ) {
            WrappingNumberBoxSample()
        }

        Section(
            title = "A NumberBox with a custom one-decimal formatter.",
            sourceCode = sourceCodeOfCustomFormatterNumberBoxSample,
        ) {
            CustomFormatterNumberBoxSample()
        }
    }
}

@Sample
@Composable
private fun BasicNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(42.0) }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            header = { Text("Quantity") },
            placeholder = { Text("Enter a number") },
        )
    }
}

@Sample
@Composable
private fun ExpressionNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(Double.NaN) }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            acceptsExpression = true,
            header = { Text("Expression") },
            placeholder = { Text("1 + 2^2") },
        )
    }
}

@Sample
@Composable
private fun InlineNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(50.0) }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            valueRange = 0.0..100.0,
            smallChange = 5.0,
            spinButtonPlacement = NumberBoxSpinButtonPlacement.Inline,
            header = { Text("Percentage") },
        )
    }
}

@Sample
@Composable
private fun CompactNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(10.0) }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            spinButtonPlacement = NumberBoxSpinButtonPlacement.Compact,
            header = { Text("Compact") },
        )
    }
}

@Sample
@Composable
private fun WrappingNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(5.0) }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            valueRange = 0.0..5.0,
            spinButtonPlacement = NumberBoxSpinButtonPlacement.Inline,
            isWrapEnabled = true,
            header = { Text("Wrapping value") },
        )
    }
}

@Sample
@Composable
private fun CustomFormatterNumberBoxSample() {
    var value by remember { mutableDoubleStateOf(21.5) }
    val oneDecimalFormatter: NumberBoxFormatter = remember {
        object : NumberBoxFormatter {
            override fun format(value: Double): String {
                val rounded = round(value * 10.0) / 10.0
                val text = rounded.toString()
                val whole = text.substringBefore('.')
                val fraction = text.substringAfter('.', "0").padEnd(1, '0').take(1)
                return "$whole.$fraction"
            }

            override fun parse(text: String): Double? =
                text.trim().toDoubleOrNull()?.takeIf { it.isFinite() }
        }
    }

    NumberBoxSampleLayout(value) {
        NumberBox(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.width(NumberBoxSampleWidth),
            valueRange = -100.0..100.0,
            formatter = oneDecimalFormatter,
            header = { Text("Temperature") },
        )
    }
}

@Composable
private fun NumberBoxSampleLayout(
    value: Double,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        content()
        Text(if (value.isNaN()) "Value: empty" else "Value: $value")
    }
}

private val NumberBoxSampleWidth = 240.dp
