

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.MailAllRead: ImageVector
    get() {
        if (_mailAllRead != null) {
            return _mailAllRead!!
        }
        _mailAllRead = fluentIcon(name = "Regular.MailAllRead") {
            fluentPath {
                moveTo(11.36f, 1.84f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.72f, 0.0f)
                lineTo(2.83f, 6.1f)
                arcTo(1.6f, 1.6f, 0.0f, false, false, 2.0f, 7.5f)
                verticalLineToRelative(8.25f)
                curveTo(2.0f, 17.55f, 3.46f, 19.0f, 5.25f, 19.0f)
                horizontalLineToRelative(11.5f)
                curveToRelative(1.8f, 0.0f, 3.25f, -1.46f, 3.25f, -3.25f)
                lineTo(20.0f, 7.5f)
                arcToRelative(1.6f, 1.6f, 0.0f, false, false, -0.83f, -1.4f)
                lineToRelative(-7.81f, -4.26f)
                close()
                moveTo(11.0f, 11.14f)
                lineTo(3.86f, 7.26f)
                lineTo(11.0f, 3.35f)
                lineToRelative(7.14f, 3.9f)
                lineToRelative(-7.14f, 3.9f)
                close()
                moveTo(11.36f, 12.66f)
                lineTo(18.5f, 8.76f)
                verticalLineToRelative(6.99f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                lineTo(5.25f, 17.5f)
                curveToRelative(-0.97f, 0.0f, -1.75f, -0.78f, -1.75f, -1.75f)
                lineTo(3.5f, 8.76f)
                lineToRelative(7.14f, 3.9f)
                curveToRelative(0.22f, 0.12f, 0.5f, 0.12f, 0.72f, 0.0f)
                close()
                moveTo(21.0f, 7.5f)
                curveToRelative(0.9f, 0.58f, 1.5f, 1.59f, 1.5f, 2.74f)
                verticalLineToRelative(6.0f)
                curveToRelative(0.0f, 2.9f, -2.35f, 5.25f, -5.25f, 5.25f)
                horizontalLineToRelative(-9.0f)
                curveToRelative(-1.15f, 0.0f, -2.16f, -0.6f, -2.74f, -1.5f)
                horizontalLineToRelative(11.74f)
                arcTo(3.75f, 3.75f, 0.0f, false, false, 21.0f, 16.25f)
                lineTo(21.0f, 7.51f)
                close()
            }
        }
        return _mailAllRead!!
    }

private var _mailAllRead: ImageVector? = null
