

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.DualScreen: ImageVector
    get() {
        if (_dualScreen != null) {
            return _dualScreen!!
        }
        _dualScreen = fluentIcon(name = "Regular.DualScreen") {
            fluentPath {
                moveTo(12.75f, 4.0f)
                horizontalLineToRelative(7.5f)
                curveTo(21.2f, 4.0f, 22.0f, 4.8f, 22.0f, 5.75f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.96f, -0.79f, 1.75f, -1.75f, 1.75f)
                lineTo(3.75f, 20.0f)
                curveTo(2.78f, 20.0f, 2.0f, 19.2f, 2.0f, 18.25f)
                lineTo(2.0f, 5.75f)
                curveTo(2.0f, 4.78f, 2.78f, 4.0f, 3.75f, 4.0f)
                horizontalLineToRelative(9.0f)
                close()
                moveTo(20.25f, 5.5f)
                horizontalLineToRelative(-7.5f)
                verticalLineToRelative(13.0f)
                horizontalLineToRelative(7.5f)
                curveToRelative(0.13f, 0.0f, 0.25f, -0.11f, 0.25f, -0.25f)
                lineTo(20.5f, 5.75f)
                arcToRelative(0.25f, 0.25f, 0.0f, false, false, -0.25f, -0.25f)
                close()
                moveTo(11.25f, 5.5f)
                horizontalLineToRelative(-7.5f)
                arcToRelative(0.25f, 0.25f, 0.0f, false, false, -0.25f, 0.25f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.13f, 0.11f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(7.5f)
                verticalLineToRelative(-13.0f)
                close()
                moveTo(14.74f, 15.5f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                horizontalLineToRelative(-1.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(1.6f)
                horizontalLineToRelative(-1.5f)
                close()
                moveTo(7.74f, 15.5f)
                horizontalLineToRelative(1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                horizontalLineToRelative(-1.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(1.6f)
                horizontalLineToRelative(-1.5f)
                close()
            }
        }
        return _dualScreen!!
    }

private var _dualScreen: ImageVector? = null
