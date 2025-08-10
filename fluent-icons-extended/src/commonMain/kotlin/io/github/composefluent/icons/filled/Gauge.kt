

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Gauge: ImageVector
    get() {
        if (_gauge != null) {
            return _gauge!!
        }
        _gauge = fluentIcon(name = "Filled.Gauge") {
            fluentPath {
                moveTo(12.0f, 22.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, false, 0.0f, -20.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, 0.0f, 20.0f)
                close()
                moveTo(15.88f, 6.67f)
                arcToRelative(0.63f, 0.63f, 0.0f, false, true, 0.96f, 0.76f)
                lineToRelative(-0.13f, 0.25f)
                arcToRelative(354.7f, 354.7f, 0.0f, false, true, -3.02f, 5.67f)
                curveToRelative(-0.12f, 0.2f, -0.24f, 0.4f, -0.32f, 0.5f)
                arcToRelative(1.88f, 1.88f, 0.0f, false, true, -2.94f, -2.33f)
                arcToRelative(37.2f, 37.2f, 0.0f, false, true, 1.1f, -1.05f)
                arcToRelative(154.39f, 154.39f, 0.0f, false, true, 4.14f, -3.62f)
                lineToRelative(0.2f, -0.18f)
                close()
                moveTo(7.93f, 17.13f)
                curveToRelative(-0.29f, 0.29f, -0.76f, 0.29f, -1.06f, 0.0f)
                arcToRelative(7.25f, 7.25f, 0.0f, false, true, 6.8f, -12.19f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -0.34f, 1.46f)
                arcToRelative(5.75f, 5.75f, 0.0f, false, false, -5.4f, 9.67f)
                curveToRelative(0.3f, 0.29f, 0.3f, 0.76f, 0.0f, 1.06f)
                close()
                moveTo(17.89f, 9.14f)
                curveToRelative(0.39f, -0.14f, 0.82f, 0.07f, 0.95f, 0.46f)
                arcToRelative(7.25f, 7.25f, 0.0f, false, true, -1.71f, 7.53f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, -1.06f, -1.06f)
                arcToRelative(5.75f, 5.75f, 0.0f, false, false, 1.36f, -5.97f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.46f, -0.96f)
                close()
            }
        }
        return _gauge!!
    }

private var _gauge: ImageVector? = null
