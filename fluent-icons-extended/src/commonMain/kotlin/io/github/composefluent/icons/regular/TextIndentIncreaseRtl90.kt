

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TextIndentIncreaseRtl90: ImageVector
    get() {
        if (_textIndentIncreaseRtl90 != null) {
            return _textIndentIncreaseRtl90!!
        }
        _textIndentIncreaseRtl90 = fluentIcon(name = "Regular.TextIndentIncreaseRtl90") {
            fluentPath {
                moveTo(8.0f, 6.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                verticalLineToRelative(-9.0f)
                close()
                moveTo(14.78f, 21.28f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.07f, -0.98f)
                lineToRelative(-0.07f, -0.08f)
                lineToRelative(-2.0f, -2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.98f, -0.07f)
                lineToRelative(-0.08f, 0.07f)
                lineToRelative(-2.0f, 2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.98f, 1.13f)
                lineToRelative(0.08f, -0.07f)
                lineToRelative(1.47f, -1.47f)
                lineToRelative(1.47f, 1.47f)
                curveToRelative(0.3f, 0.3f, 0.77f, 0.3f, 1.06f, 0.0f)
                close()
                moveTo(13.0f, 3.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(12.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                verticalLineToRelative(-12.0f)
                close()
                moveTo(18.0f, 6.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                verticalLineToRelative(9.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.5f, 0.0f)
                verticalLineToRelative(-9.0f)
                close()
            }
        }
        return _textIndentIncreaseRtl90!!
    }

private var _textIndentIncreaseRtl90: ImageVector? = null
