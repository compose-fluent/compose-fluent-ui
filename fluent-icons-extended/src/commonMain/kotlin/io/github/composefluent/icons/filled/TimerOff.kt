

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TimerOff: ImageVector
    get() {
        if (_timerOff != null) {
            return _timerOff!!
        }
        _timerOff = fluentIcon(name = "Filled.TimerOff") {
            fluentPath {
                moveTo(3.28f, 2.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.06f, 1.06f)
                lineToRelative(4.0f, 4.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, true, false, 12.01f, 12.01f)
                lineToRelative(2.49f, 2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.06f, -1.07f)
                lineTo(3.28f, 2.22f)
                close()
                moveTo(12.62f, 13.68f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.36f, -0.33f)
                lineToRelative(-0.01f, -0.1f)
                verticalLineToRelative(-0.94f)
                lineToRelative(1.37f, 1.37f)
                close()
                moveTo(12.75f, 8.75f)
                verticalLineToRelative(0.82f)
                lineToRelative(7.13f, 7.13f)
                arcTo(8.5f, 8.5f, 0.0f, false, false, 8.8f, 5.62f)
                lineToRelative(2.63f, 2.63f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.3f, 0.4f)
                lineToRelative(0.01f, 0.1f)
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
        return _timerOff!!
    }

private var _timerOff: ImageVector? = null
