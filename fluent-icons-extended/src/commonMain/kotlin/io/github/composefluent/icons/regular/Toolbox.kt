

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.Toolbox: ImageVector
    get() {
        if (_toolbox != null) {
            return _toolbox!!
        }
        _toolbox = fluentIcon(name = "Regular.Toolbox") {
            fluentPath {
                moveTo(7.0f, 6.25f)
                lineTo(7.0f, 7.0f)
                lineTo(4.25f, 7.0f)
                curveTo(3.01f, 7.0f, 2.0f, 8.0f, 2.0f, 9.25f)
                verticalLineToRelative(8.5f)
                curveTo(2.0f, 18.99f, 3.0f, 20.0f, 4.25f, 20.0f)
                horizontalLineToRelative(15.5f)
                curveToRelative(1.24f, 0.0f, 2.25f, -1.01f, 2.25f, -2.25f)
                verticalLineToRelative(-8.5f)
                curveTo(22.0f, 8.01f, 21.0f, 7.0f, 19.75f, 7.0f)
                lineTo(17.0f, 7.0f)
                verticalLineToRelative(-0.75f)
                curveTo(17.0f, 5.01f, 16.0f, 4.0f, 14.75f, 4.0f)
                horizontalLineToRelative(-5.5f)
                curveTo(8.01f, 4.0f, 7.0f, 5.01f, 7.0f, 6.25f)
                close()
                moveTo(9.25f, 5.5f)
                horizontalLineToRelative(5.5f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                lineTo(15.5f, 7.0f)
                horizontalLineToRelative(-7.0f)
                verticalLineToRelative(-0.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                close()
                moveTo(7.0f, 8.5f)
                horizontalLineToRelative(12.75f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                verticalLineToRelative(2.25f)
                horizontalLineToRelative(-3.0f)
                verticalLineToRelative(-0.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(0.75f)
                lineTo(8.0f, 11.5f)
                verticalLineToRelative(-0.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(0.75f)
                horizontalLineToRelative(-3.0f)
                lineTo(3.5f, 9.25f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                lineTo(7.0f, 8.5f)
                close()
                moveTo(16.0f, 13.0f)
                verticalLineToRelative(1.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                lineTo(17.5f, 13.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(4.75f)
                curveToRelative(0.0f, 0.41f, -0.34f, 0.75f, -0.75f, 0.75f)
                lineTo(4.25f, 18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.75f, -0.75f)
                lineTo(3.5f, 13.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(1.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                lineTo(8.0f, 13.0f)
                horizontalLineToRelative(8.0f)
                close()
            }
        }
        return _toolbox!!
    }

private var _toolbox: ImageVector? = null
