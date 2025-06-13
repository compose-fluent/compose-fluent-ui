

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.PortUsbC: ImageVector
    get() {
        if (_portUsbC != null) {
            return _portUsbC!!
        }
        _portUsbC = fluentIcon(name = "Filled.PortUsbC") {
            fluentPath {
                moveTo(19.0f, 12.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -3.0f, 3.0f)
                horizontalLineTo(8.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 0.0f, -6.0f)
                horizontalLineToRelative(8.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 3.0f, 3.0f)
                close()
            }
        }
        return _portUsbC!!
    }

private var _portUsbC: ImageVector? = null
