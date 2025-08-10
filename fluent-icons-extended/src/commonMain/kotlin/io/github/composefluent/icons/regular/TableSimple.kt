

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TableSimple: ImageVector
    get() {
        if (_tableSimple != null) {
            return _tableSimple!!
        }
        _tableSimple = fluentIcon(name = "Regular.TableSimple") {
            fluentPath {
                moveTo(3.0f, 6.25f)
                curveTo(3.0f, 4.45f, 4.46f, 3.0f, 6.25f, 3.0f)
                horizontalLineToRelative(11.5f)
                curveTo(19.55f, 3.0f, 21.0f, 4.46f, 21.0f, 6.25f)
                verticalLineToRelative(11.5f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                lineTo(6.25f, 21.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 3.0f, 17.75f)
                lineTo(3.0f, 6.25f)
                close()
                moveTo(6.25f, 4.5f)
                curveToRelative(-0.97f, 0.0f, -1.75f, 0.78f, -1.75f, 1.75f)
                verticalLineToRelative(5.0f)
                horizontalLineToRelative(6.75f)
                lineTo(11.25f, 4.5f)
                horizontalLineToRelative(-5.0f)
                close()
                moveTo(11.25f, 12.75f)
                lineTo(4.5f, 12.75f)
                verticalLineToRelative(5.0f)
                curveToRelative(0.0f, 0.97f, 0.78f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(-6.75f)
                close()
                moveTo(12.75f, 12.75f)
                verticalLineToRelative(6.75f)
                horizontalLineToRelative(5.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                verticalLineToRelative(-5.0f)
                horizontalLineToRelative(-6.75f)
                close()
                moveTo(19.5f, 11.25f)
                verticalLineToRelative(-5.0f)
                curveToRelative(0.0f, -0.97f, -0.78f, -1.75f, -1.75f, -1.75f)
                horizontalLineToRelative(-5.0f)
                verticalLineToRelative(6.75f)
                horizontalLineToRelative(6.75f)
                close()
            }
        }
        return _tableSimple!!
    }

private var _tableSimple: ImageVector? = null
