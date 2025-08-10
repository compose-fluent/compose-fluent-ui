

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.DocumentPageTopRight: ImageVector
    get() {
        if (_documentPageTopRight != null) {
            return _documentPageTopRight!!
        }
        _documentPageTopRight = fluentIcon(name = "Filled.DocumentPageTopRight") {
            fluentPath {
                moveToRelative(13.26f, 9.0f)
                lineToRelative(0.23f, -1.5f)
                horizontalLineToRelative(1.48f)
                lineTo(14.74f, 9.0f)
                horizontalLineToRelative(-1.48f)
                close()
                moveTo(17.75f, 2.0f)
                curveToRelative(1.2f, 0.0f, 2.17f, 0.93f, 2.24f, 2.1f)
                lineToRelative(0.01f, 0.15f)
                verticalLineToRelative(15.5f)
                curveToRelative(0.0f, 1.19f, -0.93f, 2.16f, -2.1f, 2.24f)
                lineTo(6.25f, 21.99f)
                curveToRelative(-1.2f, 0.0f, -2.17f, -0.92f, -2.24f, -2.09f)
                lineTo(4.0f, 19.75f)
                lineTo(4.0f, 4.25f)
                curveToRelative(0.0f, -1.2f, 0.93f, -2.17f, 2.1f, -2.24f)
                lineTo(6.25f, 2.0f)
                horizontalLineToRelative(11.5f)
                close()
                moveTo(14.4f, 11.16f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 1.48f, 0.23f)
                lineToRelative(0.14f, -0.9f)
                horizontalLineToRelative(0.73f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                horizontalLineToRelative(-0.5f)
                lineToRelative(0.24f, -1.5f)
                horizontalLineToRelative(0.76f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                horizontalLineToRelative(-0.52f)
                lineToRelative(0.16f, -1.02f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.48f, -0.23f)
                lineTo(15.21f, 6.0f)
                horizontalLineToRelative(-1.48f)
                lineToRelative(0.16f, -1.03f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.48f, -0.23f)
                lineTo(12.21f, 6.0f)
                horizontalLineToRelative(-0.96f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, 1.5f)
                horizontalLineToRelative(0.72f)
                lineTo(11.74f, 9.0f)
                horizontalLineToRelative(-0.99f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, 1.5f)
                horizontalLineToRelative(0.75f)
                lineToRelative(-0.1f, 0.66f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 1.48f, 0.23f)
                lineToRelative(0.14f, -0.9f)
                horizontalLineToRelative(1.48f)
                lineToRelative(-0.1f, 0.67f)
                close()
            }
        }
        return _documentPageTopRight!!
    }

private var _documentPageTopRight: ImageVector? = null
