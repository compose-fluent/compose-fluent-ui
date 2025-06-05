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
 * https://docs.microsoft.com/en-us/windows/apps/design/signature-experiences/typography
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

val LocalTextStyle = compositionLocalOf(structuralEqualityPolicy()) { TextStyle.Default }

@Composable
fun ProvideTextStyle(value: TextStyle, content: @Composable () -> Unit) {
    val mergedStyle = LocalTextStyle.current.merge(value)
    CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}