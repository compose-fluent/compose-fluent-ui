

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.MusicNoteOff2: ImageVector
    get() {
        if (_musicNoteOff2 != null) {
            return _musicNoteOff2!!
        }
        _musicNoteOff2 = fluentIcon(name = "Filled.MusicNoteOff2") {
            fluentPath {
                moveTo(8.5f, 9.56f)
                verticalLineToRelative(6.07f)
                arcToRelative(3.5f, 3.5f, 0.0f, true, false, 1.5f, 2.62f)
                verticalLineToRelative(-7.19f)
                lineToRelative(3.55f, 3.55f)
                arcToRelative(3.5f, 3.5f, 0.0f, false, false, 4.84f, 4.84f)
                lineToRelative(2.33f, 2.33f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.06f, -1.06f)
                lineTo(3.28f, 2.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.06f, 1.06f)
                lineTo(8.5f, 9.56f)
                close()
                moveTo(16.5f, 13.0f)
                lineTo(16.2f, 13.01f)
                lineTo(19.99f, 16.81f)
                arcToRelative(3.54f, 3.54f, 0.0f, false, false, 0.01f, -0.56f)
                lineTo(20.0f, 2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.97f, -0.72f)
                lineToRelative(-10.0f, 3.0f)
                curveToRelative(-0.2f, 0.06f, -0.35f, 0.2f, -0.44f, 0.37f)
                lineToRelative(4.1f, 4.1f)
                lineToRelative(5.81f, -1.74f)
                verticalLineToRelative(5.87f)
                arcToRelative(3.48f, 3.48f, 0.0f, false, false, -2.0f, -0.63f)
                close()
            }
        }
        return _musicNoteOff2!!
    }

private var _musicNoteOff2: ImageVector? = null
