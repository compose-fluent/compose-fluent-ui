

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.VideoPersonOff: ImageVector
    get() {
        if (_videoPersonOff != null) {
            return _videoPersonOff!!
        }
        _videoPersonOff = fluentIcon(name = "Filled.VideoPersonOff") {
            fluentPath {
                moveTo(2.22f, 2.22f)
                curveToRelative(0.3f, -0.3f, 0.77f, -0.3f, 1.06f, 0.0f)
                lineToRelative(18.5f, 18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.06f, 1.06f)
                lineTo(18.94f, 20.0f)
                lineTo(4.25f, 20.0f)
                curveTo(3.01f, 20.0f, 2.0f, 19.0f, 2.0f, 17.75f)
                lineTo(2.0f, 6.25f)
                curveToRelative(0.0f, -0.86f, 0.49f, -1.61f, 1.2f, -1.99f)
                lineToRelative(-0.98f, -0.98f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, -1.06f)
                close()
                moveTo(11.94f, 13.0f)
                arcTo(3.0f, 3.0f, 0.0f, false, true, 9.0f, 10.06f)
                lineTo(4.44f, 5.5f)
                horizontalLineToRelative(-0.19f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                verticalLineToRelative(11.5f)
                curveToRelative(0.0f, 0.42f, 0.34f, 0.75f, 0.75f, 0.75f)
                lineTo(7.0f, 18.5f)
                verticalLineToRelative(-2.75f)
                curveToRelative(0.0f, -0.92f, 0.7f, -1.67f, 1.6f, -1.74f)
                lineToRelative(0.15f, -0.01f)
                horizontalLineToRelative(4.19f)
                lineToRelative(-1.0f, -1.0f)
                close()
                moveTo(14.63f, 11.45f)
                lineTo(10.55f, 7.37f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 4.08f, 4.08f)
                close()
                moveTo(20.5f, 17.31f)
                lineTo(20.5f, 6.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                lineTo(8.68f, 5.5f)
                lineTo(7.18f, 4.0f)
                horizontalLineToRelative(12.57f)
                curveTo(20.99f, 4.0f, 22.0f, 5.0f, 22.0f, 6.25f)
                verticalLineToRelative(11.5f)
                curveToRelative(0.0f, 0.32f, -0.07f, 0.61f, -0.18f, 0.89f)
                lineTo(20.5f, 17.3f)
                close()
            }
        }
        return _videoPersonOff!!
    }

private var _videoPersonOff: ImageVector? = null
