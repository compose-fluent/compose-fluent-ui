

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.NotebookSubsection: ImageVector
    get() {
        if (_notebookSubsection != null) {
            return _notebookSubsection!!
        }
        _notebookSubsection = fluentIcon(name = "Regular.NotebookSubsection") {
            fluentPath {
                moveTo(9.0f, 2.0f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, false, -4.5f, 4.5f)
                verticalLineToRelative(9.4f)
                curveToRelative(0.0f, 0.98f, 0.63f, 1.81f, 1.5f, 2.12f)
                verticalLineTo(6.5f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 3.0f, -3.0f)
                horizontalLineToRelative(5.0f)
                arcTo(2.25f, 2.25f, 0.0f, false, false, 11.88f, 2.0f)
                horizontalLineTo(9.0f)
                close()
            }
            fluentPath {
                moveTo(7.0f, 6.75f)
                curveTo(7.0f, 5.51f, 8.0f, 4.5f, 9.25f, 4.5f)
                lineTo(15.0f, 4.5f)
                lineTo(15.0f, 2.75f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.5f, 0.0f)
                verticalLineToRelative(18.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -1.5f, 0.0f)
                lineTo(15.0f, 20.0f)
                lineTo(9.25f, 20.0f)
                curveTo(8.01f, 20.0f, 7.0f, 19.0f, 7.0f, 17.75f)
                verticalLineToRelative(-11.0f)
                close()
                moveTo(15.0f, 18.5f)
                lineTo(15.0f, 6.0f)
                lineTo(9.25f, 6.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                verticalLineToRelative(11.0f)
                curveToRelative(0.0f, 0.41f, 0.34f, 0.75f, 0.75f, 0.75f)
                lineTo(15.0f, 18.5f)
                close()
            }
        }
        return _notebookSubsection!!
    }

private var _notebookSubsection: ImageVector? = null
