

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.Spacebar: ImageVector
    get() {
        if (_spacebar != null) {
            return _spacebar!!
        }
        _spacebar = fluentIcon(name = "Regular.Spacebar") {
            fluentPath {
                moveTo(20.5f, 11.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.14f, -0.11f, 0.25f, -0.25f, 0.25f)
                horizontalLineTo(3.75f)
                arcTo(0.25f, 0.25f, 0.0f, false, true, 3.5f, 13.0f)
                verticalLineToRelative(-2.0f)
                arcTo(0.75f, 0.75f, 0.0f, false, false, 2.0f, 11.0f)
                verticalLineToRelative(2.0f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(16.5f)
                curveToRelative(0.97f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                verticalLineToRelative(-2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                close()
            }
        }
        return _spacebar!!
    }

private var _spacebar: ImageVector? = null
