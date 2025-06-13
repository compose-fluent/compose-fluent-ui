

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.StackArrowForward: ImageVector
    get() {
        if (_stackArrowForward != null) {
            return _stackArrowForward!!
        }
        _stackArrowForward = fluentIcon(name = "Regular.StackArrowForward") {
            fluentPath {
                moveTo(17.5f, 12.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, 0.0f, -11.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 0.0f, 11.0f)
                close()
                moveTo(19.35f, 3.65f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, -0.7f, 0.7f)
                lineToRelative(0.64f, 0.65f)
                horizontalLineToRelative(-2.04f)
                arcTo(3.25f, 3.25f, 0.0f, false, false, 14.0f, 8.25f)
                verticalLineToRelative(0.25f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 1.0f, 0.0f)
                verticalLineToRelative(-0.25f)
                curveTo(15.0f, 7.01f, 16.0f, 6.0f, 17.25f, 6.0f)
                horizontalLineToRelative(2.04f)
                lineToRelative(-0.64f, 0.65f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.7f, 0.7f)
                lineToRelative(1.5f, -1.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.0f, -0.7f)
                lineToRelative(-1.5f, -1.5f)
                close()
                moveTo(17.0f, 13.0f)
                verticalLineToRelative(-0.02f)
                arcToRelative(6.48f, 6.48f, 0.0f, false, true, -1.5f, -0.3f)
                lineTo(15.5f, 13.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.5f, 0.5f)
                lineTo(4.0f, 13.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -0.5f, -0.5f)
                lineTo(3.5f, 6.0f)
                curveToRelative(0.0f, -0.28f, 0.22f, -0.5f, 0.5f, -0.5f)
                horizontalLineToRelative(7.08f)
                curveToRelative(0.08f, -0.52f, 0.22f, -1.03f, 0.42f, -1.5f)
                lineTo(4.0f, 4.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, -2.0f, 2.0f)
                verticalLineToRelative(7.0f)
                curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                horizontalLineToRelative(11.0f)
                arcToRelative(2.0f, 2.0f, 0.0f, false, false, 2.0f, -2.0f)
                close()
                moveTo(18.0f, 12.98f)
                arcToRelative(6.47f, 6.47f, 0.0f, false, false, 1.5f, -0.3f)
                verticalLineToRelative(0.82f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, true, -4.0f, 4.0f)
                horizontalLineToRelative(-9.0f)
                arcTo(2.0f, 2.0f, 0.0f, false, true, 4.56f, 16.0f)
                lineTo(15.5f, 16.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, false, false, 2.5f, -2.5f)
                verticalLineToRelative(-0.52f)
                close()
                moveTo(22.0f, 11.19f)
                curveToRelative(-0.44f, 0.43f, -0.95f, 0.8f, -1.5f, 1.08f)
                verticalLineToRelative(1.98f)
                curveToRelative(0.0f, 2.35f, -1.9f, 4.25f, -4.25f, 4.25f)
                lineTo(7.06f, 18.5f)
                arcTo(2.0f, 2.0f, 0.0f, false, false, 9.0f, 20.0f)
                horizontalLineToRelative(7.25f)
                arcTo(5.75f, 5.75f, 0.0f, false, false, 22.0f, 14.25f)
                verticalLineToRelative(-3.06f)
                close()
            }
        }
        return _stackArrowForward!!
    }

private var _stackArrowForward: ImageVector? = null
