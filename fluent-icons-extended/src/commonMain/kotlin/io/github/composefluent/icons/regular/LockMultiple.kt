

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.LockMultiple: ImageVector
    get() {
        if (_lockMultiple != null) {
            return _lockMultiple!!
        }
        _lockMultiple = fluentIcon(name = "Regular.LockMultiple") {
            fluentPath {
                moveTo(10.75f, 13.75f)
                arcToRelative(1.25f, 1.25f, 0.0f, true, false, 0.0f, -2.5f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, false, 0.0f, 2.5f)
                close()
                moveTo(7.25f, 5.5f)
                arcToRelative(3.5f, 3.5f, 0.0f, true, true, 7.0f, 0.0f)
                verticalLineToRelative(1.0f)
                horizontalLineToRelative(1.0f)
                curveToRelative(1.24f, 0.0f, 2.25f, 1.0f, 2.25f, 2.25f)
                verticalLineToRelative(7.5f)
                curveToRelative(0.0f, 1.24f, -1.0f, 2.25f, -2.25f, 2.25f)
                horizontalLineToRelative(-9.0f)
                curveToRelative(-1.24f, 0.0f, -2.25f, -1.0f, -2.25f, -2.25f)
                verticalLineToRelative(-7.5f)
                curveTo(4.0f, 7.51f, 5.0f, 6.5f, 6.25f, 6.5f)
                horizontalLineToRelative(1.0f)
                verticalLineToRelative(-1.0f)
                close()
                moveTo(12.75f, 5.5f)
                arcToRelative(2.0f, 2.0f, 0.0f, true, false, -4.0f, 0.0f)
                verticalLineToRelative(1.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-1.0f)
                close()
                moveTo(5.5f, 8.75f)
                verticalLineToRelative(7.5f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(9.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, -0.34f, 0.75f, -0.75f)
                verticalLineToRelative(-7.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                horizontalLineToRelative(-9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                close()
                moveTo(9.75f, 21.0f)
                curveToRelative(-0.98f, 0.0f, -1.81f, -0.63f, -2.12f, -1.5f)
                horizontalLineToRelative(8.12f)
                arcToRelative(2.75f, 2.75f, 0.0f, false, false, 2.75f, -2.75f)
                verticalLineToRelative(-6.62f)
                curveToRelative(0.87f, 0.3f, 1.5f, 1.14f, 1.5f, 2.12f)
                verticalLineToRelative(4.5f)
                curveTo(20.0f, 19.1f, 18.1f, 21.0f, 15.75f, 21.0f)
                horizontalLineToRelative(-6.0f)
                close()
            }
        }
        return _lockMultiple!!
    }

private var _lockMultiple: ImageVector? = null
