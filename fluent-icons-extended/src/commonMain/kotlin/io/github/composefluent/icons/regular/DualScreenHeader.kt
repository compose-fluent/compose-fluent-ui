

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.DualScreenHeader: ImageVector
    get() {
        if (_dualScreenHeader != null) {
            return _dualScreenHeader!!
        }
        _dualScreenHeader = fluentIcon(name = "Regular.DualScreenHeader") {
            fluentPath {
                moveTo(12.75f, 4.0f)
                horizontalLineToRelative(7.5f)
                curveTo(21.2f, 4.0f, 22.0f, 4.8f, 22.0f, 5.75f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.97f, -0.79f, 1.75f, -1.75f, 1.75f)
                lineTo(3.75f, 20.0f)
                curveTo(2.78f, 20.0f, 2.0f, 19.2f, 2.0f, 18.25f)
                lineTo(2.0f, 5.75f)
                curveTo(2.0f, 4.8f, 2.78f, 4.0f, 3.75f, 4.0f)
                horizontalLineToRelative(9.0f)
                close()
                moveTo(20.5f, 7.0f)
                horizontalLineToRelative(-7.75f)
                verticalLineToRelative(11.5f)
                horizontalLineToRelative(7.5f)
                curveToRelative(0.13f, 0.0f, 0.25f, -0.11f, 0.25f, -0.25f)
                lineTo(20.5f, 7.0f)
                close()
                moveTo(11.25f, 7.0f)
                lineTo(3.5f, 7.0f)
                verticalLineToRelative(11.25f)
                curveToRelative(0.0f, 0.14f, 0.11f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(7.5f)
                lineTo(11.25f, 7.0f)
                close()
            }
        }
        return _dualScreenHeader!!
    }

private var _dualScreenHeader: ImageVector? = null
