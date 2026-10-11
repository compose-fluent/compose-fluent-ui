package io.github.composefluent.gallery.component

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import io.github.composefluent.component.Text
import io.github.composefluent.gallery.LocalStore
import io.github.composefluent.gallery.code.KotlinCodeDocument
import io.github.composefluent.gallery.code.KotlinCodePalette

/** Runtime rendering consumes generated data, without a parser or highlighter. */
@Composable
fun SourceCode(code: KotlinCodeDocument, modifier: Modifier = Modifier) {
    val isDark = LocalStore.current.darkMode
    val annotated = remember(code, isDark) {
        val palette = if (isDark) KotlinCodePalette.Dark else KotlinCodePalette.Light
        buildAnnotatedString {
            append(code.source)
            var start = 0
            code.spans.forEach { span ->
                val style = palette.style(span.kind)
                addStyle(
                    SpanStyle(
                        color = Color(0xFF000000L or style.foreground.toLong()),
                        fontStyle = if (style.italic) FontStyle.Italic else FontStyle.Normal,
                    ),
                    start, span.end,
                )
                start = span.end
            }
        }
    }
    SelectionContainer {
        Text(modifier = modifier, text = annotated, fontFamily = FontFamily.Monospace, overflow = TextOverflow.Visible)
    }
}
