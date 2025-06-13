

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ArrowRight: ImageVector
    get() {
        if (_arrowRight != null) {
            return _arrowRight!!
        }
        _arrowRight = fluentIcon(name = "Regular.ArrowRight") {
            fluentPath {
                moveTo(13.27f, 4.2f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.04f, 1.1f)
                lineToRelative(6.25f, 5.95f)
                horizontalLineTo(3.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, 1.5f)
                horizontalLineToRelative(14.73f)
                lineToRelative(-6.25f, 5.95f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.04f, 1.1f)
                lineToRelative(7.42f, -7.08f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, -1.44f)
                lineTo(13.27f, 4.2f)
                close()
            }
        }
        return _arrowRight!!
    }

private var _arrowRight: ImageVector? = null
