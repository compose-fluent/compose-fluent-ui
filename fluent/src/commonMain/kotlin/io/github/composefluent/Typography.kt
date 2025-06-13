package io.github.composefluent

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val LocalTypography = staticCompositionLocalOf {
    Typography(
        caption = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp, lineHeight = 16.sp
        ),
        body = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp, lineHeight = 20.sp
        ),
        bodyStrong = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp, lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp, lineHeight = 24.sp
        ),
        subtitle = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp, lineHeight = 28.sp
        ),
        title = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp, lineHeight = 36.sp
        ),
        titleLarge = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp, lineHeight = 52.sp
        ),
        display = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 68.sp, lineHeight = 92.sp
        )
    )
}

/**
 * The Fluent Design typography system, providing a set of predefined text styles.
 *
 * Based on the guidelines from Microsoft's Fluent Design documentation:
 * https://docs.microsoft.com/en-us/windows/apps/design/signature-experiences/typography
 *
 * @property caption Style for smaller text, often used for labels or annotations.
 * @property body Standard body text style.
 * @property bodyStrong Bold version of the standard body text style.
 * @property bodyLarge Larger version of the standard body text style.
 * @property subtitle Style for subtitles or secondary headings.
 * @property title Style for main titles.
 * @property titleLarge Style for large titles, suitable for prominent headings.
 * @property display Style for very large text, often used for headlines or displays.
 */
@Immutable
class Typography(
    val caption: TextStyle,
    val body: TextStyle,
    val bodyStrong: TextStyle,
    val bodyLarge: TextStyle,
    val subtitle: TextStyle,
    val title: TextStyle,
    val titleLarge: TextStyle,
    val display: TextStyle
)

/**
 * CompositionLocal containing the current [TextStyle] for text components.
 */
val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

/**
 * This composable provides a [TextStyle] to the composition local tree.
 * Any text composables within the `content` lambda will use this provided text style.
 * The provided [value] is merged with the current [LocalTextStyle].
 *
 * @param value The [TextStyle] to provide. This will be merged with the current [LocalTextStyle].
 * @param content The composable content that will inherit the provided [TextStyle].
 */
@Composable
fun ProvideTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}