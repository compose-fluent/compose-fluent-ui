

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.AlignRight: ImageVector
    get() {
        if (_alignRight != null) {
            return _alignRight!!
        }
        _alignRight = fluentIcon(name = "Filled.AlignRight") {
            fluentPath {
                moveTo(21.0f, 2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                lineTo(21.0f, 2.75f)
                close()
                moveTo(15.75f, 4.0f)
                curveTo(16.99f, 4.0f, 18.0f, 5.0f, 18.0f, 6.25f)
                verticalLineToRelative(2.5f)
                curveTo(18.0f, 9.99f, 17.0f, 11.0f, 15.75f, 11.0f)
                lineTo(5.25f, 11.0f)
                curveTo(4.01f, 11.0f, 3.0f, 10.0f, 3.0f, 8.75f)
                verticalLineToRelative(-2.5f)
                curveTo(3.0f, 5.01f, 4.0f, 4.0f, 5.25f, 4.0f)
                horizontalLineToRelative(10.5f)
                close()
                moveTo(15.75f, 13.0f)
                curveToRelative(1.24f, 0.0f, 2.25f, 1.0f, 2.25f, 2.25f)
                verticalLineToRelative(2.5f)
                curveToRelative(0.0f, 1.24f, -1.0f, 2.25f, -2.25f, 2.25f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(-1.24f, 0.0f, -2.25f, -1.0f, -2.25f, -2.25f)
                verticalLineToRelative(-2.5f)
                curveToRelative(0.0f, -1.24f, 1.0f, -2.25f, 2.25f, -2.25f)
                horizontalLineToRelative(7.0f)
                close()
            }
        }
        return _alignRight!!
    }

private var _alignRight: ImageVector? = null
