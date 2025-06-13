

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CircleLine: ImageVector
    get() {
        if (_circleLine != null) {
            return _circleLine!!
        }
        _circleLine = fluentIcon(name = "Filled.CircleLine") {
            fluentPath {
                moveTo(21.97f, 12.75f)
                lineTo(2.03f, 12.75f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, 19.94f, 0.0f)
                close()
                moveTo(21.97f, 11.25f)
                lineTo(2.03f, 11.25f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, true, 19.94f, 0.0f)
                close()
            }
        }
        return _circleLine!!
    }

private var _circleLine: ImageVector? = null
