

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.IosArrowLtr: ImageVector
    get() {
        if (_iosArrowLtr != null) {
            return _iosArrowLtr!!
        }
        _iosArrowLtr = fluentIcon(name = "Filled.IosArrowLtr") {
            fluentPath {
                moveTo(12.73f, 3.69f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, -1.46f, -1.38f)
                lineToRelative(-8.5f, 9.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, 1.38f)
                lineToRelative(8.5f, 9.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 1.46f, -1.38f)
                lineTo(4.87f, 12.0f)
                lineToRelative(7.86f, -8.31f)
                close()
            }
        }
        return _iosArrowLtr!!
    }

private var _iosArrowLtr: ImageVector? = null
