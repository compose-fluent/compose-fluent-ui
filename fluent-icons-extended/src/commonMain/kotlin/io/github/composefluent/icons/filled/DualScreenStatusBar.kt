

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DualScreenStatusBar: ImageVector
    get() {
        if (_dualScreenStatusBar != null) {
            return _dualScreenStatusBar!!
        }
        _dualScreenStatusBar = fluentIcon(name = "Filled.DualScreenStatusBar") {
            fluentPath {
                moveTo(20.25f, 4.0f)
                curveTo(21.2f, 4.0f, 22.0f, 4.8f, 22.0f, 5.75f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.97f, -0.79f, 1.75f, -1.75f, 1.75f)
                lineTo(13.0f, 20.0f)
                curveToRelative(-0.09f, 0.0f, -0.17f, 0.0f, -0.26f, -0.02f)
                lineTo(12.74f, 4.02f)
                lineTo(13.0f, 4.0f)
                horizontalLineToRelative(7.25f)
                close()
                moveTo(11.0f, 4.0f)
                curveToRelative(0.08f, 0.0f, 0.16f, 0.0f, 0.24f, 0.02f)
                verticalLineToRelative(15.96f)
                lineTo(11.0f, 20.0f)
                lineTo(3.75f, 20.0f)
                curveTo(2.78f, 20.0f, 2.0f, 19.2f, 2.0f, 18.25f)
                lineTo(2.0f, 5.75f)
                curveTo(2.0f, 4.8f, 2.78f, 4.0f, 3.75f, 4.0f)
                lineTo(11.0f, 4.0f)
                close()
                moveTo(19.75f, 6.5f)
                horizontalLineToRelative(-4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.1f, 1.5f)
                horizontalLineToRelative(4.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.1f, -1.5f)
                horizontalLineToRelative(-0.1f)
                close()
            }
        }
        return _dualScreenStatusBar!!
    }

private var _dualScreenStatusBar: ImageVector? = null
