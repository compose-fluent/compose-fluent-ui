

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.PanelLeftExpand: ImageVector
    get() {
        if (_panelLeftExpand != null) {
            return _panelLeftExpand!!
        }
        _panelLeftExpand = fluentIcon(name = "Regular.PanelLeftExpand") {
            fluentPath {
                moveTo(14.2f, 14.75f)
                curveToRelative(0.27f, 0.3f, 0.74f, 0.33f, 1.05f, 0.06f)
                lineToRelative(2.5f, -2.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.12f)
                lineToRelative(-2.5f, -2.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.0f, 1.12f)
                lineToRelative(1.05f, 0.94f)
                horizontalLineToRelative(-3.55f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 0.0f, 1.5f)
                horizontalLineToRelative(3.55f)
                lineToRelative(-1.05f, 0.94f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.06f, 1.06f)
                close()
                moveTo(2.0f, 6.75f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 4.75f, 4.0f)
                horizontalLineToRelative(14.5f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 22.0f, 6.75f)
                verticalLineToRelative(10.5f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 19.25f, 20.0f)
                lineTo(4.75f, 20.0f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 2.0f, 17.25f)
                lineTo(2.0f, 6.75f)
                close()
                moveTo(4.75f, 5.5f)
                curveToRelative(-0.69f, 0.0f, -1.25f, 0.56f, -1.25f, 1.25f)
                verticalLineToRelative(10.5f)
                curveToRelative(0.0f, 0.69f, 0.56f, 1.25f, 1.25f, 1.25f)
                lineTo(8.0f, 18.5f)
                verticalLineToRelative(-13.0f)
                lineTo(4.75f, 5.5f)
                close()
                moveTo(9.5f, 5.5f)
                verticalLineToRelative(13.0f)
                horizontalLineToRelative(9.75f)
                curveToRelative(0.69f, 0.0f, 1.25f, -0.56f, 1.25f, -1.25f)
                lineTo(20.5f, 6.75f)
                curveToRelative(0.0f, -0.69f, -0.56f, -1.25f, -1.25f, -1.25f)
                lineTo(9.5f, 5.5f)
                close()
            }
        }
        return _panelLeftExpand!!
    }

private var _panelLeftExpand: ImageVector? = null
