

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.MusicNoteOff1: ImageVector
    get() {
        if (_musicNoteOff1 != null) {
            return _musicNoteOff1!!
        }
        _musicNoteOff1 = fluentIcon(name = "Filled.MusicNoteOff1") {
            fluentPath {
                moveToRelative(12.0f, 13.06f)
                lineToRelative(8.72f, 8.72f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.06f, -1.06f)
                lineTo(3.28f, 2.22f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, -1.06f, 1.06f)
                lineToRelative(8.28f, 8.28f)
                verticalLineToRelative(3.32f)
                arcToRelative(4.0f, 4.0f, 0.0f, true, false, 1.5f, 3.37f)
                verticalLineToRelative(-5.19f)
                close()
                moveTo(18.99f, 11.46f)
                lineTo(12.02f, 8.84f)
                lineTo(10.5f, 7.32f)
                lineTo(10.5f, 2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.01f, -0.7f)
                lineToRelative(6.38f, 2.39f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 20.0f, 7.48f)
                verticalLineToRelative(3.27f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.01f, 0.7f)
                close()
            }
        }
        return _musicNoteOff1!!
    }

private var _musicNoteOff1: ImageVector? = null
