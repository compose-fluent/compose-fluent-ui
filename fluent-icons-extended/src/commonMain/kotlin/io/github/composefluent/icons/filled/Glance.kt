

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Glance: ImageVector
    get() {
        if (_glance != null) {
            return _glance!!
        }
        _glance = fluentIcon(name = "Filled.Glance") {
            fluentPath {
                moveTo(19.25f, 11.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.79f, 1.75f, 1.75f)
                verticalLineToRelative(6.5f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                horizontalLineToRelative(-4.5f)
                curveToRelative(-0.97f, 0.0f, -1.75f, -0.78f, -1.75f, -1.75f)
                verticalLineToRelative(-6.5f)
                curveToRelative(0.0f, -0.96f, 0.78f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(4.5f)
                close()
                moveTo(9.27f, 15.01f)
                curveToRelative(0.96f, 0.0f, 1.75f, 0.79f, 1.75f, 1.75f)
                verticalLineToRelative(2.5f)
                curveToRelative(0.0f, 0.96f, -0.79f, 1.74f, -1.75f, 1.74f)
                lineTo(4.75f, 21.0f)
                curveTo(3.8f, 21.0f, 3.0f, 20.22f, 3.0f, 19.25f)
                verticalLineToRelative(-2.49f)
                curveToRelative(0.0f, -0.96f, 0.79f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(4.52f)
                close()
                moveTo(9.25f, 3.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(6.5f)
                curveToRelative(0.0f, 0.92f, -0.7f, 1.67f, -1.6f, 1.74f)
                lineToRelative(-0.15f, 0.01f)
                horizontalLineToRelative(-4.5f)
                curveTo(3.8f, 13.0f, 3.0f, 12.22f, 3.0f, 11.25f)
                verticalLineToRelative(-6.5f)
                curveTo(3.0f, 3.78f, 3.8f, 3.0f, 4.75f, 3.0f)
                horizontalLineToRelative(4.5f)
                close()
                moveTo(19.25f, 3.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(2.5f)
                curveTo(21.0f, 8.22f, 20.22f, 9.0f, 19.25f, 9.0f)
                horizontalLineToRelative(-4.5f)
                curveTo(13.78f, 9.0f, 13.0f, 8.22f, 13.0f, 7.25f)
                verticalLineToRelative(-2.5f)
                curveToRelative(0.0f, -0.97f, 0.78f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(4.5f)
                close()
            }
        }
        return _glance!!
    }

private var _glance: ImageVector? = null
