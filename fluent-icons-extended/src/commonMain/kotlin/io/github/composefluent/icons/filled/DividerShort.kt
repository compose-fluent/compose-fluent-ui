

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DividerShort: ImageVector
    get() {
        if (_dividerShort != null) {
            return _dividerShort!!
        }
        _dividerShort = fluentIcon(name = "Filled.DividerShort") {
            fluentPath {
                moveTo(11.0f, 5.0f)
                verticalLineToRelative(14.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 2.0f, 0.0f)
                verticalLineTo(5.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, -2.0f, 0.0f)
                close()
            }
        }
        return _dividerShort!!
    }

private var _dividerShort: ImageVector? = null
