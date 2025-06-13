

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Molecule: ImageVector
    get() {
        if (_molecule != null) {
            return _molecule!!
        }
        _molecule = fluentIcon(name = "Filled.Molecule") {
            fluentPath {
                moveTo(16.0f, 12.0f)
                arcToRelative(5.0f, 5.0f, 0.0f, true, false, -4.34f, -2.51f)
                lineToRelative(-2.71f, 1.8f)
                arcTo(3.99f, 3.99f, 0.0f, false, false, 2.0f, 14.0f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, false, 7.18f, 2.43f)
                lineToRelative(3.89f, 1.94f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.67f, -1.34f)
                lineToRelative(-3.89f, -1.94f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, false, -0.11f, -2.52f)
                lineToRelative(2.86f, -1.9f)
                curveToRelative(0.9f, 0.82f, 2.09f, 1.33f, 3.4f, 1.33f)
                close()
            }
        }
        return _molecule!!
    }

private var _molecule: ImageVector? = null
