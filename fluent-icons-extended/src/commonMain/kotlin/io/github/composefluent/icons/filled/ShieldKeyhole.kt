

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ShieldKeyhole: ImageVector
    get() {
        if (_shieldKeyhole != null) {
            return _shieldKeyhole!!
        }
        _shieldKeyhole = fluentIcon(name = "Filled.ShieldKeyhole") {
            fluentPath {
                moveTo(3.0f, 5.75f)
                verticalLineTo(11.0f)
                curveToRelative(0.0f, 5.0f, 2.96f, 8.68f, 8.73f, 10.95f)
                curveToRelative(0.17f, 0.07f, 0.37f, 0.07f, 0.54f, 0.0f)
                curveTo(18.04f, 19.68f, 21.0f, 16.0f, 21.0f, 11.0f)
                verticalLineTo(5.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                curveToRelative(-2.66f, 0.0f, -5.26f, -0.94f, -7.8f, -2.85f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.9f, 0.0f)
                curveTo(9.01f, 4.05f, 6.41f, 5.0f, 3.75f, 5.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                close()
                moveTo(14.0f, 11.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, true, -1.25f, 1.85f)
                verticalLineToRelative(2.4f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.5f, 0.0f)
                verticalLineToRelative(-2.4f)
                arcTo(2.0f, 2.0f, 0.0f, true, true, 14.0f, 11.0f)
                close()
            }
        }
        return _shieldKeyhole!!
    }

private var _shieldKeyhole: ImageVector? = null
