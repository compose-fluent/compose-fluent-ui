

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.DocumentFooter: ImageVector
    get() {
        if (_documentFooter != null) {
            return _documentFooter!!
        }
        _documentFooter = fluentIcon(name = "Regular.DocumentFooter") {
            fluentPath {
                moveTo(8.5f, 16.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, false, 0.0f, 3.0f)
                horizontalLineToRelative(7.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 0.0f, -3.0f)
                horizontalLineToRelative(-7.0f)
                close()
                moveTo(20.0f, 4.1f)
                arcTo(2.25f, 2.25f, 0.0f, false, false, 17.74f, 2.0f)
                horizontalLineTo(6.09f)
                arcTo(2.25f, 2.25f, 0.0f, false, false, 4.0f, 4.25f)
                verticalLineToRelative(15.5f)
                lineToRelative(0.01f, 0.15f)
                arcTo(2.25f, 2.25f, 0.0f, false, false, 6.25f, 22.0f)
                horizontalLineToRelative(11.5f)
                lineToRelative(0.15f, -0.01f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, false, 2.1f, -2.25f)
                verticalLineTo(4.1f)
                close()
                moveTo(6.24f, 3.5f)
                horizontalLineToRelative(11.6f)
                curveToRelative(0.36f, 0.05f, 0.65f, 0.37f, 0.65f, 0.75f)
                verticalLineToRelative(15.5f)
                lineToRelative(-0.01f, 0.1f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.74f, 0.64f)
                horizontalLineTo(6.15f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.65f, -0.75f)
                verticalLineTo(4.14f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.75f, -0.64f)
                close()
            }
        }
        return _documentFooter!!
    }

private var _documentFooter: ImageVector? = null
