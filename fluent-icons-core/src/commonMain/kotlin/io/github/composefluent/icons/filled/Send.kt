

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Send: ImageVector
    get() {
        if (_send != null) {
            return _send!!
        }
        _send = fluentIcon(name = "Filled.Send") {
            fluentPath {
                moveToRelative(12.81f, 12.2f)
                lineToRelative(-7.53f, 1.25f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.38f, 0.32f)
                lineToRelative(-2.6f, 6.96f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.03f, 0.94f)
                lineToRelative(18.0f, -9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.34f)
                lineToRelative(-18.0f, -9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.03f, 0.94f)
                lineToRelative(2.6f, 6.96f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.38f, 0.32f)
                lineToRelative(7.53f, 1.25f)
                arcToRelative(0.2f, 0.2f, 0.0f, false, true, 0.0f, 0.4f)
                close()
            }
        }
        return _send!!
    }

private var _send: ImageVector? = null
