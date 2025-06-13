

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.PresenceDnd: ImageVector
    get() {
        if (_presenceDnd != null) {
            return _presenceDnd!!
        }
        _presenceDnd = fluentIcon(name = "Filled.PresenceDnd") {
            fluentPath {
                moveTo(12.0f, 24.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, true, false, 0.0f, -24.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, false, false, 0.0f, 24.0f)
                close()
                moveTo(7.5f, 10.5f)
                horizontalLineToRelative(9.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, 0.0f, 3.0f)
                horizontalLineToRelative(-9.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, 0.0f, -3.0f)
                close()
            }
        }
        return _presenceDnd!!
    }

private var _presenceDnd: ImageVector? = null
