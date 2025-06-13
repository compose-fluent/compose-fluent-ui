

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Crop: ImageVector
    get() {
        if (_crop != null) {
            return _crop!!
        }
        _crop = fluentIcon(name = "Filled.Crop") {
            fluentPath {
                moveTo(7.0f, 15.5f)
                curveToRelative(0.0f, 0.78f, 0.6f, 1.42f, 1.36f, 1.5f)
                horizontalLineTo(21.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                horizontalLineTo(19.0f)
                verticalLineToRelative(2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -2.0f, 0.12f)
                verticalLineTo(19.0f)
                horizontalLineTo(8.5f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 5.0f, 15.7f)
                verticalLineTo(7.0f)
                horizontalLineTo(3.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(3.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 2.0f, -0.12f)
                verticalLineTo(15.5f)
                close()
                moveTo(8.0f, 5.0f)
                horizontalLineToRelative(7.5f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 19.0f, 8.3f)
                verticalLineTo(16.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineTo(8.5f)
                curveToRelative(0.0f, -0.78f, -0.6f, -1.42f, -1.36f, -1.5f)
                horizontalLineTo(8.0f)
                verticalLineTo(5.0f)
                close()
            }
        }
        return _crop!!
    }

private var _crop: ImageVector? = null
