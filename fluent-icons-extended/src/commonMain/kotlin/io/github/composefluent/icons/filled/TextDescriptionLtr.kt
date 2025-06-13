

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextDescriptionLtr: ImageVector
    get() {
        if (_textDescriptionLtr != null) {
            return _textDescriptionLtr!!
        }
        _textDescriptionLtr = fluentIcon(name = "Filled.TextDescriptionLtr") {
            fluentPath {
                moveTo(3.0f, 17.0f)
                horizontalLineToRelative(12.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(3.0f, 19.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(15.0f, 17.0f)
                lineTo(3.0f, 17.0f)
                close()
                moveTo(3.0f, 13.0f)
                horizontalLineToRelative(18.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(3.0f, 15.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(21.0f, 13.0f)
                lineTo(3.0f, 13.0f)
                close()
                moveTo(3.0f, 9.0f)
                horizontalLineToRelative(18.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(3.0f, 11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(21.0f, 9.0f)
                lineTo(3.0f, 9.0f)
                close()
                moveTo(3.0f, 5.0f)
                horizontalLineToRelative(18.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(3.0f, 7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(21.0f, 5.0f)
                lineTo(3.0f, 5.0f)
                close()
            }
        }
        return _textDescriptionLtr!!
    }

private var _textDescriptionLtr: ImageVector? = null
