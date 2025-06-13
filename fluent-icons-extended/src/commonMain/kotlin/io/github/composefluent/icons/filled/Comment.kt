

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Comment: ImageVector
    get() {
        if (_comment != null) {
            return _comment!!
        }
        _comment = fluentIcon(name = "Filled.Comment") {
            fluentPath {
                moveTo(5.25f, 3.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, false, 2.0f, 6.25f)
                verticalLineToRelative(8.5f)
                curveTo(2.0f, 16.55f, 3.46f, 18.0f, 5.25f, 18.0f)
                horizontalLineTo(6.0f)
                verticalLineToRelative(2.75f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, false, 2.0f, 1.0f)
                lineTo(13.0f, 18.0f)
                horizontalLineToRelative(5.75f)
                curveToRelative(1.8f, 0.0f, 3.25f, -1.46f, 3.25f, -3.25f)
                verticalLineToRelative(-8.5f)
                curveTo(22.0f, 4.45f, 20.54f, 3.0f, 18.75f, 3.0f)
                horizontalLineTo(5.25f)
                close()
            }
        }
        return _comment!!
    }

private var _comment: ImageVector? = null
