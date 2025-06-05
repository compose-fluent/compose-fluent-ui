

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.EyeOff: ImageVector
    get() {
        if (_eyeOff != null) {
            return _eyeOff!!
        }
        _eyeOff = fluentIcon(name = "Regular.EyeOff") {
            fluentPath {
                moveTo(2.22f, 2.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.07f, 0.98f)
                lineToRelative(0.07f, 0.08f)
                lineToRelative(4.03f, 4.03f)
                arcToRelative(9.99f, 9.99f, 0.0f, false, false, -3.95f, 5.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.45f, 0.37f)
                arcToRelative(8.49f, 8.49f, 0.0f, false, true, 3.58f, -5.04f)
                lineToRelative(1.81f, 1.81f)
                arcTo(3.99f, 3.99f, 0.0f, false, false, 12.0f, 17.0f)
                curveToRelative(1.09f, 0.0f, 2.08f, -0.43f, 2.8f, -1.14f)
                lineToRelative(5.92f, 5.92f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.13f, -0.98f)
                lineToRelative(-0.07f, -0.08f)
                lineToRelative(-6.11f, -6.11f)
                lineToRelative(-1.2f, -1.2f)
                lineToRelative(-2.87f, -2.87f)
                lineToRelative(-2.88f, -2.88f)
                lineToRelative(-1.13f, -1.13f)
                lineToRelative(-4.31f, -4.31f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.06f, 0.0f)
                close()
                moveTo(10.2f, 11.27f)
                lineTo(13.74f, 14.8f)
                arcTo(2.5f, 2.5f, 0.0f, false, true, 9.5f, 13.0f)
                curveToRelative(0.0f, -0.67f, 0.27f, -1.28f, 0.7f, -1.73f)
                close()
                moveTo(12.0f, 5.5f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, -2.89f, 0.42f)
                lineToRelative(1.24f, 1.24f)
                arcToRelative(8.52f, 8.52f, 0.0f, false, true, 9.9f, 6.27f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.45f, -0.36f)
                arcTo(10.0f, 10.0f, 0.0f, false, false, 12.0f, 5.5f)
                close()
                moveTo(12.2f, 9.0f)
                lineTo(16.0f, 12.81f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, false, -3.8f, -3.8f)
                close()
            }
        }
        return _eyeOff!!
    }

private var _eyeOff: ImageVector? = null
