

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CloudArrowDown: ImageVector
    get() {
        if (_cloudArrowDown != null) {
            return _cloudArrowDown!!
        }
        _cloudArrowDown = fluentIcon(name = "Filled.CloudArrowDown") {
            fluentPath {
                moveTo(6.08f, 8.02f)
                arcToRelative(6.0f, 6.0f, 0.0f, false, true, 11.84f, 0.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, 4.05f, 4.97f)
                arcTo(6.5f, 6.5f, 0.0f, false, false, 10.02f, 17.0f)
                lineTo(6.5f, 17.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, -0.42f, -8.98f)
                close()
                moveTo(22.0f, 16.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 11.0f, 0.0f)
                close()
                moveTo(17.0f, 13.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -1.0f, 0.0f)
                verticalLineToRelative(4.8f)
                lineToRelative(-1.65f, -1.65f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.7f, 0.7f)
                lineToRelative(2.5f, 2.5f)
                curveToRelative(0.2f, 0.2f, 0.5f, 0.2f, 0.7f, 0.0f)
                lineToRelative(2.5f, -2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.7f, -0.7f)
                lineTo(17.0f, 18.29f)
                lineTo(17.0f, 13.5f)
                close()
            }
        }
        return _cloudArrowDown!!
    }

private var _cloudArrowDown: ImageVector? = null
