

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TabDesktop: ImageVector
    get() {
        if (_tabDesktop != null) {
            return _tabDesktop!!
        }
        _tabDesktop = fluentIcon(name = "Filled.TabDesktop") {
            fluentPath {
                moveTo(11.0f, 3.0f)
                lineTo(6.0f, 3.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -3.0f, 3.0f)
                verticalLineToRelative(12.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, 3.0f, 3.0f)
                horizontalLineToRelative(12.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, 3.0f, -3.0f)
                lineTo(21.0f, 8.0f)
                horizontalLineToRelative(-7.75f)
                curveTo(12.01f, 8.0f, 11.0f, 7.0f, 11.0f, 5.75f)
                lineTo(11.0f, 3.0f)
                close()
                moveTo(21.0f, 6.5f)
                lineTo(21.0f, 6.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -3.0f, -3.0f)
                horizontalLineToRelative(-5.5f)
                verticalLineToRelative(2.75f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                lineTo(21.0f, 6.5f)
                close()
            }
        }
        return _tabDesktop!!
    }

private var _tabDesktop: ImageVector? = null
