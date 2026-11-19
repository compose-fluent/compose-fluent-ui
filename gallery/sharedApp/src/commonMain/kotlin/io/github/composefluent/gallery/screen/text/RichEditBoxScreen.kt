package io.github.composefluent.gallery.screen.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import io.github.composefluent.component.Flyout
import io.github.composefluent.component.FlyoutPlacement
import io.github.composefluent.component.SubtleButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import io.github.composefluent.component.Button
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.ContentDialog
import io.github.composefluent.component.ContentDialogButton
import io.github.composefluent.component.FontIcon
import io.github.composefluent.component.FontIconPrimitive
import io.github.composefluent.component.CommandBarButton
import io.github.composefluent.component.MenuFlyoutItem
import io.github.composefluent.component.CheckBox
import io.github.composefluent.component.RichEditBox
import io.github.composefluent.component.RichEditBoxToolbar
import io.github.composefluent.component.Text
import io.github.composefluent.component.rememberRichEditBoxState
import io.github.composefluent.gallery.annotation.Component
import io.github.composefluent.gallery.annotation.Sample
import io.github.composefluent.gallery.component.ComponentPagePath
import io.github.composefluent.gallery.component.GalleryPage
import io.github.composefluent.source.generated.FluentSourceFile

@Component(index = 7, description = "A multi-line text field with character formatting and undo/redo.")
@Composable
fun RichEditBoxScreen() {
    var enabled by remember { mutableStateOf(true) }
    var readOnly by remember { mutableStateOf(false) }
    GalleryPage(
        title = "RichEditBox",
        description = "Use a RichEditBox to edit formatted text. Select text to show the formatting toolbar, or use Ctrl/Cmd+B, I, and U. Undo and redo restore both text and formatting.",
        componentPath = FluentSourceFile.RichEditBox,
        galleryPath = ComponentPagePath.RichEditBoxScreen
    ) {
        Section(
            title = "A simple RichEditBox.",
            sourceCode = sourceCodeOfRichEditBoxSample,
            content = { RichEditBoxSample(enabled, readOnly) },
            options = {
                CheckBox(checked = !enabled, onCheckStateChange = { enabled = !it }, label = "Disable editor")
                CheckBox(checked = readOnly, onCheckStateChange = { readOnly = it }, label = "Read only")
            }
        )
        Section(
            title = "A RichEditBox with custom menu commands.",
            sourceCode = sourceCodeOfRichEditBoxHeaderSample,
            content = { RichEditBoxHeaderSample() }
        )
        Section(
            title = "Formatted text with a persistent toolbar and undo/redo.",
            sourceCode = sourceCodeOfRichEditBoxFormattingSample,
            content = { RichEditBoxFormattingSample() }
        )
    }
}

@Sample
@Composable
private fun RichEditBoxSample(enabled: Boolean = true, readOnly: Boolean = false) {
    RichEditBox(
        state = rememberRichEditBoxState("This is Body text. Windows is personal: it adapts seamlessly to the way I use my device."),
        modifier = Modifier.width(160.dp),
        enabled = enabled,
        readOnly = readOnly
    )
}

@Sample
@Composable
private fun RichEditBoxHeaderSample() {
    val state = rememberRichEditBoxState()
    val clipboard = LocalClipboardManager.current
    var shareText by remember { mutableStateOf<String?>(null) }
    RichEditBox(
        state = state,
        modifier = Modifier.width(240.dp),
        header = { Text("Header") },
        placeholder = { Text("Text") },
        primary = { expanded ->
            item {
                CommandBarButton(
                    onClick = {
                        val range = state.selection
                        shareText = if (range.collapsed) state.text.toString()
                            else state.text.substring(range.min, range.max)
                    },
                    modifier = Modifier.focusProperties { canFocus = false }
                        .semantics { contentDescription = "Share" },
                    enabled = state.text.isNotEmpty()
                ) {
                    FontIcon(FontIconPrimitive.Share, contentDescription = null)
                    if (expanded || isOverflow) Text("Share", style = FluentTheme.typography.caption)
                }
            }
        },
        secondary = { dismiss ->
            MenuFlyoutItem(
                text = { Text("Clear text") },
                icon = {},
                enabled = state.text.isNotEmpty(),
                onClick = {
                    state.edit { replace(0, length, "") }
                    dismiss()
                }
            )
        }
    )
    ContentDialog(
        title = "Share text",
        visible = shareText != null,
        content = { Text(shareText.orEmpty()) },
        primaryButtonText = "Copy text",
        closeButtonText = "Close",
        onButtonClick = {
            if (it == ContentDialogButton.Primary) clipboard.setText(AnnotatedString(shareText.orEmpty()))
            shareText = null
        }
    )
}

@Sample
@Composable
private fun RichEditBoxFormattingSample() {
    val state = rememberRichEditBoxState(buildAnnotatedString {
        append("This is ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("bold") }
        append(", ")
        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("italic") }
        append(", and ")
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("underlined") }
        append(" text.\nSelect some text to change its formatting.")
    })
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        RichEditBoxToolbar(state, retainEditorFocus = true, primary = {
            item {
                var open by remember { mutableStateOf(false) }
                Box {
                    SubtleButton(
                        onClick = { open = !open },
                        modifier = Modifier.semantics { contentDescription = "Text color" }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("A", style = FluentTheme.typography.caption)
                            Box(Modifier.size(16.dp, 4.dp).border(1.dp, FluentTheme.colors.text.text.primary)
                                .background(if (state.textColor == Color.Unspecified) Color.Transparent else state.textColor))
                        }
                        FontIcon(FontIconPrimitive.ChevronDown, contentDescription = null)
                    }
                    Flyout(
                        visible = open,
                        onDismissRequest = { open = false },
                        placement = FlyoutPlacement.Bottom,
                        focusable = false
                    ) {
                        val colors = listOf(
                            "Red" to Color.Red, "Orange" to Color(0xFFFFA500), "Yellow" to Color.Yellow,
                            "Green" to Color(0xFF008000), "Blue" to Color.Blue, "Indigo" to Color(0xFF4B0082),
                            "Violet" to Color(0xFFEE82EE), "Gray" to Color.Gray
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            colors.chunked(3).forEach { row ->
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    row.forEach { (name, color) ->
                                        Box(Modifier.size(32.dp).background(color)
                                            .border(1.dp, FluentTheme.colors.stroke.control.default)
                                            .focusProperties { canFocus = false }
                                            .semantics { contentDescription = name }
                                            .clickable(role = Role.Button) {
                                                state.setTextColor(color)
                                                open = false
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
        RichEditBox(
            state = state,
            modifier = Modifier.width(320.dp),
            showFormattingToolbar = false
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = state::undo, disabled = !state.canUndo) { Text("Undo") }
            Button(onClick = state::redo, disabled = !state.canRedo) { Text("Redo") }
        }
        Text(state.toAnnotatedString())
    }
}
