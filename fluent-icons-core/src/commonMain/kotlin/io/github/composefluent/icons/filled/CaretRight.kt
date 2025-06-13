

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CaretRight: ImageVector
    get() {
        if (_caretRight != null) {
            return _caretRight!!
        }
        _caretRight = fluentIcon(name = "Filled.CaretRight") {
            fluentPath {
                moveTo(9.0f, 17.9f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, false, 2.07f, 0.94f)
                lineToRelative(6.31f, -5.52f)
                curveToRelative(0.8f, -0.7f, 0.8f, -1.94f, 0.0f, -2.64f)
                lineToRelative(-6.3f, -5.52f)
                curveTo(10.25f, 4.46f, 9.0f, 5.03f, 9.0f, 6.1f)
                verticalLineToRelative(11.8f)
                close()
            }
        }
        return _caretRight!!
    }

private var _caretRight: ImageVector? = null
