

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.MoreVertical: ImageVector
    get() {
        if (_moreVertical != null) {
            return _moreVertical!!
        }
        _moreVertical = fluentIcon(name = "Regular.MoreVertical") {
            fluentPath {
                moveTo(12.0f, 7.75f)
                arcToRelative(1.75f, 1.75f, 0.0f, true, true, 0.0f, -3.5f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, true, 0.0f, 3.5f)
                close()
                moveTo(12.0f, 13.75f)
                arcToRelative(1.75f, 1.75f, 0.0f, true, true, 0.0f, -3.5f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, true, 0.0f, 3.5f)
                close()
                moveTo(10.25f, 18.0f)
                arcToRelative(1.75f, 1.75f, 0.0f, true, false, 3.5f, 0.0f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, false, -3.5f, 0.0f)
                close()
            }
        }
        return _moreVertical!!
    }

private var _moreVertical: ImageVector? = null
