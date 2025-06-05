

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DrawerAdd: ImageVector
    get() {
        if (_drawerAdd != null) {
            return _drawerAdd!!
        }
        _drawerAdd = fluentIcon(name = "Filled.DrawerAdd") {
            fluentPath {
                moveTo(22.0f, 6.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, false, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, false, 11.0f, 0.0f)
                close()
                moveTo(17.0f, 7.0f)
                verticalLineToRelative(2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, true, -1.0f, 0.0f)
                verticalLineTo(7.0f)
                horizontalLineToRelative(-2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.0f, -1.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(3.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 1.0f, 0.0f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.0f, 1.0f)
                horizontalLineTo(17.0f)
                close()
                moveTo(6.25f, 4.0f)
                horizontalLineToRelative(4.25f)
                curveToRelative(-0.2f, 0.47f, -0.34f, 0.98f, -0.42f, 1.5f)
                horizontalLineTo(6.25f)
                curveToRelative(-0.97f, 0.0f, -1.75f, 0.78f, -1.75f, 1.75f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(5.67f)
                curveToRelative(0.13f, 0.53f, 0.32f, 1.03f, 0.56f, 1.5f)
                horizontalLineTo(4.5f)
                verticalLineTo(14.0f)
                horizontalLineToRelative(4.56f)
                curveToRelative(0.38f, 0.0f, 0.69f, 0.31f, 0.69f, 0.7f)
                verticalLineToRelative(0.05f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, false, 4.5f, 0.0f)
                verticalLineToRelative(-0.06f)
                curveToRelative(0.0f, -0.38f, 0.31f, -0.69f, 0.7f, -0.69f)
                horizontalLineToRelative(4.55f)
                verticalLineToRelative(-1.73f)
                arcToRelative(6.52f, 6.52f, 0.0f, false, false, 1.5f, -1.08f)
                verticalLineToRelative(7.56f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                horizontalLineTo(6.25f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 3.0f, 18.75f)
                verticalLineTo(7.25f)
                curveTo(3.0f, 5.45f, 4.46f, 4.0f, 6.25f, 4.0f)
                close()
            }
        }
        return _drawerAdd!!
    }

private var _drawerAdd: ImageVector? = null
