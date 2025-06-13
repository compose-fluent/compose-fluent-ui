package io.github.composefluent.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle

/**
 * A composable that displays text.
 *
 * This composable provides a flexible way to display text with various styling options.
 * It supports customization of color, font size, font style, font weight, font family,
 * letter spacing, text decoration, text alignment, line height, overflow behavior, soft wrapping,
 * maximum and minimum lines, and text layout callbacks.
 *
 * @param text The text to be displayed.
 * @param modifier The [Modifier] to be applied to the text.
 * @param color The color to apply to the text. If [Color.Unspecified], and no color is specified in the [style],
 * this will be [LocalContentColor].
 * @param fontSize The size of the font. If [TextUnit.Unspecified], the font size from the [style] will
 * be used.
 * @param fontStyle The style of the font, such as italic or normal.
 * @param fontWeight The weight of the font, such as bold or normal.
 * @param fontFamily The family of the font.
 * @param letterSpacing The spacing between letters. If [TextUnit.Unspecified], the letter spacing
 * from the [style] will be used.
 * @param textDecoration The decoration to apply to the text, such as underline or line-through.
 * @param textAlign The alignment of the text within its container.
 * @param lineHeight The height of each line of text. If [TextUnit.Unspecified], the line height from
 * the [style] will be used.
 * @param overflow How visual overflow should be handled.
 * @param softWrap Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If true, soft line breaks will be taken into account when laying out the text.
 * @param maxLines The maximum number of lines to display. If the text exceeds this number, it will be truncated based on the [overflow] property.
 * @param minLines The minimum number of lines to display. Defaults to 1.
 * @param onTextLayout Callback that is executed when a new text layout is available.
 * @param style The [TextStyle] to be applied to the text.
 */
@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        AnnotatedString(text),
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        minLines,
        emptyMap(),
        onTextLayout,
        style
    )
}

/**
 * A composable that displays text.
 *
 * This composable provides a basic text element that can be customized with various styling options.
 * It uses [BasicText] internally to render the text, and merges the provided style parameters with
 * the [LocalTextStyle] to create the final text style.
 *
 * @param text The text to be displayed. It can be either a plain [String] or an [AnnotatedString].
 * @param modifier [Modifier] to apply to this layout node.
 * @param color The color to apply to the text. If [Color.Unspecified], and no color is specified in the
 *  [style], the current [LocalContentColor] will be used.
 * @param fontSize The size of the font for the text. If [TextUnit.Unspecified], the font size from
 *  [style] will be used.
 * @param fontStyle The font style to use for the text, such as [FontStyle.Italic]. If `null`, no
 *  style will be applied. If not `null`, and no font style is specified in the [style], this font
 *  style will be used.
 * @param fontWeight The font weight to use for the text, such as [FontWeight.Bold]. If `null`, no
 *  weight will be applied. If not `null`, and no font weight is specified in the [style], this
 *  font weight will be used.
 * @param fontFamily The font family to use for the text. If `null`, no font family will be applied.
 *  If not `null`, and no font family is specified in the [style], this font family will be used.
 * @param letterSpacing The amount of space to add between each letter in the text. If
 *  [TextUnit.Unspecified], the letter spacing from [style] will be used.
 * @param textDecoration The text decoration to apply to the text, such as [TextDecoration.Underline].
 *  If `null`, no decoration will be applied. If not `null`, and no text decoration is specified
 *  in the [style], this text decoration will be used.
 * @param textAlign The alignment of the text within its container. If [TextAlign.Unspecified], the
 *  text alignment from [style] will be used.
 */
@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )
    BasicText(
        text,
        modifier,
        mergedStyle,
        onTextLayout,
        overflow,
        softWrap,
        maxLines,
        minLines,
        inlineContent
    )
}

