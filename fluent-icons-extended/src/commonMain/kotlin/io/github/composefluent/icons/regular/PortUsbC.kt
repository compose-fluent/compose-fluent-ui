

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.PortUsbC: ImageVector
    get() {
        if (_portUsbC != null) {
            return _portUsbC!!
        }
        _portUsbC = fluentIcon(name = "Regular.PortUsbC") {
            fluentPath {
                moveTo(16.0f, 10.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, true, 0.0f, 3.0f)
                horizontalLineTo(8.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, true, 0.0f, -3.0f)
                horizontalLineToRelative(8.0f)
                close()
                moveTo(16.0f, 9.0f)
                horizontalLineTo(8.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, 6.0f)
                horizontalLineToRelative(8.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, 0.0f, -6.0f)
                close()
            }
        }
        return _portUsbC!!
    }

private var _portUsbC: ImageVector? = null
