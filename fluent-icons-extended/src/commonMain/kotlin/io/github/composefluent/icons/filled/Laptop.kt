

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Laptop: ImageVector
    get() {
        if (_laptop != null) {
            return _laptop!!
        }
        _laptop = fluentIcon(name = "Filled.Laptop") {
            fluentPath {
                moveTo(2.75f, 16.5f)
                horizontalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.1f, 1.5f)
                horizontalLineTo(2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.1f, -1.5f)
                horizontalLineToRelative(18.6f)
                horizontalLineToRelative(-18.5f)
                close()
                moveTo(18.25f, 5.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(7.5f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                horizontalLineTo(5.75f)
                curveTo(4.78f, 16.0f, 4.0f, 15.22f, 4.0f, 14.25f)
                verticalLineToRelative(-7.5f)
                curveTo(4.0f, 5.78f, 4.78f, 5.0f, 5.75f, 5.0f)
                horizontalLineToRelative(12.5f)
                close()
            }
        }
        return _laptop!!
    }

private var _laptop: ImageVector? = null
