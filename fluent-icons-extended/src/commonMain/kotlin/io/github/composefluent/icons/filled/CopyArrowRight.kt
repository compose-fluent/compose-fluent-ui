

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CopyArrowRight: ImageVector
    get() {
        if (_copyArrowRight != null) {
            return _copyArrowRight!!
        }
        _copyArrowRight = fluentIcon(name = "Filled.CopyArrowRight") {
            fluentPath {
                moveTo(17.5f, 12.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, 0.0f, 11.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 0.0f, -11.0f)
                close()
                moveTo(5.5f, 4.63f)
                lineTo(5.5f, 17.25f)
                curveToRelative(0.0f, 1.8f, 1.46f, 3.25f, 3.25f, 3.25f)
                horizontalLineToRelative(2.98f)
                curveToRelative(0.3f, 0.56f, 0.66f, 1.06f, 1.08f, 1.5f)
                lineTo(8.75f, 22.0f)
                arcTo(4.75f, 4.75f, 0.0f, false, true, 4.0f, 17.25f)
                lineTo(4.0f, 6.75f)
                curveToRelative(0.0f, -0.98f, 0.63f, -1.81f, 1.5f, -2.12f)
                close()
                moveTo(18.28f, 14.59f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.56f, 0.0f)
                lineToRelative(-0.07f, 0.06f)
                lineToRelative(-0.06f, 0.07f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.0f, 0.56f)
                lineToRelative(0.06f, 0.07f)
                lineTo(19.29f, 17.0f)
                lineTo(14.4f, 17.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.4f, 0.41f)
                verticalLineToRelative(0.18f)
                curveToRelative(0.04f, 0.2f, 0.2f, 0.36f, 0.4f, 0.4f)
                lineToRelative(0.1f, 0.01f)
                horizontalLineToRelative(4.8f)
                lineToRelative(-1.65f, 1.65f)
                lineToRelative(-0.06f, 0.07f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.7f, 0.7f)
                lineToRelative(0.06f, -0.07f)
                lineToRelative(2.53f, -2.53f)
                lineToRelative(0.04f, -0.05f)
                lineToRelative(0.04f, -0.08f)
                lineToRelative(0.03f, -0.08f)
                lineTo(20.99f, 17.39f)
                lineToRelative(-0.01f, -0.04f)
                lineToRelative(-0.04f, -0.08f)
                lineToRelative(-0.02f, -0.04f)
                lineToRelative(-0.04f, -0.05f)
                lineToRelative(-2.53f, -2.53f)
                lineToRelative(-0.07f, -0.06f)
                close()
                moveTo(17.75f, 2.0f)
                curveTo(18.99f, 2.0f, 20.0f, 3.0f, 20.0f, 4.25f)
                verticalLineToRelative(7.25f)
                arcToRelative(6.5f, 6.5f, 0.0f, false, false, -8.69f, 8.0f)
                lineTo(8.75f, 19.5f)
                curveToRelative(-1.24f, 0.0f, -2.25f, -1.0f, -2.25f, -2.25f)
                verticalLineToRelative(-13.0f)
                curveTo(6.5f, 3.01f, 7.5f, 2.0f, 8.75f, 2.0f)
                horizontalLineToRelative(9.0f)
                close()
            }
        }
        return _copyArrowRight!!
    }

private var _copyArrowRight: ImageVector? = null
