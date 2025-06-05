

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CommentLightning: ImageVector
    get() {
        if (_commentLightning != null) {
            return _commentLightning!!
        }
        _commentLightning = fluentIcon(name = "Filled.CommentLightning") {
            fluentPath {
                moveTo(16.32f, 1.0f)
                horizontalLineToRelative(4.83f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.43f, 0.75f)
                lineTo(19.75f, 5.0f)
                horizontalLineToRelative(1.5f)
                curveToRelative(0.64f, 0.0f, 0.99f, 0.76f, 0.56f, 1.24f)
                lineTo(17.0f, 11.75f)
                curveToRelative(-0.5f, 0.58f, -1.45f, 0.09f, -1.26f, -0.66f)
                lineTo(16.5f, 8.0f)
                horizontalLineToRelative(-3.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.45f, -0.71f)
                lineToRelative(2.82f, -6.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.45f, -0.29f)
                close()
                moveTo(5.25f, 3.0f)
                horizontalLineToRelative(8.7f)
                lineToRelative(-1.81f, 3.86f)
                arcTo(1.5f, 1.5f, 0.0f, false, false, 13.5f, 9.0f)
                horizontalLineToRelative(1.72f)
                lineToRelative(-0.46f, 1.85f)
                curveToRelative(-0.44f, 1.76f, 1.78f, 2.93f, 2.98f, 1.56f)
                lineTo(22.0f, 7.55f)
                verticalLineToRelative(7.2f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                horizontalLineTo(13.0f)
                lineToRelative(-5.0f, 3.75f)
                curveToRelative(-0.82f, 0.62f, -2.0f, 0.03f, -2.0f, -1.0f)
                verticalLineTo(18.0f)
                horizontalLineToRelative(-0.75f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 2.0f, 14.75f)
                verticalLineToRelative(-8.5f)
                curveTo(2.0f, 4.45f, 3.46f, 3.0f, 5.25f, 3.0f)
                close()
            }
        }
        return _commentLightning!!
    }

private var _commentLightning: ImageVector? = null
