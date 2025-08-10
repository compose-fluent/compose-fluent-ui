

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Timer: ImageVector
    get() {
        if (_timer != null) {
            return _timer!!
        }
        _timer = fluentIcon(name = "Filled.Timer") {
            fluentPath {
                moveTo(12.0f, 5.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, true, true, 0.0f, 17.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, false, true, 0.0f, -17.0f)
                close()
                moveTo(12.0f, 8.0f)
                curveToRelative(-0.38f, 0.0f, -0.7f, 0.28f, -0.74f, 0.65f)
                lineToRelative(-0.01f, 0.1f)
                verticalLineToRelative(4.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                lineTo(12.75f, 8.65f)
                arcTo(0.75f, 0.75f, 0.0f, false, false, 12.0f, 8.0f)
                close()
                moveTo(19.17f, 5.12f)
                lineTo(19.25f, 5.18f)
                lineTo(20.4f, 6.18f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.9f, 1.2f)
                lineToRelative(-0.08f, -0.06f)
                lineToRelative(-1.15f, -1.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.9f, -1.2f)
                close()
                moveTo(14.25f, 2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                horizontalLineToRelative(-4.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(4.6f)
                close()
            }
        }
        return _timer!!
    }

private var _timer: ImageVector? = null
