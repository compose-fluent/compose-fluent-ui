

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.FormMultiple: ImageVector
    get() {
        if (_formMultiple != null) {
            return _formMultiple!!
        }
        _formMultiple = fluentIcon(name = "Regular.FormMultiple") {
            fluentPath {
                moveTo(6.0f, 12.25f)
                arcToRelative(2.25f, 2.25f, 0.0f, true, true, 4.5f, 0.0f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, -4.5f, 0.0f)
                close()
                moveTo(8.25f, 11.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 0.0f, 1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                close()
            }
            fluentPath {
                moveTo(11.5f, 12.25f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                horizontalLineToRelative(2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                horizontalLineToRelative(-2.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.75f, -0.75f)
                close()
            }
            fluentPath {
                moveTo(6.0f, 7.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                horizontalLineToRelative(8.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                horizontalLineToRelative(-8.0f)
                arcTo(0.75f, 0.75f, 0.0f, false, true, 6.0f, 7.75f)
                close()
            }
            fluentPath {
                moveTo(6.25f, 3.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, false, 3.0f, 6.25f)
                verticalLineToRelative(9.0f)
                curveToRelative(0.0f, 1.8f, 1.46f, 3.25f, 3.25f, 3.25f)
                horizontalLineToRelative(9.0f)
                curveToRelative(1.8f, 0.0f, 3.25f, -1.46f, 3.25f, -3.25f)
                verticalLineToRelative(-9.0f)
                curveToRelative(0.0f, -1.8f, -1.46f, -3.25f, -3.25f, -3.25f)
                horizontalLineToRelative(-9.0f)
                close()
                moveTo(4.5f, 6.25f)
                curveToRelative(0.0f, -0.97f, 0.78f, -1.75f, 1.75f, -1.75f)
                horizontalLineToRelative(9.0f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(9.0f)
                arcTo(1.75f, 1.75f, 0.0f, false, true, 15.25f, 17.0f)
                horizontalLineToRelative(-9.0f)
                arcToRelative(1.75f, 1.75f, 0.0f, false, true, -1.75f, -1.75f)
                verticalLineToRelative(-9.0f)
                close()
            }
            fluentPath {
                moveTo(8.75f, 21.0f)
                curveToRelative(-1.15f, 0.0f, -2.16f, -0.6f, -2.74f, -1.5f)
                horizontalLineToRelative(9.74f)
                arcToRelative(3.75f, 3.75f, 0.0f, false, false, 3.75f, -3.75f)
                verticalLineTo(6.01f)
                curveToRelative(0.9f, 0.58f, 1.5f, 1.59f, 1.5f, 2.74f)
                verticalLineToRelative(7.0f)
                curveToRelative(0.0f, 2.9f, -2.35f, 5.25f, -5.25f, 5.25f)
                horizontalLineToRelative(-7.0f)
                close()
            }
        }
        return _formMultiple!!
    }

private var _formMultiple: ImageVector? = null
