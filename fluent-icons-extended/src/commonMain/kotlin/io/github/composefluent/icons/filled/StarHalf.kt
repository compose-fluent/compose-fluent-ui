

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.StarHalf: ImageVector
    get() {
        if (_starHalf != null) {
            return _starHalf!!
        }
        _starHalf = fluentIcon(name = "Filled.StarHalf") {
            fluentPath {
                moveTo(12.0f, 2.35f)
                curveToRelative(-0.48f, 0.0f, -0.96f, 0.25f, -1.21f, 0.75f)
                lineTo(8.43f, 7.88f)
                lineToRelative(-5.27f, 0.77f)
                arcToRelative(1.35f, 1.35f, 0.0f, false, false, -0.75f, 2.3f)
                lineToRelative(3.81f, 3.72f)
                lineToRelative(-0.9f, 5.25f)
                arcToRelative(1.35f, 1.35f, 0.0f, false, false, 1.96f, 1.42f)
                lineTo(12.0f, 18.86f)
                verticalLineTo(2.35f)
                close()
            }
        }
        return _starHalf!!
    }

private var _starHalf: ImageVector? = null
