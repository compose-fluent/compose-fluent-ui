

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ImageArrowBack: ImageVector
    get() {
        if (_imageArrowBack != null) {
            return _imageArrowBack!!
        }
        _imageArrowBack = fluentIcon(name = "Regular.ImageArrowBack") {
            fluentPath {
                moveTo(12.0f, 6.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 11.0f, 0.0f)
                close()
                moveTo(5.35f, 4.35f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, false, -0.7f, -0.7f)
                lineToRelative(-1.5f, 1.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 0.0f, 0.7f)
                lineToRelative(1.5f, 1.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, false, 0.7f, -0.7f)
                lineTo(4.71f, 6.0f)
                horizontalLineToRelative(2.04f)
                curveTo(7.99f, 6.0f, 9.0f, 7.0f, 9.0f, 8.25f)
                verticalLineToRelative(0.25f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 1.0f, 0.0f)
                verticalLineToRelative(-0.25f)
                curveTo(10.0f, 6.45f, 8.54f, 5.0f, 6.75f, 5.0f)
                lineTo(4.71f, 5.0f)
                lineToRelative(0.64f, -0.65f)
                close()
                moveTo(3.0f, 11.98f)
                curveToRelative(0.46f, 0.3f, 0.97f, 0.53f, 1.5f, 0.7f)
                verticalLineToRelative(5.07f)
                curveToRelative(0.0f, 0.2f, 0.04f, 0.4f, 0.1f, 0.6f)
                lineToRelative(5.83f, -5.7f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 3.02f, -0.12f)
                lineToRelative(0.12f, 0.11f)
                lineToRelative(5.83f, 5.7f)
                curveToRelative(0.06f, -0.18f, 0.1f, -0.38f, 0.1f, -0.59f)
                lineTo(19.5f, 6.25f)
                curveToRelative(0.0f, -0.97f, -0.78f, -1.75f, -1.75f, -1.75f)
                horizontalLineToRelative(-5.06f)
                arcToRelative(6.47f, 6.47f, 0.0f, false, false, -0.71f, -1.5f)
                horizontalLineToRelative(5.77f)
                curveTo(19.55f, 3.0f, 21.0f, 4.46f, 21.0f, 6.25f)
                verticalLineToRelative(11.5f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                lineTo(6.25f, 21.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, true, 3.0f, 17.75f)
                verticalLineToRelative(-5.77f)
                close()
                moveTo(18.33f, 19.4f)
                lineTo(12.53f, 13.71f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.97f, -0.07f)
                lineToRelative(-0.08f, 0.07f)
                lineToRelative(-5.81f, 5.7f)
                curveToRelative(0.18f, 0.06f, 0.38f, 0.09f, 0.58f, 0.09f)
                horizontalLineToRelative(11.5f)
                curveToRelative(0.2f, 0.0f, 0.4f, -0.03f, 0.58f, -0.1f)
                close()
                moveTo(15.25f, 6.5f)
                arcToRelative(2.25f, 2.25f, 0.0f, true, true, 0.0f, 4.5f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, 0.0f, -4.5f)
                close()
                moveTo(15.25f, 8.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 0.0f, 1.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                close()
            }
        }
        return _imageArrowBack!!
    }

private var _imageArrowBack: ImageVector? = null
