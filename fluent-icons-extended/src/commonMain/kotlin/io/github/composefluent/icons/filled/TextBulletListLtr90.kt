

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextBulletListLtr90: ImageVector
    get() {
        if (_textBulletListLtr90 != null) {
            return _textBulletListLtr90!!
        }
        _textBulletListLtr90 = fluentIcon(name = "Filled.TextBulletListLtr90") {
            fluentPath {
                moveTo(7.5f, 3.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, true, -3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, 3.0f, 0.0f)
                close()
                moveTo(7.0f, 7.5f)
                lineTo(7.0f, 21.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -2.0f, 0.12f)
                lineTo(5.0f, 7.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 2.0f, -0.12f)
                verticalLineToRelative(0.12f)
                close()
                moveTo(13.5f, 3.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, true, -3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, 3.0f, 0.0f)
                close()
                moveTo(13.0f, 7.5f)
                lineTo(13.0f, 21.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -2.0f, 0.12f)
                lineTo(11.0f, 7.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 2.0f, -0.12f)
                verticalLineToRelative(0.12f)
                close()
                moveTo(19.5f, 3.5f)
                arcToRelative(1.5f, 1.5f, 0.0f, true, true, -3.0f, 0.0f)
                arcToRelative(1.5f, 1.5f, 0.0f, false, true, 3.0f, 0.0f)
                close()
                moveTo(19.0f, 7.5f)
                lineTo(19.0f, 21.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -2.0f, 0.12f)
                lineTo(17.0f, 7.5f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 2.0f, -0.12f)
                verticalLineToRelative(0.12f)
                close()
            }
        }
        return _textBulletListLtr90!!
    }

private var _textBulletListLtr90: ImageVector? = null
