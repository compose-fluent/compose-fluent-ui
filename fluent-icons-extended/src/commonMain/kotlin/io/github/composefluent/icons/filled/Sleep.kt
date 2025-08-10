

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Sleep: ImageVector
    get() {
        if (_sleep != null) {
            return _sleep!!
        }
        _sleep = fluentIcon(name = "Filled.Sleep") {
            fluentPath {
                moveTo(13.38f, 3.03f)
                arcToRelative(9.0f, 9.0f, 0.0f, true, true, -7.9f, 14.1f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, 0.32f, -1.02f)
                curveToRelative(3.4f, -1.21f, 5.21f, -2.62f, 6.27f, -4.63f)
                curveToRelative(1.1f, -2.12f, 1.4f, -4.44f, 0.62f, -7.62f)
                arcToRelative(0.68f, 0.68f, 0.0f, false, true, 0.69f, -0.83f)
                close()
                moveTo(10.99f, 3.65f)
                lineTo(11.0f, 3.75f)
                lineTo(11.0f, 9.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, true, -1.5f, -1.94f)
                verticalLineToRelative(-2.3f)
                lineTo(6.0f, 5.83f)
                lineTo(6.0f, 10.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, true, -1.5f, -1.94f)
                lineTo(4.5f, 5.27f)
                curveToRelative(0.0f, -0.3f, 0.17f, -0.55f, 0.43f, -0.68f)
                lineToRelative(0.1f, -0.03f)
                lineToRelative(5.0f, -1.53f)
                curveToRelative(0.45f, -0.13f, 0.9f, 0.17f, 0.96f, 0.62f)
                close()
            }
        }
        return _sleep!!
    }

private var _sleep: ImageVector? = null
