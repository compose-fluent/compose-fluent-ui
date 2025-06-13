

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.HomeCheckmark: ImageVector
    get() {
        if (_homeCheckmark != null) {
            return _homeCheckmark!!
        }
        _homeCheckmark = fluentIcon(name = "Filled.HomeCheckmark") {
            fluentPath {
                moveTo(13.45f, 2.53f)
                curveToRelative(-0.84f, -0.7f, -2.06f, -0.7f, -2.9f, 0.0f)
                lineTo(3.8f, 8.23f)
                curveToRelative(-0.5f, 0.43f, -0.8f, 1.05f, -0.8f, 1.72f)
                verticalLineToRelative(9.3f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(14.5f)
                curveToRelative(0.97f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                verticalLineToRelative(-9.3f)
                curveToRelative(0.0f, -0.67f, -0.3f, -1.3f, -0.8f, -1.72f)
                lineToRelative(-6.75f, -5.7f)
                close()
                moveTo(15.78f, 11.28f)
                lineTo(11.28f, 15.78f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.06f, 0.0f)
                lineToRelative(-2.0f, -2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.06f, -1.06f)
                lineToRelative(1.47f, 1.47f)
                lineToRelative(3.97f, -3.97f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, 1.06f, 1.06f)
                close()
            }
        }
        return _homeCheckmark!!
    }

private var _homeCheckmark: ImageVector? = null
