

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DividerTall: ImageVector
    get() {
        if (_dividerTall != null) {
            return _dividerTall!!
        }
        _dividerTall = fluentIcon(name = "Filled.DividerTall") {
            fluentPath {
                moveTo(11.0f, 3.0f)
                verticalLineToRelative(18.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 2.0f, 0.0f)
                verticalLineTo(3.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, -2.0f, 0.0f)
                close()
            }
        }
        return _dividerTall!!
    }

private var _dividerTall: ImageVector? = null
