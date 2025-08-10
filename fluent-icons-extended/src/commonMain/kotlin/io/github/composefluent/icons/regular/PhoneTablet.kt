

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.PhoneTablet: ImageVector
    get() {
        if (_phoneTablet != null) {
            return _phoneTablet!!
        }
        _phoneTablet = fluentIcon(name = "Regular.PhoneTablet") {
            fluentPath {
                moveTo(8.25f, 7.0f)
                curveTo(9.22f, 7.0f, 10.0f, 7.78f, 10.0f, 8.75f)
                verticalLineToRelative(9.5f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                horizontalLineToRelative(-4.5f)
                curveTo(2.78f, 20.0f, 2.0f, 19.22f, 2.0f, 18.25f)
                verticalLineToRelative(-9.5f)
                curveTo(2.0f, 7.78f, 2.78f, 7.0f, 3.75f, 7.0f)
                horizontalLineToRelative(4.5f)
                close()
                moveTo(8.25f, 8.5f)
                horizontalLineToRelative(-4.5f)
                arcToRelative(0.25f, 0.25f, 0.0f, false, false, -0.25f, 0.25f)
                verticalLineToRelative(9.5f)
                curveToRelative(0.0f, 0.14f, 0.11f, 0.25f, 0.25f, 0.25f)
                horizontalLineToRelative(4.5f)
                curveToRelative(0.14f, 0.0f, 0.25f, -0.11f, 0.25f, -0.25f)
                verticalLineToRelative(-9.5f)
                arcToRelative(0.25f, 0.25f, 0.0f, false, false, -0.25f, -0.25f)
                close()
                moveTo(6.25f, 16.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                horizontalLineToRelative(-0.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(0.6f)
                close()
                moveTo(19.75f, 4.0f)
                curveToRelative(1.19f, 0.0f, 2.16f, 0.93f, 2.24f, 2.1f)
                verticalLineToRelative(8.65f)
                curveToRelative(0.0f, 1.2f, -0.92f, 2.17f, -2.09f, 2.24f)
                lineToRelative(-0.15f, 0.01f)
                lineTo(11.0f, 17.0f)
                verticalLineToRelative(-1.5f)
                horizontalLineToRelative(8.75f)
                curveToRelative(0.38f, 0.0f, 0.7f, -0.28f, 0.74f, -0.65f)
                verticalLineToRelative(-8.6f)
                curveToRelative(0.0f, -0.38f, -0.27f, -0.7f, -0.64f, -0.74f)
                lineToRelative(-0.1f, -0.01f)
                lineTo(8.25f, 5.5f)
                curveToRelative(-0.33f, 0.0f, -0.6f, 0.2f, -0.7f, 0.5f)
                lineTo(6.0f, 6.0f)
                arcTo(2.25f, 2.25f, 0.0f, false, true, 8.1f, 4.0f)
                horizontalLineToRelative(11.66f)
                close()
                moveTo(15.25f, 13.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                horizontalLineToRelative(-2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, -1.5f)
                horizontalLineToRelative(2.5f)
                close()
            }
        }
        return _phoneTablet!!
    }

private var _phoneTablet: ImageVector? = null
