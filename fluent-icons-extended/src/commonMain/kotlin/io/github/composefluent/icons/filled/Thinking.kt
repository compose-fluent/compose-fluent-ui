

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Thinking: ImageVector
    get() {
        if (_thinking != null) {
            return _thinking!!
        }
        _thinking = fluentIcon(name = "Filled.Thinking") {
            fluentPath {
                moveTo(4.0f, 18.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, true, 0.0f, 4.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, 0.0f, -4.0f)
                close()
                moveTo(9.5f, 15.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, 0.0f, 5.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, false, true, 0.0f, -5.0f)
                close()
                moveTo(12.0f, 2.0f)
                arcToRelative(5.41f, 5.41f, 0.0f, false, true, 5.33f, 4.47f)
                horizontalLineToRelative(0.08f)
                arcToRelative(3.76f, 3.76f, 0.0f, true, true, 0.0f, 7.53f)
                lineTo(6.6f, 14.0f)
                arcToRelative(3.76f, 3.76f, 0.0f, true, true, 0.0f, -7.53f)
                horizontalLineToRelative(0.08f)
                arcTo(5.41f, 5.41f, 0.0f, false, true, 12.0f, 2.0f)
                close()
            }
        }
        return _thinking!!
    }

private var _thinking: ImageVector? = null
