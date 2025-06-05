

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.AlignBottom: ImageVector
    get() {
        if (_alignBottom != null) {
            return _alignBottom!!
        }
        _alignBottom = fluentIcon(name = "Filled.AlignBottom") {
            fluentPath {
                moveTo(2.75f, 21.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, -1.5f)
                horizontalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                lineTo(2.75f, 21.0f)
                close()
                moveTo(4.0f, 15.75f)
                curveTo(4.0f, 16.99f, 5.0f, 18.0f, 6.25f, 18.0f)
                horizontalLineToRelative(2.5f)
                curveTo(9.99f, 18.0f, 11.0f, 17.0f, 11.0f, 15.75f)
                lineTo(11.0f, 5.25f)
                curveTo(11.0f, 4.01f, 10.0f, 3.0f, 8.75f, 3.0f)
                horizontalLineToRelative(-2.5f)
                curveTo(5.01f, 3.0f, 4.0f, 4.0f, 4.0f, 5.25f)
                verticalLineToRelative(10.5f)
                close()
                moveTo(13.0f, 15.75f)
                curveToRelative(0.0f, 1.24f, 1.0f, 2.25f, 2.25f, 2.25f)
                horizontalLineToRelative(2.5f)
                curveToRelative(1.24f, 0.0f, 2.25f, -1.0f, 2.25f, -2.25f)
                verticalLineToRelative(-7.0f)
                curveToRelative(0.0f, -1.24f, -1.0f, -2.25f, -2.25f, -2.25f)
                horizontalLineToRelative(-2.5f)
                curveToRelative(-1.24f, 0.0f, -2.25f, 1.0f, -2.25f, 2.25f)
                verticalLineToRelative(7.0f)
                close()
            }
        }
        return _alignBottom!!
    }

private var _alignBottom: ImageVector? = null
