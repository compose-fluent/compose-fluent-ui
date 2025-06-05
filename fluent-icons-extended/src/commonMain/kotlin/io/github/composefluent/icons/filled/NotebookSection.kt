

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.NotebookSection: ImageVector
    get() {
        if (_notebookSection != null) {
            return _notebookSection!!
        }
        _notebookSection = fluentIcon(name = "Filled.NotebookSection") {
            fluentPath {
                moveTo(15.65f, 2.0f)
                horizontalLineToRelative(0.1f)
                curveToRelative(0.38f, 0.0f, 0.7f, 0.28f, 0.74f, 0.65f)
                lineToRelative(0.01f, 0.1f)
                verticalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.5f, 0.1f)
                verticalLineTo(20.0f)
                horizontalLineTo(9.25f)
                curveTo(8.01f, 20.0f, 7.0f, 18.99f, 7.0f, 17.75f)
                verticalLineTo(6.25f)
                curveTo(7.0f, 5.01f, 8.0f, 4.0f, 9.25f, 4.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(2.75f)
                curveToRelative(0.0f, -0.38f, 0.28f, -0.7f, 0.65f, -0.74f)
                lineToRelative(0.1f, -0.01f)
                horizontalLineToRelative(-0.1f)
                close()
            }
        }
        return _notebookSection!!
    }

private var _notebookSection: ImageVector? = null
