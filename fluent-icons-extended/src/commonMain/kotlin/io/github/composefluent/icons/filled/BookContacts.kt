

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.BookContacts: ImageVector
    get() {
        if (_bookContacts != null) {
            return _bookContacts!!
        }
        _bookContacts = fluentIcon(name = "Filled.BookContacts") {
            fluentPath {
                moveTo(6.5f, 2.0f)
                arcTo(2.5f, 2.5f, 0.0f, false, false, 4.0f, 4.5f)
                verticalLineToRelative(15.0f)
                arcTo(2.5f, 2.5f, 0.0f, false, false, 6.5f, 22.0f)
                horizontalLineToRelative(13.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                lineTo(6.5f, 20.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.0f, -1.0f)
                horizontalLineToRelative(14.25f)
                curveToRelative(0.41f, 0.0f, 0.75f, -0.34f, 0.75f, -0.75f)
                lineTo(20.5f, 4.5f)
                arcTo(2.5f, 2.5f, 0.0f, false, false, 18.0f, 2.0f)
                lineTo(6.5f, 2.0f)
                close()
                moveTo(15.5f, 12.25f)
                verticalLineToRelative(0.5f)
                curveToRelative(0.0f, 1.0f, -1.38f, 1.75f, -3.25f, 1.75f)
                reflectiveCurveTo(9.0f, 13.75f, 9.0f, 12.75f)
                verticalLineToRelative(-0.5f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                horizontalLineToRelative(5.0f)
                curveToRelative(0.41f, 0.0f, 0.75f, 0.34f, 0.75f, 0.75f)
                close()
                moveTo(14.0f, 8.75f)
                arcToRelative(1.75f, 1.75f, 0.0f, true, true, -3.5f, 0.0f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, true, 3.5f, 0.0f)
                close()
            }
        }
        return _bookContacts!!
    }

private var _bookContacts: ImageVector? = null
