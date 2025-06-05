

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.HeartCircle: ImageVector
    get() {
        if (_heartCircle != null) {
            return _heartCircle!!
        }
        _heartCircle = fluentIcon(name = "Regular.HeartCircle") {
            fluentPath {
                moveToRelative(11.7f, 16.86f)
                lineToRelative(-4.11f, -4.8f)
                arcToRelative(2.46f, 2.46f, 0.0f, false, true, 3.58f, -3.36f)
                lineToRelative(0.83f, 0.8f)
                lineToRelative(0.83f, -0.8f)
                arcToRelative(2.46f, 2.46f, 0.0f, false, true, 3.58f, 3.36f)
                lineToRelative(-4.1f, 4.8f)
                arcToRelative(0.4f, 0.4f, 0.0f, false, true, -0.61f, 0.0f)
                close()
                moveTo(22.0f, 12.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, true, -20.0f, 0.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, true, 20.0f, 0.0f)
                close()
                moveTo(20.5f, 12.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, true, false, -17.0f, 0.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, false, false, 17.0f, 0.0f)
                close()
            }
        }
        return _heartCircle!!
    }

private var _heartCircle: ImageVector? = null
