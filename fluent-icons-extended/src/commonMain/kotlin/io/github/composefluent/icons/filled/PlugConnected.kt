

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.PlugConnected: ImageVector
    get() {
        if (_plugConnected != null) {
            return _plugConnected!!
        }
        _plugConnected = fluentIcon(name = "Filled.PlugConnected") {
            fluentPath {
                moveTo(19.49f, 5.57f)
                arcToRelative(5.97f, 5.97f, 0.0f, false, true, -1.9f, 8.96f)
                curveToRelative(-0.64f, 0.35f, -1.42f, 0.14f, -1.94f, -0.38f)
                lineToRelative(-5.8f, -5.8f)
                curveToRelative(-0.52f, -0.52f, -0.73f, -1.3f, -0.38f, -1.95f)
                arcToRelative(6.0f, 6.0f, 0.0f, false, true, 8.96f, -1.89f)
                lineToRelative(2.29f, -2.29f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, 1.06f, 1.06f)
                lineToRelative(-2.29f, 2.3f)
                close()
                moveTo(3.28f, 21.78f)
                lineToRelative(2.3f, -2.29f)
                arcToRelative(5.97f, 5.97f, 0.0f, false, false, 8.95f, -1.9f)
                curveToRelative(0.35f, -0.64f, 0.14f, -1.42f, -0.38f, -1.94f)
                lineToRelative(-5.8f, -5.8f)
                curveToRelative(-0.52f, -0.52f, -1.3f, -0.73f, -1.95f, -0.38f)
                arcToRelative(6.0f, 6.0f, 0.0f, false, false, -1.89f, 8.96f)
                lineToRelative(-2.29f, 2.29f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 1.06f, 1.06f)
                close()
            }
        }
        return _plugConnected!!
    }

private var _plugConnected: ImageVector? = null
