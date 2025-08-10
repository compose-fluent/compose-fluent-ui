

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextDirectionRotate90Rtl: ImageVector
    get() {
        if (_textDirectionRotate90Rtl != null) {
            return _textDirectionRotate90Rtl!!
        }
        _textDirectionRotate90Rtl = fluentIcon(name = "Filled.TextDirectionRotate90Rtl") {
            fluentPath {
                moveToRelative(20.38f, 15.33f)
                lineToRelative(-9.0f, -3.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, -0.76f, 1.84f)
                lineToRelative(2.38f, 1.0f)
                verticalLineToRelative(3.66f)
                lineToRelative(-2.38f, 1.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.76f, 1.84f)
                lineToRelative(9.0f, -3.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, -1.84f)
                close()
                moveTo(15.0f, 15.25f)
                lineToRelative(2.4f, 1.0f)
                lineToRelative(-2.4f, 1.0f)
                verticalLineToRelative(-2.0f)
                close()
                moveTo(8.0f, 20.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                lineTo(6.0f, 6.41f)
                lineToRelative(-0.3f, 0.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, -1.42f)
                lineToRelative(2.0f, -2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, 0.0f)
                lineToRelative(2.0f, 2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, 1.42f)
                lineTo(8.0f, 6.4f)
                lineTo(8.0f, 20.0f)
                close()
                moveTo(16.0f, 11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                lineTo(15.0f, 6.41f)
                lineToRelative(-0.3f, 0.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, -1.4f, -1.42f)
                lineToRelative(2.0f, -2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, 0.0f)
                lineToRelative(2.0f, 2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, 1.42f)
                lineToRelative(-0.3f, -0.3f)
                lineTo(17.0f, 10.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, 1.0f)
                close()
            }
        }
        return _textDirectionRotate90Rtl!!
    }

private var _textDirectionRotate90Rtl: ImageVector? = null
