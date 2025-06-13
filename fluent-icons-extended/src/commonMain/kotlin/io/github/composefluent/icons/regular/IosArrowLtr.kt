

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.IosArrowLtr: ImageVector
    get() {
        if (_iosArrowLtr != null) {
            return _iosArrowLtr!!
        }
        _iosArrowLtr = fluentIcon(name = "Regular.IosArrowLtr") {
            fluentPath {
                moveToRelative(4.3f, 12.0f)
                lineToRelative(8.49f, -8.73f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.08f, -1.04f)
                lineToRelative(-9.0f, 9.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, 1.04f)
                lineToRelative(9.0f, 9.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 1.08f, -1.04f)
                lineTo(4.29f, 12.0f)
                close()
            }
        }
        return _iosArrowLtr!!
    }

private var _iosArrowLtr: ImageVector? = null
