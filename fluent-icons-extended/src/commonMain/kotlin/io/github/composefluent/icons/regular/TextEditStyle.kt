

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.TextEditStyle: ImageVector
    get() {
        if (_textEditStyle != null) {
            return _textEditStyle!!
        }
        _textEditStyle = fluentIcon(name = "Regular.TextEditStyle") {
            fluentPath {
                moveTo(20.06f, 8.45f)
                arcToRelative(3.22f, 3.22f, 0.0f, false, true, 0.0f, 4.55f)
                lineToRelative(-7.12f, 7.1f)
                curveToRelative(-0.26f, 0.27f, -0.59f, 0.46f, -0.94f, 0.57f)
                lineToRelative(-4.3f, 1.29f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -1.24f, -1.27f)
                lineToRelative(1.36f, -4.22f)
                curveToRelative(0.11f, -0.34f, 0.3f, -0.65f, 0.56f, -0.9f)
                lineToRelative(7.13f, -7.13f)
                arcToRelative(3.22f, 3.22f, 0.0f, false, true, 4.55f, 0.0f)
                close()
                moveTo(16.56f, 9.5f)
                lineTo(9.44f, 16.63f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.19f, 0.3f)
                lineToRelative(-1.07f, 3.32f)
                lineToRelative(3.38f, -1.02f)
                curveToRelative(0.12f, -0.03f, 0.23f, -0.1f, 0.32f, -0.18f)
                lineTo(19.0f, 11.94f)
                arcToRelative(1.72f, 1.72f, 0.0f, true, false, -2.43f, -2.44f)
                close()
                moveTo(8.16f, 2.37f)
                lineTo(8.2f, 2.47f)
                lineTo(11.45f, 10.72f)
                lineTo(10.3f, 11.88f)
                lineTo(9.56f, 10.0f)
                lineTo(5.44f, 10.0f)
                lineToRelative(-1.0f, 2.52f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.87f, 0.45f)
                lineToRelative(-0.1f, -0.03f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, -0.45f, -0.87f)
                lineToRelative(0.03f, -0.1f)
                lineToRelative(3.76f, -9.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 1.34f, -0.1f)
                close()
                moveTo(7.5f, 4.79f)
                lineTo(6.04f, 8.5f)
                horizontalLineToRelative(2.92f)
                lineTo(7.5f, 4.8f)
                close()
            }
        }
        return _textEditStyle!!
    }

private var _textEditStyle: ImageVector? = null
