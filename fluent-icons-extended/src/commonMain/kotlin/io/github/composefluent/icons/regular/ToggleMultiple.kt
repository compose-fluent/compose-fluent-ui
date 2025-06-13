

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ToggleMultiple: ImageVector
    get() {
        if (_toggleMultiple != null) {
            return _toggleMultiple!!
        }
        _toggleMultiple = fluentIcon(name = "Regular.ToggleMultiple") {
            fluentPath {
                moveTo(6.5f, 8.5f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, false, 0.0f, -4.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 0.0f, 4.0f)
                close()
                moveTo(6.5f, 2.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, 0.0f, 9.0f)
                horizontalLineToRelative(-11.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, 0.0f, -9.0f)
                close()
                moveTo(17.5f, 3.5f)
                horizontalLineToRelative(-11.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, 0.0f, 6.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, -6.0f)
                close()
                moveTo(17.5f, 19.5f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, false, 0.0f, -4.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 0.0f, 4.0f)
                close()
                moveTo(2.0f, 17.5f)
                arcTo(4.5f, 4.5f, 0.0f, false, true, 6.5f, 13.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, true, 0.0f, 9.0f)
                horizontalLineToRelative(-11.0f)
                arcTo(4.5f, 4.5f, 0.0f, false, true, 2.0f, 17.5f)
                close()
                moveTo(6.5f, 14.5f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, 6.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, -6.0f)
                horizontalLineToRelative(-11.0f)
                close()
            }
        }
        return _toggleMultiple!!
    }

private var _toggleMultiple: ImageVector? = null
