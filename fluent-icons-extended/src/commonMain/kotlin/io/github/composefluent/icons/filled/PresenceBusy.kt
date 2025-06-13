

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.PresenceBusy: ImageVector
    get() {
        if (_presenceBusy != null) {
            return _presenceBusy!!
        }
        _presenceBusy = fluentIcon(name = "Filled.PresenceBusy") {
            fluentPath {
                moveTo(24.0f, 12.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, true, true, -24.0f, 0.0f)
                arcToRelative(12.0f, 12.0f, 0.0f, false, true, 24.0f, 0.0f)
                close()
            }
        }
        return _presenceBusy!!
    }

private var _presenceBusy: ImageVector? = null
