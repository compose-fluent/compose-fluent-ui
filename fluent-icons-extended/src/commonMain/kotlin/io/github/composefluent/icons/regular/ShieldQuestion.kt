

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ShieldQuestion: ImageVector
    get() {
        if (_shieldQuestion != null) {
            return _shieldQuestion!!
        }
        _shieldQuestion = fluentIcon(name = "Regular.ShieldQuestion") {
            fluentPath {
                moveTo(3.0f, 5.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                curveToRelative(2.66f, 0.0f, 5.26f, -0.94f, 7.8f, -2.85f)
                curveToRelative(0.27f, -0.2f, 0.63f, -0.2f, 0.9f, 0.0f)
                curveTo(14.99f, 4.05f, 17.59f, 5.0f, 20.25f, 5.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                lineTo(21.0f, 11.0f)
                curveToRelative(0.0f, 5.0f, -2.96f, 8.68f, -8.73f, 10.95f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.54f, 0.0f)
                curveTo(5.96f, 19.68f, 3.0f, 16.0f, 3.0f, 11.0f)
                lineTo(3.0f, 5.75f)
                close()
                moveTo(4.5f, 6.48f)
                lineTo(4.5f, 11.0f)
                curveToRelative(0.0f, 4.26f, 2.45f, 7.38f, 7.5f, 9.44f)
                curveToRelative(5.05f, -2.06f, 7.5f, -5.18f, 7.5f, -9.44f)
                lineTo(19.5f, 6.48f)
                arcToRelative(14.36f, 14.36f, 0.0f, false, true, -7.5f, -2.8f)
                arcToRelative(14.36f, 14.36f, 0.0f, false, true, -7.5f, 2.8f)
                close()
                moveTo(13.0f, 16.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 2.0f, 0.0f)
                close()
                moveTo(10.75f, 9.75f)
                arcToRelative(1.25f, 1.25f, 0.0f, true, true, 2.5f, 0.0f)
                curveToRelative(0.0f, 0.45f, -0.25f, 0.74f, -0.78f, 1.27f)
                lineToRelative(-0.05f, 0.05f)
                curveToRelative(-0.47f, 0.46f, -1.17f, 1.16f, -1.17f, 2.28f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                curveToRelative(0.0f, -0.45f, 0.25f, -0.74f, 0.78f, -1.27f)
                lineToRelative(0.05f, -0.05f)
                curveToRelative(0.47f, -0.47f, 1.17f, -1.16f, 1.17f, -2.28f)
                arcToRelative(2.75f, 2.75f, 0.0f, true, false, -5.5f, 0.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                close()
            }
        }
        return _shieldQuestion!!
    }

private var _shieldQuestion: ImageVector? = null
