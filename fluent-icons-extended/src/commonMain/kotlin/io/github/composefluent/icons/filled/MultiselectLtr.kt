

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.MultiselectLtr: ImageVector
    get() {
        if (_multiselectLtr != null) {
            return _multiselectLtr!!
        }
        _multiselectLtr = fluentIcon(name = "Filled.MultiselectLtr") {
            fluentPath {
                moveTo(6.7f, 3.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.4f, 0.0f)
                lineTo(4.0f, 4.58f)
                lineToRelative(-0.3f, -0.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, -1.4f, 1.42f)
                lineToRelative(1.0f, 1.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 1.4f, 0.0f)
                lineToRelative(2.0f, -2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, false, 0.0f, -1.42f)
                close()
                moveTo(10.0f, 17.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 1.99f)
                lineTo(10.0f, 18.99f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 9.88f, 17.0f)
                lineTo(10.0f, 17.0f)
                close()
                moveTo(10.0f, 11.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(10.0f, 13.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(10.0f, 11.0f)
                close()
                moveTo(10.0f, 5.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 0.12f, 2.0f)
                lineTo(10.0f, 7.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.12f, -2.0f)
                lineTo(10.0f, 5.0f)
                close()
                moveTo(5.3f, 16.3f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.4f, 1.4f)
                lineToRelative(-2.0f, 2.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.4f, 0.0f)
                lineToRelative(-1.0f, -1.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 1.4f, -1.4f)
                lineToRelative(0.3f, 0.29f)
                lineToRelative(1.3f, -1.3f)
                close()
            }
        }
        return _multiselectLtr!!
    }

private var _multiselectLtr: ImageVector? = null
