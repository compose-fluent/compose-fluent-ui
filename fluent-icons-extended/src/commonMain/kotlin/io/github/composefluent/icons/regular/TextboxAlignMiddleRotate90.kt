

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TextboxAlignMiddleRotate90: ImageVector
    get() {
        if (_textboxAlignMiddleRotate90 != null) {
            return _textboxAlignMiddleRotate90!!
        }
        _textboxAlignMiddleRotate90 = fluentIcon(name = "Regular.TextboxAlignMiddleRotate90") {
            fluentPath {
                moveTo(14.0f, 7.0f)
                verticalLineToRelative(10.0f)
                curveToRelative(0.0f, 0.55f, -0.34f, 1.0f, -0.75f, 1.0f)
                curveToRelative(-0.38f, 0.0f, -0.7f, -0.38f, -0.74f, -0.86f)
                lineTo(12.5f, 17.0f)
                lineTo(12.5f, 7.0f)
                curveToRelative(0.0f, -0.55f, 0.34f, -1.0f, 0.75f, -1.0f)
                curveToRelative(0.38f, 0.0f, 0.7f, 0.38f, 0.74f, 0.86f)
                lineTo(14.0f, 7.0f)
                close()
                moveTo(11.0f, 17.0f)
                lineTo(11.0f, 6.86f)
                curveToRelative(-0.06f, -0.48f, -0.37f, -0.86f, -0.75f, -0.86f)
                curveToRelative(-0.41f, 0.0f, -0.75f, 0.45f, -0.75f, 1.0f)
                verticalLineToRelative(10.14f)
                curveToRelative(0.06f, 0.48f, 0.37f, 0.86f, 0.75f, 0.86f)
                curveToRelative(0.41f, 0.0f, 0.75f, -0.45f, 0.75f, -1.0f)
                close()
                moveTo(18.25f, 21.0f)
                arcTo(2.75f, 2.75f, 0.0f, false, false, 21.0f, 18.25f)
                lineTo(21.0f, 5.75f)
                arcTo(2.75f, 2.75f, 0.0f, false, false, 18.25f, 3.0f)
                lineTo(5.75f, 3.0f)
                arcTo(2.75f, 2.75f, 0.0f, false, false, 3.0f, 5.75f)
                verticalLineToRelative(12.5f)
                arcTo(2.75f, 2.75f, 0.0f, false, false, 5.75f, 21.0f)
                horizontalLineToRelative(12.5f)
                close()
                moveTo(19.5f, 5.75f)
                verticalLineToRelative(12.5f)
                curveToRelative(0.0f, 0.69f, -0.56f, 1.25f, -1.25f, 1.25f)
                lineTo(5.75f, 19.5f)
                curveToRelative(-0.69f, 0.0f, -1.25f, -0.56f, -1.25f, -1.25f)
                lineTo(4.5f, 5.75f)
                curveToRelative(0.0f, -0.69f, 0.56f, -1.25f, 1.25f, -1.25f)
                horizontalLineToRelative(12.5f)
                curveToRelative(0.69f, 0.0f, 1.25f, 0.56f, 1.25f, 1.25f)
                close()
            }
        }
        return _textboxAlignMiddleRotate90!!
    }

private var _textboxAlignMiddleRotate90: ImageVector? = null
