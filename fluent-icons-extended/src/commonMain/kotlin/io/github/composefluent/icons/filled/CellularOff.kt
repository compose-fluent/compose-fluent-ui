

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CellularOff: ImageVector
    get() {
        if (_cellularOff != null) {
            return _cellularOff!!
        }
        _cellularOff = fluentIcon(name = "Filled.CellularOff") {
            fluentPath {
                moveTo(3.28f, 2.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.06f, 1.06f)
                lineTo(11.0f, 12.06f)
                lineTo(11.0f, 19.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 2.0f, 0.0f)
                verticalLineToRelative(-4.94f)
                lineToRelative(2.0f, 2.0f)
                lineTo(15.0f, 19.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 2.0f, 0.0f)
                verticalLineToRelative(-0.94f)
                lineToRelative(3.72f, 3.72f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.06f, -1.06f)
                lineTo(3.28f, 2.22f)
                close()
                moveTo(19.0f, 15.82f)
                lineToRelative(2.0f, 2.0f)
                lineTo(21.0f, 6.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -2.0f, 0.0f)
                verticalLineToRelative(9.82f)
                close()
                moveTo(15.0f, 11.82f)
                lineTo(17.0f, 13.82f)
                lineTo(17.0f, 9.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -2.0f, 0.0f)
                verticalLineToRelative(2.82f)
                close()
                moveTo(8.0f, 14.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, 1.0f)
                verticalLineToRelative(4.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, 1.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                verticalLineToRelative(-4.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, -1.0f)
                close()
                moveTo(4.0f, 17.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.0f, 0.98f)
                verticalLineToRelative(1.04f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 4.0f, 20.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -0.98f)
                verticalLineToRelative(-1.04f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 4.0f, 17.0f)
                close()
            }
        }
        return _cellularOff!!
    }

private var _cellularOff: ImageVector? = null
