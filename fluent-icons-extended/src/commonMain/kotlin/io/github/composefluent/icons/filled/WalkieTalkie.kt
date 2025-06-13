

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.WalkieTalkie: ImageVector
    get() {
        if (_walkieTalkie != null) {
            return _walkieTalkie!!
        }
        _walkieTalkie = fluentIcon(name = "Filled.WalkieTalkie") {
            fluentPath {
                moveTo(9.5f, 7.5f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(5.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(-5.0f)
                close()
                moveTo(8.75f, 1.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                lineTo(9.5f, 3.0f)
                horizontalLineToRelative(7.25f)
                curveTo(17.99f, 3.0f, 19.0f, 4.0f, 19.0f, 5.25f)
                lineTo(19.0f, 14.0f)
                curveToRelative(0.0f, 0.19f, -0.07f, 0.37f, -0.2f, 0.5f)
                lineToRelative(-1.3f, 1.42f)
                verticalLineToRelative(3.83f)
                curveToRelative(0.0f, 1.24f, -1.0f, 2.25f, -2.25f, 2.25f)
                horizontalLineToRelative(-6.5f)
                curveToRelative(-1.24f, 0.0f, -2.25f, -1.0f, -2.25f, -2.25f)
                verticalLineToRelative(-3.83f)
                lineTo(5.2f, 14.5f)
                arcTo(0.75f, 0.75f, 0.0f, false, true, 5.0f, 14.0f)
                lineTo(5.0f, 5.25f)
                curveTo(5.0f, 4.01f, 6.0f, 3.0f, 7.25f, 3.0f)
                lineTo(8.0f, 3.0f)
                lineTo(8.0f, 1.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                close()
                moveTo(8.0f, 6.75f)
                verticalLineToRelative(4.5f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(6.5f)
                curveToRelative(0.41f, 0.0f, 0.75f, -0.34f, 0.75f, -0.75f)
                verticalLineToRelative(-4.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                horizontalLineToRelative(-6.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                close()
                moveTo(9.5f, 14.75f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                horizontalLineToRelative(3.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                horizontalLineToRelative(-3.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                close()
            }
        }
        return _walkieTalkie!!
    }

private var _walkieTalkie: ImageVector? = null
