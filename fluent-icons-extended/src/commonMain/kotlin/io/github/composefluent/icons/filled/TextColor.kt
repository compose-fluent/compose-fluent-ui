

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextColor: ImageVector
    get() {
        if (_textColor != null) {
            return _textColor!!
        }
        _textColor = fluentIcon(name = "Filled.TextColor") {
            fluentPath {
                moveTo(17.75f, 14.5f)
                curveToRelative(1.24f, 0.0f, 2.25f, 1.0f, 2.25f, 2.25f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 1.24f, -1.0f, 2.25f, -2.25f, 2.25f)
                lineTo(5.25f, 22.0f)
                curveTo(4.01f, 22.0f, 3.0f, 21.0f, 3.0f, 19.75f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -1.24f, 1.0f, -2.25f, 2.25f, -2.25f)
                horizontalLineToRelative(12.5f)
                close()
                moveTo(7.05f, 11.97f)
                lineTo(10.81f, 2.47f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.34f, -0.1f)
                lineToRelative(0.05f, 0.1f)
                lineToRelative(3.75f, 9.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.35f, 0.65f)
                lineToRelative(-0.05f, -0.1f)
                lineToRelative(-1.0f, -2.52f)
                horizontalLineToRelative(-4.1f)
                lineToRelative(-1.0f, 2.52f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.88f, 0.45f)
                lineToRelative(-0.1f, -0.03f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.45f, -0.87f)
                lineToRelative(0.03f, -0.1f)
                lineToRelative(3.76f, -9.5f)
                lineToRelative(-3.76f, 9.5f)
                close()
                moveTo(11.5f, 4.79f)
                lineTo(10.04f, 8.5f)
                horizontalLineToRelative(2.92f)
                lineTo(11.5f, 4.8f)
                close()
            }
        }
        return _textColor!!
    }

private var _textColor: ImageVector? = null
