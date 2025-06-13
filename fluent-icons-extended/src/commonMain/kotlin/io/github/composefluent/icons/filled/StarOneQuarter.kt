

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.StarOneQuarter: ImageVector
    get() {
        if (_starOneQuarter != null) {
            return _starOneQuarter!!
        }
        _starOneQuarter = fluentIcon(name = "Filled.StarOneQuarter") {
            fluentPath {
                moveToRelative(9.0f, 6.72f)
                lineToRelative(-0.57f, 1.16f)
                lineToRelative(-5.27f, 0.77f)
                arcToRelative(1.35f, 1.35f, 0.0f, false, false, -0.75f, 2.3f)
                lineToRelative(3.81f, 3.72f)
                lineToRelative(-0.9f, 5.25f)
                arcToRelative(1.35f, 1.35f, 0.0f, false, false, 1.96f, 1.42f)
                lineToRelative(1.72f, -0.9f)
                verticalLineTo(6.72f)
                close()
            }
        }
        return _starOneQuarter!!
    }

private var _starOneQuarter: ImageVector? = null
