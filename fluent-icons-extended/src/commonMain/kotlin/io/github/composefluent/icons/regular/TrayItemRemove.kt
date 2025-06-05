

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TrayItemRemove: ImageVector
    get() {
        if (_trayItemRemove != null) {
            return _trayItemRemove!!
        }
        _trayItemRemove = fluentIcon(name = "Regular.TrayItemRemove") {
            fluentPath {
                moveTo(14.0f, 3.5f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(3.5f)
                verticalLineToRelative(-2.0f)
                lineTo(14.0f, 3.5f)
                close()
                moveTo(12.5f, 3.25f)
                curveToRelative(0.0f, -0.69f, 0.56f, -1.25f, 1.25f, -1.25f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.69f, 0.0f, 1.25f, 0.56f, 1.25f, 1.25f)
                verticalLineToRelative(2.5f)
                curveTo(19.0f, 6.44f, 18.44f, 7.0f, 17.75f, 7.0f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.69f, 0.0f, -1.25f, -0.56f, -1.25f, -1.25f)
                verticalLineToRelative(-2.5f)
                close()
                moveTo(15.22f, 8.72f)
                curveToRelative(0.3f, -0.3f, 0.77f, -0.3f, 1.06f, 0.0f)
                lineToRelative(2.5f, 2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.06f, 1.06f)
                lineToRelative(-1.22f, -1.22f)
                verticalLineToRelative(6.69f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.5f, 0.0f)
                verticalLineToRelative(-6.69f)
                lineToRelative(-1.22f, 1.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.06f, -1.06f)
                lineToRelative(2.5f, -2.5f)
                close()
                moveTo(6.5f, 15.0f)
                verticalLineToRelative(2.0f)
                lineTo(10.0f, 17.0f)
                verticalLineToRelative(-2.0f)
                lineTo(6.5f, 15.0f)
                close()
                moveTo(5.0f, 14.75f)
                curveToRelative(0.0f, -0.69f, 0.56f, -1.25f, 1.25f, -1.25f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.69f, 0.0f, 1.25f, 0.56f, 1.25f, 1.25f)
                verticalLineToRelative(2.5f)
                curveToRelative(0.0f, 0.69f, -0.56f, 1.25f, -1.25f, 1.25f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.69f, 0.0f, -1.25f, -0.56f, -1.25f, -1.25f)
                verticalLineToRelative(-2.5f)
                close()
                moveTo(2.75f, 16.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.69f, 0.56f, 1.25f, 1.25f, 1.25f)
                horizontalLineToRelative(14.5f)
                curveToRelative(0.69f, 0.0f, 1.25f, -0.56f, 1.25f, -1.25f)
                verticalLineToRelative(-2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                verticalLineToRelative(2.0f)
                arcToRelative(2.75f, 2.75f, 0.0f, false, true, -2.75f, 2.75f)
                lineTo(4.75f, 21.5f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 2.0f, 18.75f)
                verticalLineToRelative(-2.0f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                close()
            }
        }
        return _trayItemRemove!!
    }

private var _trayItemRemove: ImageVector? = null
