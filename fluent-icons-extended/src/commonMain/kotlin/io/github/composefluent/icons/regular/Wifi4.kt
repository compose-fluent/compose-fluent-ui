

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.Wifi4: ImageVector
    get() {
        if (_wifi4 != null) {
            return _wifi4!!
        }
        _wifi4 = fluentIcon(name = "Regular.Wifi4") {
            fluentPath {
                moveTo(10.94f, 18.56f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, false, 2.12f, -2.12f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, -2.12f, 2.12f)
                close()
            }
        }
        return _wifi4!!
    }

private var _wifi4: ImageVector? = null
