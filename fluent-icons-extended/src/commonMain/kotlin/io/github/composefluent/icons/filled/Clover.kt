

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Clover: ImageVector
    get() {
        if (_clover != null) {
            return _clover!!
        }
        _clover = fluentIcon(name = "Filled.Clover") {
            fluentPath {
                moveTo(6.75f, 2.0f)
                arcToRelative(4.75f, 4.75f, 0.0f, false, false, 0.0f, 9.5f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, -0.34f, 0.75f, -0.75f)
                verticalLineToRelative(-4.0f)
                arcTo(4.75f, 4.75f, 0.0f, false, false, 6.75f, 2.0f)
                close()
                moveTo(6.75f, 22.0f)
                arcToRelative(4.75f, 4.75f, 0.0f, true, true, 0.0f, -9.5f)
                horizontalLineToRelative(4.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                verticalLineToRelative(4.0f)
                arcTo(4.75f, 4.75f, 0.0f, false, true, 6.75f, 22.0f)
                close()
                moveTo(17.25f, 2.0f)
                arcToRelative(4.75f, 4.75f, 0.0f, true, true, 0.0f, 9.5f)
                horizontalLineToRelative(-4.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.75f, -0.75f)
                verticalLineToRelative(-4.0f)
                arcTo(4.75f, 4.75f, 0.0f, false, true, 17.25f, 2.0f)
                close()
                moveTo(17.25f, 22.0f)
                arcToRelative(4.75f, 4.75f, 0.0f, true, false, 0.0f, -9.5f)
                horizontalLineToRelative(-4.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                verticalLineToRelative(4.0f)
                arcTo(4.75f, 4.75f, 0.0f, false, false, 17.25f, 22.0f)
                close()
            }
        }
        return _clover!!
    }

private var _clover: ImageVector? = null
