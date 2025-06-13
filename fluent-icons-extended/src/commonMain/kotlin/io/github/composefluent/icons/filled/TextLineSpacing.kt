

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextLineSpacing: ImageVector
    get() {
        if (_textLineSpacing != null) {
            return _textLineSpacing!!
        }
        _textLineSpacing = fluentIcon(name = "Filled.TextLineSpacing") {
            fluentPath {
                moveTo(20.0f, 6.41f)
                lineTo(20.0f, 10.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                lineTo(18.0f, 6.41f)
                lineToRelative(-0.3f, 0.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, -1.4f, -1.42f)
                lineToRelative(2.0f, -2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, 0.0f)
                lineToRelative(2.0f, 2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, 1.42f)
                lineToRelative(-0.3f, -0.3f)
                close()
                moveTo(2.0f, 6.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, -1.0f)
                horizontalLineToRelative(8.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 0.0f, 2.0f)
                lineTo(3.0f, 7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                close()
                moveTo(2.0f, 12.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, -1.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 0.0f, 2.0f)
                lineTo(3.0f, 13.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                close()
                moveTo(3.0f, 17.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, 2.0f)
                horizontalLineToRelative(8.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, -2.0f)
                lineTo(3.0f, 17.0f)
                close()
                moveTo(20.0f, 14.0f)
                verticalLineToRelative(3.59f)
                lineToRelative(0.3f, -0.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, 1.42f)
                lineToRelative(-2.0f, 2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, 0.0f)
                lineToRelative(-2.0f, -2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, -1.42f)
                lineToRelative(0.3f, 0.3f)
                lineTo(18.0f, 14.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 2.0f, 0.0f)
                close()
            }
        }
        return _textLineSpacing!!
    }

private var _textLineSpacing: ImageVector? = null
