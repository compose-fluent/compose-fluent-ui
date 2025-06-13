

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Flag: ImageVector
    get() {
        if (_flag != null) {
            return _flag!!
        }
        _flag = fluentIcon(name = "Filled.Flag") {
            fluentPath {
                moveTo(3.0f, 3.75f)
                curveToRelative(0.0f, -0.42f, 0.34f, -0.75f, 0.75f, -0.75f)
                horizontalLineToRelative(16.5f)
                curveToRelative(0.62f, 0.0f, 0.98f, 0.7f, 0.6f, 1.2f)
                lineTo(16.7f, 9.75f)
                lineToRelative(4.16f, 5.55f)
                curveToRelative(0.38f, 0.5f, 0.02f, 1.2f, -0.6f, 1.2f)
                horizontalLineTo(4.5f)
                verticalLineToRelative(4.75f)
                curveToRelative(0.0f, 0.38f, -0.28f, 0.7f, -0.65f, 0.74f)
                lineToRelative(-0.1f, 0.01f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.74f, -0.65f)
                lineToRelative(-0.01f, -0.1f)
                verticalLineTo(3.75f)
                close()
            }
        }
        return _flag!!
    }

private var _flag: ImageVector? = null
