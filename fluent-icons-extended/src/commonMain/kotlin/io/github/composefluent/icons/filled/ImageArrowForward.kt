

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ImageArrowForward: ImageVector
    get() {
        if (_imageArrowForward != null) {
            return _imageArrowForward!!
        }
        _imageArrowForward = fluentIcon(name = "Filled.ImageArrowForward") {
            fluentPath {
                moveTo(1.0f, 6.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, false, 11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, false, -11.0f, 0.0f)
                close()
                moveTo(7.65f, 4.35f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, true, 0.7f, -0.7f)
                lineToRelative(1.5f, 1.5f)
                curveToRelative(0.2f, 0.2f, 0.2f, 0.5f, 0.0f, 0.7f)
                lineToRelative(-1.5f, 1.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, true, -0.7f, -0.7f)
                lineTo(8.29f, 6.0f)
                lineTo(6.25f, 6.0f)
                curveTo(5.01f, 6.0f, 4.0f, 7.0f, 4.0f, 8.25f)
                verticalLineToRelative(0.25f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, -1.0f, 0.0f)
                verticalLineToRelative(-0.25f)
                curveTo(3.0f, 6.45f, 4.46f, 5.0f, 6.25f, 5.0f)
                horizontalLineToRelative(2.04f)
                lineToRelative(-0.64f, -0.65f)
                close()
                moveTo(15.75f, 7.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, true, 0.0f, 1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, -1.5f)
                close()
                moveTo(13.0f, 6.5f)
                arcToRelative(6.5f, 6.5f, 0.0f, false, true, -10.0f, 5.48f)
                verticalLineToRelative(5.77f)
                curveToRelative(0.0f, 0.63f, 0.18f, 1.21f, 0.49f, 1.7f)
                lineToRelative(6.93f, -6.8f)
                lineToRelative(0.14f, -0.13f)
                curveToRelative(0.83f, -0.7f, 2.05f, -0.7f, 2.89f, 0.01f)
                lineToRelative(0.13f, 0.12f)
                lineToRelative(6.93f, 6.8f)
                curveToRelative(0.31f, -0.49f, 0.49f, -1.07f, 0.49f, -1.7f)
                lineTo(21.0f, 6.25f)
                curveTo(21.0f, 4.45f, 19.54f, 3.0f, 17.75f, 3.0f)
                horizontalLineToRelative(-5.77f)
                arcTo(6.47f, 6.47f, 0.0f, false, true, 13.0f, 6.5f)
                close()
                moveTo(15.75f, 6.0f)
                arcToRelative(2.25f, 2.25f, 0.0f, true, true, 0.0f, 4.5f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 0.0f, -4.5f)
                close()
                moveTo(11.47f, 13.72f)
                lineTo(11.56f, 13.65f)
                curveToRelative(0.26f, -0.2f, 0.61f, -0.2f, 0.87f, -0.01f)
                lineToRelative(0.1f, 0.08f)
                lineToRelative(6.92f, 6.8f)
                curveToRelative(-0.5f, 0.3f, -1.08f, 0.48f, -1.7f, 0.48f)
                lineTo(6.25f, 21.0f)
                curveToRelative(-0.62f, 0.0f, -1.2f, -0.18f, -1.7f, -0.48f)
                lineToRelative(6.92f, -6.8f)
                close()
            }
        }
        return _imageArrowForward!!
    }

private var _imageArrowForward: ImageVector? = null
