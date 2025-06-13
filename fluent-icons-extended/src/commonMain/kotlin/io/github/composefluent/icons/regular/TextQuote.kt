

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TextQuote: ImageVector
    get() {
        if (_textQuote != null) {
            return _textQuote!!
        }
        _textQuote = fluentIcon(name = "Regular.TextQuote") {
            fluentPath {
                moveTo(7.5f, 6.0f)
                arcTo(2.5f, 2.5f, 0.0f, false, true, 10.0f, 8.34f)
                verticalLineToRelative(0.2f)
                arcToRelative(13.01f, 13.01f, 0.0f, false, true, -3.7f, 9.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.1f, -1.02f)
                arcToRelative(11.55f, 11.55f, 0.0f, false, false, 3.08f, -5.86f)
                arcTo(2.5f, 2.5f, 0.0f, true, true, 7.5f, 6.0f)
                close()
                moveTo(16.5f, 6.0f)
                arcTo(2.5f, 2.5f, 0.0f, false, true, 19.0f, 8.34f)
                verticalLineToRelative(0.2f)
                arcToRelative(12.99f, 12.99f, 0.0f, false, true, -3.7f, 9.23f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.1f, -1.04f)
                arcToRelative(11.51f, 11.51f, 0.0f, false, false, 3.08f, -5.85f)
                arcTo(2.5f, 2.5f, 0.0f, true, true, 16.5f, 6.0f)
                close()
                moveTo(7.5f, 7.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 1.0f, 1.12f)
                verticalLineToRelative(-0.13f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.0f, -0.99f)
                close()
                moveTo(16.5f, 7.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 1.0f, 1.12f)
                verticalLineToRelative(-0.13f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.0f, -0.99f)
                close()
            }
        }
        return _textQuote!!
    }

private var _textQuote: ImageVector? = null
