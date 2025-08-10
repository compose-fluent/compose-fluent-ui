

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ClipboardDay: ImageVector
    get() {
        if (_clipboardDay != null) {
            return _clipboardDay!!
        }
        _clipboardDay = fluentIcon(name = "Filled.ClipboardDay") {
            fluentPath {
                moveTo(9.5f, 13.75f)
                curveToRelative(0.0f, -0.14f, 0.11f, -0.25f, 0.25f, -0.25f)
                horizontalLineToRelative(4.5f)
                curveToRelative(0.14f, 0.0f, 0.25f, 0.11f, 0.25f, 0.25f)
                verticalLineToRelative(2.5f)
                curveToRelative(0.0f, 0.14f, -0.11f, 0.25f, -0.25f, 0.25f)
                horizontalLineToRelative(-4.5f)
                arcToRelative(0.25f, 0.25f, 0.0f, false, true, -0.25f, -0.25f)
                verticalLineToRelative(-2.5f)
                close()
                moveTo(10.25f, 2.0f)
                horizontalLineToRelative(3.5f)
                curveToRelative(1.16f, 0.0f, 2.11f, 0.87f, 2.24f, 2.0f)
                horizontalLineToRelative(1.76f)
                curveTo(18.99f, 4.0f, 20.0f, 5.0f, 20.0f, 6.25f)
                verticalLineToRelative(13.5f)
                curveToRelative(0.0f, 1.24f, -1.0f, 2.25f, -2.25f, 2.25f)
                lineTo(6.25f, 22.0f)
                curveTo(5.01f, 22.0f, 4.0f, 21.0f, 4.0f, 19.75f)
                lineTo(4.0f, 6.25f)
                curveTo(4.0f, 5.01f, 5.0f, 4.0f, 6.25f, 4.0f)
                horizontalLineToRelative(1.76f)
                curveToRelative(0.13f, -1.13f, 1.08f, -2.0f, 2.24f, -2.0f)
                close()
                moveTo(13.75f, 3.5f)
                horizontalLineToRelative(-3.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, 1.5f)
                horizontalLineToRelative(3.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                close()
                moveTo(8.0f, 9.75f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(6.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                horizontalLineToRelative(-6.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                close()
                moveTo(9.75f, 12.0f)
                curveTo(8.78f, 12.0f, 8.0f, 12.78f, 8.0f, 13.75f)
                verticalLineToRelative(2.5f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(4.5f)
                curveToRelative(0.97f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                verticalLineToRelative(-2.5f)
                curveToRelative(0.0f, -0.97f, -0.78f, -1.75f, -1.75f, -1.75f)
                horizontalLineToRelative(-4.5f)
                close()
            }
        }
        return _clipboardDay!!
    }

private var _clipboardDay: ImageVector? = null
