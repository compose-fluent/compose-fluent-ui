

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.DividerTall: ImageVector
    get() {
        if (_dividerTall != null) {
            return _dividerTall!!
        }
        _dividerTall = fluentIcon(name = "Regular.DividerTall") {
            fluentPath {
                moveTo(11.25f, 2.75f)
                verticalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                verticalLineTo(2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                close()
            }
        }
        return _dividerTall!!
    }

private var _dividerTall: ImageVector? = null
