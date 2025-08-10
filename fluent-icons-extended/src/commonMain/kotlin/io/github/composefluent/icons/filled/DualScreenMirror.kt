

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DualScreenMirror: ImageVector
    get() {
        if (_dualScreenMirror != null) {
            return _dualScreenMirror!!
        }
        _dualScreenMirror = fluentIcon(name = "Filled.DualScreenMirror") {
            fluentPath {
                moveTo(20.25f, 4.0f)
                curveTo(21.22f, 4.0f, 22.0f, 4.8f, 22.0f, 5.75f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                horizontalLineToRelative(-7.24f)
                curveToRelative(-0.1f, 0.0f, -0.18f, 0.0f, -0.26f, -0.02f)
                lineTo(12.75f, 4.02f)
                lineToRelative(0.26f, -0.02f)
                horizontalLineToRelative(7.24f)
                close()
                moveTo(18.17f, 9.42f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.34f, 0.0f)
                lineToRelative(-2.25f, 4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.67f, 1.08f)
                horizontalLineToRelative(4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.67f, -1.08f)
                lineToRelative(-2.25f, -4.5f)
                close()
                moveTo(11.01f, 4.0f)
                curveToRelative(0.08f, 0.0f, 0.16f, 0.0f, 0.24f, 0.02f)
                verticalLineToRelative(15.96f)
                lineToRelative(-0.24f, 0.02f)
                lineTo(3.76f, 20.0f)
                curveTo(2.79f, 20.0f, 2.0f, 19.2f, 2.0f, 18.25f)
                lineTo(2.0f, 5.75f)
                curveTo(2.0f, 4.8f, 2.79f, 4.0f, 3.76f, 4.0f)
                lineTo(11.0f, 4.0f)
                close()
                moveTo(7.17f, 9.42f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.34f, 0.0f)
                lineToRelative(-2.25f, 4.5f)
                arcTo(0.75f, 0.75f, 0.0f, false, false, 4.25f, 15.0f)
                horizontalLineToRelative(4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.67f, -1.08f)
                lineToRelative(-2.25f, -4.5f)
                close()
            }
        }
        return _dualScreenMirror!!
    }

private var _dualScreenMirror: ImageVector? = null
