

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Rewind: ImageVector
    get() {
        if (_rewind != null) {
            return _rewind!!
        }
        _rewind = fluentIcon(name = "Filled.Rewind") {
            fluentPath {
                moveTo(10.03f, 4.36f)
                arcTo(1.5f, 1.5f, 0.0f, false, true, 12.5f, 5.5f)
                verticalLineToRelative(4.0f)
                lineToRelative(6.03f, -5.14f)
                arcTo(1.5f, 1.5f, 0.0f, false, true, 21.0f, 5.51f)
                lineTo(21.0f, 18.5f)
                curveToRelative(0.0f, 0.88f, -0.7f, 1.48f, -1.46f, 1.5f)
                horizontalLineToRelative(-0.09f)
                curveToRelative(-0.32f, 0.0f, -0.64f, -0.12f, -0.92f, -0.36f)
                lineToRelative(-6.03f, -5.13f)
                verticalLineToRelative(3.99f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, -2.47f, 1.14f)
                lineToRelative(-7.41f, -6.3f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, true, 0.0f, -2.67f)
                lineToRelative(7.41f, -6.3f)
                close()
                moveTo(2.62f, 10.66f)
                lineTo(3.11f, 11.24f)
                close()
            }
        }
        return _rewind!!
    }

private var _rewind: ImageVector? = null
