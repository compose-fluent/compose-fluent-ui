

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Link: ImageVector
    get() {
        if (_link != null) {
            return _link!!
        }
        _link = fluentIcon(name = "Filled.Link") {
            fluentPath {
                moveTo(9.0f, 7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(7.0f, 9.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, -0.18f, 6.0f)
                lineTo(9.0f, 15.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(7.0f, 17.0f)
                arcToRelative(5.0f, 5.0f, 0.0f, false, true, -0.22f, -10.0f)
                lineTo(9.0f, 7.0f)
                close()
                moveTo(17.0f, 7.0f)
                arcToRelative(5.0f, 5.0f, 0.0f, false, true, 0.22f, 10.0f)
                lineTo(15.0f, 17.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(17.0f, 15.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, false, 0.18f, -6.0f)
                lineTo(15.0f, 9.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(17.0f, 7.0f)
                close()
                moveTo(7.0f, 11.0f)
                horizontalLineToRelative(10.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(7.0f, 13.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(17.0f, 11.0f)
                lineTo(7.0f, 11.0f)
                close()
            }
        }
        return _link!!
    }

private var _link: ImageVector? = null
