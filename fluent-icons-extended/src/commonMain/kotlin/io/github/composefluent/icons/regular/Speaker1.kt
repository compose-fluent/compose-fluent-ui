

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.Speaker1: ImageVector
    get() {
        if (_speaker1 != null) {
            return _speaker1!!
        }
        _speaker1 = fluentIcon(name = "Regular.Speaker1") {
            fluentPath {
                moveTo(14.7f, 3.44f)
                curveToRelative(0.2f, 0.23f, 0.3f, 0.51f, 0.3f, 0.81f)
                verticalLineToRelative(15.5f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, -2.06f, 0.96f)
                lineTo(7.98f, 16.5f)
                lineTo(4.25f, 16.5f)
                curveToRelative(-1.24f, 0.0f, -2.25f, -1.0f, -2.25f, -2.25f)
                verticalLineToRelative(-4.5f)
                curveTo(2.0f, 8.51f, 3.0f, 7.5f, 4.25f, 7.5f)
                horizontalLineToRelative(3.73f)
                lineToRelative(4.96f, -4.2f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, 1.76f, 0.14f)
                close()
                moveTo(13.5f, 4.8f)
                lineTo(8.52f, 9.0f)
                lineTo(4.25f, 9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                verticalLineToRelative(4.5f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(4.27f)
                lineToRelative(4.98f, 4.21f)
                lineTo(13.5f, 4.8f)
                close()
                moveTo(17.1f, 8.65f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.03f, 0.25f)
                curveToRelative(0.58f, 0.94f, 0.87f, 2.0f, 0.87f, 3.14f)
                curveToRelative(0.0f, 1.15f, -0.3f, 2.2f, -0.87f, 3.14f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.28f, -0.78f)
                curveToRelative(0.43f, -0.7f, 0.65f, -1.48f, 0.65f, -2.36f)
                curveToRelative(0.0f, -0.88f, -0.22f, -1.66f, -0.65f, -2.36f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.25f, -1.03f)
                close()
            }
        }
        return _speaker1!!
    }

private var _speaker1: ImageVector? = null
