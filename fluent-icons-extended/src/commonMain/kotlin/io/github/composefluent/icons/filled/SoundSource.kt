

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.SoundSource: ImageVector
    get() {
        if (_soundSource != null) {
            return _soundSource!!
        }
        _soundSource = fluentIcon(name = "Filled.SoundSource") {
            fluentPath {
                moveTo(3.5f, 12.0f)
                arcToRelative(8.5f, 8.5f, 0.0f, true, true, 14.76f, 5.75f)
                lineToRelative(1.0f, 1.13f)
                arcToRelative(10.0f, 10.0f, 0.0f, true, false, -14.5f, 0.0f)
                lineToRelative(0.98f, -1.13f)
                arcTo(8.47f, 8.47f, 0.0f, false, true, 3.5f, 12.0f)
                close()
                moveTo(19.25f, 12.13f)
                arcToRelative(7.1f, 7.1f, 0.0f, false, true, -1.78f, 4.71f)
                lineToRelative(-1.0f, -1.14f)
                arcToRelative(5.63f, 5.63f, 0.0f, true, false, -8.8f, -0.15f)
                lineTo(6.65f, 16.7f)
                arcToRelative(7.12f, 7.12f, 0.0f, true, true, 12.6f, -4.57f)
                close()
                moveTo(16.25f, 12.0f)
                curveToRelative(0.0f, 0.94f, -0.3f, 1.8f, -0.82f, 2.51f)
                lineToRelative(-1.03f, -1.17f)
                arcToRelative(2.75f, 2.75f, 0.0f, true, false, -4.8f, 0.0f)
                lineTo(8.57f, 14.5f)
                arcTo(4.25f, 4.25f, 0.0f, true, true, 16.25f, 12.0f)
                close()
                moveTo(12.0f, 12.5f)
                curveToRelative(0.22f, 0.0f, 0.42f, 0.1f, 0.56f, 0.26f)
                lineToRelative(7.0f, 8.0f)
                arcTo(0.75f, 0.75f, 0.0f, false, true, 19.0f, 22.0f)
                lineTo(5.0f, 22.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.56f, -1.24f)
                lineToRelative(7.0f, -8.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.56f, -0.26f)
                close()
            }
        }
        return _soundSource!!
    }

private var _soundSource: ImageVector? = null
