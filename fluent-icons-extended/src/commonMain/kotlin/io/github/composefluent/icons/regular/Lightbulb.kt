

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.Lightbulb: ImageVector
    get() {
        if (_lightbulb != null) {
            return _lightbulb!!
        }
        _lightbulb = fluentIcon(name = "Regular.Lightbulb") {
            fluentPath {
                moveTo(12.0f, 2.0f)
                curveToRelative(4.0f, 0.0f, 7.25f, 3.25f, 7.25f, 7.25f)
                curveToRelative(0.0f, 2.1f, -0.9f, 4.02f, -2.66f, 5.74f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.18f, 0.27f)
                lineToRelative(-0.03f, 0.1f)
                lineToRelative(-1.13f, 4.9f)
                arcToRelative(2.25f, 2.25f, 0.0f, false, true, -2.02f, 1.73f)
                lineToRelative(-0.17f, 0.01f)
                horizontalLineToRelative(-2.12f)
                curveToRelative(-0.99f, 0.0f, -1.85f, -0.65f, -2.14f, -1.58f)
                lineToRelative(-0.05f, -0.16f)
                lineToRelative(-1.13f, -4.9f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.2f, -0.37f)
                curveToRelative(-1.68f, -1.64f, -2.58f, -3.46f, -2.66f, -5.44f)
                lineToRelative(-0.01f, -0.3f)
                verticalLineToRelative(-0.24f)
                arcToRelative(7.25f, 7.25f, 0.0f, false, true, 7.25f, -7.0f)
                close()
                moveTo(14.12f, 18.5f)
                lineTo(9.88f, 18.5f)
                lineToRelative(0.33f, 1.42f)
                curveToRelative(0.07f, 0.3f, 0.33f, 0.53f, 0.63f, 0.57f)
                lineToRelative(0.1f, 0.01f)
                horizontalLineToRelative(2.12f)
                curveToRelative(0.31f, 0.0f, 0.59f, -0.2f, 0.7f, -0.48f)
                lineToRelative(0.03f, -0.1f)
                lineToRelative(0.33f, -1.42f)
                close()
                moveTo(12.0f, 3.5f)
                arcToRelative(5.75f, 5.75f, 0.0f, false, false, -5.75f, 5.53f)
                verticalLineToRelative(0.5f)
                curveToRelative(0.08f, 1.56f, 0.8f, 3.02f, 2.21f, 4.39f)
                curveToRelative(0.27f, 0.26f, 0.46f, 0.58f, 0.57f, 0.93f)
                lineToRelative(0.05f, 0.17f)
                lineToRelative(0.46f, 1.98f)
                horizontalLineToRelative(4.92f)
                lineToRelative(0.46f, -1.98f)
                curveToRelative(0.08f, -0.36f, 0.25f, -0.69f, 0.5f, -0.97f)
                lineToRelative(0.12f, -0.13f)
                curveToRelative(1.4f, -1.37f, 2.13f, -2.83f, 2.2f, -4.4f)
                lineToRelative(0.01f, -0.27f)
                verticalLineToRelative(-0.22f)
                arcTo(5.75f, 5.75f, 0.0f, false, false, 12.0f, 3.5f)
                close()
            }
        }
        return _lightbulb!!
    }

private var _lightbulb: ImageVector? = null
