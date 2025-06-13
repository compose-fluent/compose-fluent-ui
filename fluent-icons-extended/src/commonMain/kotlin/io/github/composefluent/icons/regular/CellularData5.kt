

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.CellularData5: ImageVector
    get() {
        if (_cellularData5 != null) {
            return _cellularData5!!
        }
        _cellularData5 = fluentIcon(name = "Regular.CellularData5") {
            fluentPath {
                moveTo(3.0f, 17.75f)
                verticalLineToRelative(1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                verticalLineToRelative(-1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                close()
            }
        }
        return _cellularData5!!
    }

private var _cellularData5: ImageVector? = null
