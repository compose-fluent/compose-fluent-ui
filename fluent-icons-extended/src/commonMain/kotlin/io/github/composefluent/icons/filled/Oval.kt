

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Oval: ImageVector
    get() {
        if (_oval != null) {
            return _oval!!
        }
        _oval = fluentIcon(name = "Filled.Oval") {
            fluentPath {
                moveTo(2.0f, 12.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, false, true, 8.0f, -8.0f)
                horizontalLineToRelative(4.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, true, true, 0.0f, 16.0f)
                horizontalLineToRelative(-4.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, false, true, -8.0f, -8.0f)
                close()
            }
        }
        return _oval!!
    }

private var _oval: ImageVector? = null
