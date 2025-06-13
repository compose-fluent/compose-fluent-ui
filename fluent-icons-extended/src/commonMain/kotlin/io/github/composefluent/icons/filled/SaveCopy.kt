

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.SaveCopy: ImageVector
    get() {
        if (_saveCopy != null) {
            return _saveCopy!!
        }
        _saveCopy = fluentIcon(name = "Filled.SaveCopy") {
            fluentPath {
                moveTo(20.5f, 5.63f)
                curveToRelative(0.87f, 0.3f, 1.5f, 1.14f, 1.5f, 2.12f)
                verticalLineToRelative(10.0f)
                curveTo(22.0f, 20.1f, 20.1f, 22.0f, 17.75f, 22.0f)
                horizontalLineToRelative(-10.0f)
                curveToRelative(-0.98f, 0.0f, -1.82f, -0.63f, -2.12f, -1.5f)
                horizontalLineToRelative(12.12f)
                arcToRelative(2.75f, 2.75f, 0.0f, false, false, 2.75f, -2.75f)
                lineTo(20.5f, 5.63f)
                close()
                moveTo(17.25f, 2.0f)
                curveToRelative(1.24f, 0.0f, 2.25f, 1.0f, 2.25f, 2.25f)
                verticalLineToRelative(13.0f)
                curveToRelative(0.0f, 1.24f, -1.01f, 2.25f, -2.25f, 2.25f)
                horizontalLineToRelative(-13.0f)
                curveTo(3.01f, 19.5f, 2.0f, 18.49f, 2.0f, 17.25f)
                verticalLineToRelative(-13.0f)
                curveTo(2.0f, 3.01f, 3.0f, 2.0f, 4.25f, 2.0f)
                horizontalLineToRelative(13.0f)
                close()
                moveTo(10.75f, 6.75f)
                curveToRelative(-0.38f, 0.0f, -0.7f, 0.28f, -0.74f, 0.65f)
                lineToRelative(-0.01f, 0.1f)
                lineTo(10.0f, 10.0f)
                lineTo(7.5f, 10.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.1f, 1.5f)
                lineTo(10.0f, 11.5f)
                lineTo(10.0f, 14.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.1f)
                verticalLineToRelative(-2.6f)
                lineTo(14.0f, 11.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.1f, -1.5f)
                horizontalLineToRelative(-2.6f)
                lineTo(11.5f, 7.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                close()
            }
        }
        return _saveCopy!!
    }

private var _saveCopy: ImageVector? = null
