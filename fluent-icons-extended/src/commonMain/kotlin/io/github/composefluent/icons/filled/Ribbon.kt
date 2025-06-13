

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Ribbon: ImageVector
    get() {
        if (_ribbon != null) {
            return _ribbon!!
        }
        _ribbon = fluentIcon(name = "Filled.Ribbon") {
            fluentPath {
                moveTo(17.0f, 15.24f)
                verticalLineToRelative(6.0f)
                curveToRelative(0.0f, 0.61f, -0.68f, 0.97f, -1.18f, 0.62f)
                lineTo(12.0f, 19.17f)
                lineToRelative(-3.82f, 2.69f)
                arcTo(0.75f, 0.75f, 0.0f, false, true, 7.0f, 21.25f)
                verticalLineToRelative(-6.0f)
                arcToRelative(7.97f, 7.97f, 0.0f, false, false, 10.0f, 0.0f)
                close()
                moveTo(12.0f, 2.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, true, true, 0.0f, 14.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, false, true, 0.0f, -14.0f)
                close()
            }
        }
        return _ribbon!!
    }

private var _ribbon: ImageVector? = null
