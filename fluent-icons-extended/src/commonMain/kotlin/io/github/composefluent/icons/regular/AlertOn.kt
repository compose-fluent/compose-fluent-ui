

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.AlertOn: ImageVector
    get() {
        if (_alertOn != null) {
            return _alertOn!!
        }
        _alertOn = fluentIcon(name = "Regular.AlertOn") {
            fluentPath {
                moveTo(12.0f, 2.0f)
                arcToRelative(7.5f, 7.5f, 0.0f, false, true, 7.5f, 7.25f)
                verticalLineToRelative(4.35f)
                lineToRelative(1.38f, 3.15f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, -1.15f, 1.75f)
                lineTo(15.0f, 18.5f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -6.0f, 0.18f)
                verticalLineToRelative(-0.18f)
                lineTo(4.27f, 18.5f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, -1.14f, -1.75f)
                lineTo(4.5f, 13.6f)
                lineTo(4.5f, 9.5f)
                curveTo(4.5f, 5.35f, 7.85f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(13.5f, 18.5f)
                horizontalLineToRelative(-3.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, false, 3.0f, 0.15f)
                verticalLineToRelative(-0.15f)
                close()
                moveTo(12.0f, 3.5f)
                curveToRelative(-3.32f, 0.0f, -6.0f, 2.67f, -6.0f, 6.0f)
                verticalLineToRelative(4.4f)
                lineTo(4.66f, 17.0f)
                horizontalLineToRelative(14.7f)
                lineTo(18.0f, 13.9f)
                lineTo(18.0f, 9.29f)
                arcToRelative(5.99f, 5.99f, 0.0f, false, false, -6.0f, -5.78f)
                close()
                moveTo(21.0f, 8.25f)
                horizontalLineToRelative(2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                lineTo(21.0f, 9.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(0.1f)
                close()
                moveTo(1.0f, 8.25f)
                horizontalLineToRelative(2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                lineTo(1.0f, 9.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                lineTo(1.0f, 8.25f)
                close()
                moveTo(22.6f, 2.55f)
                curveToRelative(0.23f, 0.3f, 0.2f, 0.72f, -0.07f, 0.98f)
                lineToRelative(-0.08f, 0.07f)
                lineToRelative(-2.0f, 1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.98f, -1.13f)
                lineToRelative(0.08f, -0.07f)
                lineToRelative(2.0f, -1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.05f, 0.15f)
                close()
                moveTo(2.45f, 2.4f)
                lineToRelative(2.0f, 1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -0.9f, 1.2f)
                lineToRelative(-2.0f, -1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, 0.9f, -1.2f)
                close()
            }
        }
        return _alertOn!!
    }

private var _alertOn: ImageVector? = null
