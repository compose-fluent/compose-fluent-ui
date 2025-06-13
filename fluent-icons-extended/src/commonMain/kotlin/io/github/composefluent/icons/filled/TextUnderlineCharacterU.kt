

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextUnderlineCharacterU: ImageVector
    get() {
        if (_textUnderlineCharacterU != null) {
            return _textUnderlineCharacterU!!
        }
        _textUnderlineCharacterU = fluentIcon(name = "Filled.TextUnderlineCharacterU") {
            fluentPath {
                moveTo(9.0f, 5.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -2.0f, 0.0f)
                verticalLineToRelative(6.5f)
                arcToRelative(5.0f, 5.0f, 0.0f, false, false, 10.0f, 0.0f)
                verticalLineTo(5.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, -2.0f, 0.0f)
                verticalLineToRelative(6.5f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, true, -6.0f, 0.0f)
                verticalLineTo(5.0f)
                close()
                moveTo(7.0f, 18.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, 2.0f)
                horizontalLineToRelative(10.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, false, 0.0f, -2.0f)
                horizontalLineTo(7.0f)
                close()
            }
        }
        return _textUnderlineCharacterU!!
    }

private var _textUnderlineCharacterU: ImageVector? = null
