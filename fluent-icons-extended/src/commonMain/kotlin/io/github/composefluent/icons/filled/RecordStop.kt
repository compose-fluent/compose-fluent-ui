

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.RecordStop: ImageVector
    get() {
        if (_recordStop != null) {
            return _recordStop!!
        }
        _recordStop = fluentIcon(name = "Filled.RecordStop") {
            fluentPath {
                moveTo(12.0f, 22.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, false, 0.0f, -20.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, 0.0f, 20.0f)
                close()
                moveTo(9.5f, 8.0f)
                horizontalLineToRelative(5.0f)
                curveToRelative(0.83f, 0.0f, 1.5f, 0.67f, 1.5f, 1.5f)
                verticalLineToRelative(5.0f)
                curveToRelative(0.0f, 0.83f, -0.67f, 1.5f, -1.5f, 1.5f)
                horizontalLineToRelative(-5.0f)
                arcTo(1.5f, 1.5f, 0.0f, false, true, 8.0f, 14.5f)
                verticalLineToRelative(-5.0f)
                curveTo(8.0f, 8.67f, 8.67f, 8.0f, 9.5f, 8.0f)
                close()
            }
        }
        return _recordStop!!
    }

private var _recordStop: ImageVector? = null
