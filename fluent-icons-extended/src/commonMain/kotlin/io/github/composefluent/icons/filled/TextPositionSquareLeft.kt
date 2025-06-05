

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextPositionSquareLeft: ImageVector
    get() {
        if (_textPositionSquareLeft != null) {
            return _textPositionSquareLeft!!
        }
        _textPositionSquareLeft = fluentIcon(name = "Filled.TextPositionSquareLeft") {
            fluentPath {
                moveTo(3.75f, 3.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, 2.0f)
                horizontalLineToRelative(16.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, -2.0f)
                lineTo(3.75f, 3.75f)
                close()
                moveTo(13.25f, 7.25f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, 2.0f)
                horizontalLineToRelative(7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, -2.0f)
                horizontalLineToRelative(-7.0f)
                close()
                moveTo(12.25f, 15.25f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, -1.0f)
                horizontalLineToRelative(7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 0.0f, 2.0f)
                horizontalLineToRelative(-7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                close()
                moveTo(13.25f, 10.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, 2.0f)
                horizontalLineToRelative(7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, -2.0f)
                horizontalLineToRelative(-7.0f)
                close()
                moveTo(2.75f, 18.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, -1.0f)
                horizontalLineToRelative(16.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 0.0f, 2.0f)
                lineTo(3.75f, 19.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                close()
                moveTo(4.75f, 11.0f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 4.5f, 0.0f)
                verticalLineToRelative(4.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 2.0f, 0.0f)
                lineTo(11.25f, 11.0f)
                arcToRelative(4.25f, 4.25f, 0.0f, false, false, -8.5f, 0.0f)
                verticalLineToRelative(4.75f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 2.0f, 0.0f)
                lineTo(4.75f, 11.0f)
                close()
            }
        }
        return _textPositionSquareLeft!!
    }

private var _textPositionSquareLeft: ImageVector? = null
