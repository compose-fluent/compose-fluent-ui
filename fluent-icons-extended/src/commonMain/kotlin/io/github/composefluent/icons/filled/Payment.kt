

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.Payment: ImageVector
    get() {
        if (_payment != null) {
            return _payment!!
        }
        _payment = fluentIcon(name = "Filled.Payment") {
            fluentPath {
                moveTo(5.25f, 5.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, false, 2.0f, 8.25f)
                lineTo(2.0f, 9.5f)
                horizontalLineToRelative(20.0f)
                lineTo(22.0f, 8.25f)
                curveTo(22.0f, 6.45f, 20.54f, 5.0f, 18.75f, 5.0f)
                lineTo(5.25f, 5.0f)
                close()
                moveTo(22.0f, 11.0f)
                lineTo(2.0f, 11.0f)
                verticalLineToRelative(4.75f)
                curveTo(2.0f, 17.55f, 3.46f, 19.0f, 5.25f, 19.0f)
                horizontalLineToRelative(13.5f)
                curveToRelative(1.8f, 0.0f, 3.25f, -1.46f, 3.25f, -3.25f)
                lineTo(22.0f, 11.0f)
                close()
                moveTo(15.75f, 14.5f)
                horizontalLineToRelative(2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                horizontalLineToRelative(-2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, -1.5f)
                close()
            }
        }
        return _payment!!
    }

private var _payment: ImageVector? = null
