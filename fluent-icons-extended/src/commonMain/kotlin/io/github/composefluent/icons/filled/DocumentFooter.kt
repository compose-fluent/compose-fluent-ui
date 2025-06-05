

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DocumentFooter: ImageVector
    get() {
        if (_documentFooter != null) {
            return _documentFooter!!
        }
        _documentFooter = fluentIcon(name = "Filled.DocumentFooter") {
            fluentPath {
                moveTo(17.75f, 2.0f)
                curveToRelative(1.19f, 0.0f, 2.16f, 0.92f, 2.24f, 2.1f)
                verticalLineToRelative(15.65f)
                curveToRelative(0.0f, 1.18f, -0.92f, 2.16f, -2.09f, 2.24f)
                horizontalLineTo(6.25f)
                curveToRelative(-1.2f, 0.0f, -2.17f, -0.92f, -2.25f, -2.1f)
                verticalLineTo(4.25f)
                curveTo(4.0f, 3.07f, 4.92f, 2.09f, 6.1f, 2.0f)
                horizontalLineToRelative(11.65f)
                close()
                moveTo(8.5f, 16.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 0.0f, 3.0f)
                horizontalLineToRelative(7.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 0.0f, -3.0f)
                horizontalLineToRelative(-7.0f)
                close()
            }
        }
        return _documentFooter!!
    }

private var _documentFooter: ImageVector? = null
