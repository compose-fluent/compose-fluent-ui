

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.CommentCheckmark: ImageVector
    get() {
        if (_commentCheckmark != null) {
            return _commentCheckmark!!
        }
        _commentCheckmark = fluentIcon(name = "Filled.CommentCheckmark") {
            fluentPath {
                moveTo(23.0f, 6.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 11.0f, 0.0f)
                close()
                moveTo(20.85f, 4.15f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.7f, 0.0f)
                lineTo(16.5f, 7.79f)
                lineToRelative(-1.65f, -1.64f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.7f, 0.7f)
                lineToRelative(2.0f, 2.0f)
                curveToRelative(0.2f, 0.2f, 0.5f, 0.2f, 0.7f, 0.0f)
                lineToRelative(4.0f, -4.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.0f, -0.7f)
                close()
                moveTo(17.5f, 13.0f)
                curveToRelative(1.75f, 0.0f, 3.33f, -0.69f, 4.5f, -1.81f)
                verticalLineToRelative(3.56f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                lineTo(13.0f, 18.0f)
                lineToRelative(-5.0f, 3.75f)
                curveToRelative(-0.82f, 0.62f, -2.0f, 0.03f, -2.0f, -1.0f)
                lineTo(6.0f, 18.0f)
                horizontalLineToRelative(-0.75f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 2.0f, 14.75f)
                verticalLineToRelative(-8.5f)
                curveTo(2.0f, 4.45f, 3.46f, 3.0f, 5.25f, 3.0f)
                horizontalLineToRelative(6.77f)
                arcToRelative(6.5f, 6.5f, 0.0f, false, false, 5.48f, 10.0f)
                close()
            }
        }
        return _commentCheckmark!!
    }

private var _commentCheckmark: ImageVector? = null
