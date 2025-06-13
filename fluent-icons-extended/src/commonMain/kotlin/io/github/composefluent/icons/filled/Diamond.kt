

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Diamond: ImageVector
    get() {
        if (_diamond != null) {
            return _diamond!!
        }
        _diamond = fluentIcon(name = "Filled.Diamond") {
            fluentPath {
                moveTo(2.66f, 13.6f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 0.0f, -3.19f)
                lineToRelative(7.75f, -7.75f)
                curveToRelative(0.88f, -0.88f, 2.3f, -0.88f, 3.18f, 0.0f)
                lineToRelative(7.75f, 7.75f)
                curveToRelative(0.88f, 0.88f, 0.88f, 2.3f, 0.0f, 3.18f)
                lineToRelative(-7.75f, 7.75f)
                curveToRelative(-0.88f, 0.88f, -2.3f, 0.88f, -3.18f, 0.0f)
                lineTo(2.66f, 13.6f)
                close()
            }
        }
        return _diamond!!
    }

private var _diamond: ImageVector? = null
