

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ArrowDown: ImageVector
    get() {
        if (_arrowDown != null) {
            return _arrowDown!!
        }
        _arrowDown = fluentIcon(name = "Regular.ArrowDown") {
            fluentPath {
                moveTo(19.8f, 13.27f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.1f, -1.04f)
                lineToRelative(-5.95f, 6.25f)
                verticalLineTo(3.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.5f, 0.0f)
                verticalLineToRelative(14.73f)
                lineTo(5.3f, 12.23f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.1f, 1.04f)
                lineToRelative(7.08f, 7.42f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 1.44f, 0.0f)
                lineToRelative(7.07f, -7.42f)
                close()
            }
        }
        return _arrowDown!!
    }

private var _arrowDown: ImageVector? = null
