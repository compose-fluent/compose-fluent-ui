

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ArrowSortUp: ImageVector
    get() {
        if (_arrowSortUp != null) {
            return _arrowSortUp!!
        }
        _arrowSortUp = fluentIcon(name = "Regular.ArrowSortUp") {
            fluentPath {
                moveToRelative(6.72f, 8.71f)
                lineToRelative(4.5f, -4.49f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.97f, -0.07f)
                lineToRelative(0.08f, 0.07f)
                lineToRelative(4.5f, 4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.97f, 1.13f)
                lineToRelative(-0.08f, -0.07f)
                lineToRelative(-3.22f, -3.22f)
                verticalLineToRelative(12.7f)
                curveToRelative(0.0f, 0.37f, -0.28f, 0.69f, -0.65f, 0.74f)
                horizontalLineToRelative(-0.1f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.75f, -0.64f)
                verticalLineTo(6.56f)
                lineTo(7.78f, 9.78f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.98f, 0.07f)
                lineToRelative(-0.08f, -0.07f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.07f, -0.98f)
                lineToRelative(0.07f, -0.09f)
                lineToRelative(4.5f, -4.49f)
                lineToRelative(-4.5f, 4.5f)
                close()
            }
        }
        return _arrowSortUp!!
    }

private var _arrowSortUp: ImageVector? = null
