

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.TextboxAlignTop: ImageVector
    get() {
        if (_textboxAlignTop != null) {
            return _textboxAlignTop!!
        }
        _textboxAlignTop = fluentIcon(name = "Filled.TextboxAlignTop") {
            fluentPath {
                moveTo(18.25f, 3.0f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 21.0f, 5.75f)
                verticalLineToRelative(12.5f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 18.25f, 21.0f)
                lineTo(5.75f, 21.0f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 3.0f, 18.25f)
                lineTo(3.0f, 5.75f)
                arcTo(2.75f, 2.75f, 0.0f, false, true, 5.75f, 3.0f)
                horizontalLineToRelative(12.5f)
                close()
                moveTo(7.0f, 7.48f)
                lineToRelative(-0.14f, 0.01f)
                curveToRelative(-0.48f, 0.05f, -0.86f, 0.36f, -0.86f, 0.74f)
                curveToRelative(0.0f, 0.42f, 0.45f, 0.75f, 1.0f, 0.75f)
                horizontalLineToRelative(10.14f)
                curveToRelative(0.48f, -0.05f, 0.86f, -0.37f, 0.86f, -0.75f)
                curveToRelative(0.0f, -0.41f, -0.45f, -0.75f, -1.0f, -0.75f)
                lineTo(7.0f, 7.48f)
                close()
                moveTo(17.0f, 10.48f)
                lineTo(7.0f, 10.48f)
                lineToRelative(-0.14f, 0.01f)
                curveToRelative(-0.48f, 0.05f, -0.86f, 0.36f, -0.86f, 0.74f)
                curveToRelative(0.0f, 0.42f, 0.45f, 0.75f, 1.0f, 0.75f)
                horizontalLineToRelative(10.14f)
                curveToRelative(0.48f, -0.05f, 0.86f, -0.37f, 0.86f, -0.75f)
                curveToRelative(0.0f, -0.41f, -0.45f, -0.75f, -1.0f, -0.75f)
                close()
            }
        }
        return _textboxAlignTop!!
    }

private var _textboxAlignTop: ImageVector? = null
