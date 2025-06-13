

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Info: ImageVector
    get() {
        if (_info != null) {
            return _info!!
        }
        _info = fluentIcon(name = "Filled.Info") {
            fluentPath {
                moveTo(12.0f, 2.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, true, 0.0f, 20.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, true, 0.0f, -20.0f)
                close()
                moveTo(12.0f, 10.25f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.0f, 0.88f)
                verticalLineToRelative(5.74f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 2.0f, 0.0f)
                verticalLineToRelative(-5.62f)
                lineToRelative(-0.01f, -0.12f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.0f, -0.88f)
                close()
                moveTo(12.0f, 6.5f)
                arcTo(1.25f, 1.25f, 0.0f, true, false, 12.0f, 9.0f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, false, 0.0f, -2.5f)
                close()
            }
        }
        return _info!!
    }

private var _info: ImageVector? = null
