

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CaretUp: ImageVector
    get() {
        if (_caretUp != null) {
            return _caretUp!!
        }
        _caretUp = fluentIcon(name = "Filled.CaretUp") {
            fluentPath {
                moveTo(6.1f, 16.98f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, -0.94f, -2.07f)
                lineToRelative(5.52f, -6.31f)
                curveToRelative(0.7f, -0.8f, 1.94f, -0.8f, 2.64f, 0.0f)
                lineToRelative(5.52f, 6.3f)
                curveToRelative(0.7f, 0.82f, 0.13f, 2.08f, -0.94f, 2.08f)
                horizontalLineTo(6.1f)
                close()
            }
        }
        return _caretUp!!
    }

private var _caretUp: ImageVector? = null
