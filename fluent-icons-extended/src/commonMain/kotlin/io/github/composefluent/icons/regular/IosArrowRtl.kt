

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.IosArrowRtl: ImageVector
    get() {
        if (_iosArrowRtl != null) {
            return _iosArrowRtl!!
        }
        _iosArrowRtl = fluentIcon(name = "Regular.IosArrowRtl") {
            fluentPath {
                moveToRelative(19.7f, 12.0f)
                lineToRelative(-8.49f, -8.73f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, 1.08f, -1.04f)
                lineToRelative(9.0f, 9.25f)
                curveToRelative(0.28f, 0.29f, 0.28f, 0.75f, 0.0f, 1.04f)
                lineToRelative(-9.0f, 9.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.08f, -1.04f)
                lineToRelative(8.5f, -8.73f)
                close()
            }
        }
        return _iosArrowRtl!!
    }

private var _iosArrowRtl: ImageVector? = null
