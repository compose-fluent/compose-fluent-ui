

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ChevronRight: ImageVector
    get() {
        if (_chevronRight != null) {
            return _chevronRight!!
        }
        _chevronRight = fluentIcon(name = "Filled.ChevronRight") {
            fluentPath {
                moveTo(8.3f, 4.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, 1.4f)
                lineToRelative(6.29f, 6.3f)
                lineToRelative(-6.3f, 6.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 1.42f, 1.4f)
                lineToRelative(7.0f, -7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, -1.4f)
                lineToRelative(-7.0f, -7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.42f, 0.0f)
                close()
            }
        }
        return _chevronRight!!
    }

private var _chevronRight: ImageVector? = null
