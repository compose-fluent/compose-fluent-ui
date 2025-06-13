

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Pause: ImageVector
    get() {
        if (_pause != null) {
            return _pause!!
        }
        _pause = fluentIcon(name = "Filled.Pause") {
            fluentPath {
                moveTo(5.75f, 3.0f)
                curveTo(4.78f, 3.0f, 4.0f, 3.78f, 4.0f, 4.75f)
                verticalLineToRelative(14.5f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(3.5f)
                curveToRelative(0.96f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                lineTo(11.0f, 4.75f)
                curveTo(11.0f, 3.78f, 10.2f, 3.0f, 9.25f, 3.0f)
                horizontalLineToRelative(-3.5f)
                close()
                moveTo(14.75f, 3.0f)
                curveTo(13.78f, 3.0f, 13.0f, 3.78f, 13.0f, 4.75f)
                verticalLineToRelative(14.5f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(3.5f)
                curveToRelative(0.96f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                lineTo(20.0f, 4.75f)
                curveTo(20.0f, 3.78f, 19.2f, 3.0f, 18.25f, 3.0f)
                horizontalLineToRelative(-3.5f)
                close()
            }
        }
        return _pause!!
    }

private var _pause: ImageVector? = null
