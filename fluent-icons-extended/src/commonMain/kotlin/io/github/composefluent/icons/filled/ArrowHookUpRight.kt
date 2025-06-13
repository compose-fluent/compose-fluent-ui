

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ArrowHookUpRight: ImageVector
    get() {
        if (_arrowHookUpRight != null) {
            return _arrowHookUpRight!!
        }
        _arrowHookUpRight = fluentIcon(name = "Filled.ArrowHookUpRight") {
            fluentPath {
                moveTo(10.5f, 18.0f)
                horizontalLineTo(16.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 0.0f, 2.0f)
                horizontalLineToRelative(-5.5f)
                arcToRelative(6.5f, 6.5f, 0.0f, true, true, 0.0f, -13.0f)
                horizontalLineToRelative(5.14f)
                lineToRelative(-1.93f, -1.93f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.41f, -1.42f)
                lineToRelative(3.53f, 3.53f)
                curveToRelative(0.1f, 0.1f, 0.16f, 0.2f, 0.21f, 0.31f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.18f, 1.24f)
                lineToRelative(-0.03f, 0.03f)
                lineToRelative(-3.53f, 3.53f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.41f, -1.41f)
                lineTo(15.59f, 9.0f)
                horizontalLineTo(10.5f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, false, 0.0f, 9.0f)
                close()
            }
        }
        return _arrowHookUpRight!!
    }

private var _arrowHookUpRight: ImageVector? = null
