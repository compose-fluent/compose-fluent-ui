

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.HeartCircle: ImageVector
    get() {
        if (_heartCircle != null) {
            return _heartCircle!!
        }
        _heartCircle = fluentIcon(name = "Filled.HeartCircle") {
            fluentPath {
                moveTo(12.0f, 22.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, false, 0.0f, -20.0f)
                arcToRelative(10.0f, 10.0f, 0.0f, false, false, 0.0f, 20.0f)
                close()
                moveTo(11.7f, 16.86f)
                lineTo(7.59f, 12.06f)
                arcToRelative(2.46f, 2.46f, 0.0f, false, true, 3.58f, -3.36f)
                lineToRelative(0.83f, 0.8f)
                lineToRelative(0.83f, -0.8f)
                arcToRelative(2.46f, 2.46f, 0.0f, false, true, 3.58f, 3.36f)
                lineToRelative(-4.1f, 4.8f)
                arcToRelative(0.4f, 0.4f, 0.0f, false, true, -0.61f, 0.0f)
                close()
            }
        }
        return _heartCircle!!
    }

private var _heartCircle: ImageVector? = null
