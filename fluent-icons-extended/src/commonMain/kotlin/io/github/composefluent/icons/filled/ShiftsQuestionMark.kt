

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.ShiftsQuestionMark: ImageVector
    get() {
        if (_shiftsQuestionMark != null) {
            return _shiftsQuestionMark!!
        }
        _shiftsQuestionMark = fluentIcon(name = "Filled.ShiftsQuestionMark") {
            fluentPath {
                moveTo(6.5f, 12.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, 0.0f, 11.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 0.0f, -11.0f)
                close()
                moveTo(6.5f, 19.88f)
                arcToRelative(0.62f, 0.62f, 0.0f, true, false, 0.0f, 1.25f)
                arcToRelative(0.62f, 0.62f, 0.0f, false, false, 0.0f, -1.25f)
                close()
                moveTo(17.75f, 3.0f)
                curveTo(19.55f, 3.0f, 21.0f, 4.46f, 21.0f, 6.25f)
                verticalLineToRelative(11.5f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                horizontalLineToRelative(-5.77f)
                arcToRelative(6.47f, 6.47f, 0.0f, false, false, -0.37f, -7.51f)
                horizontalLineToRelative(0.07f)
                lineToRelative(0.07f, 0.01f)
                horizontalLineToRelative(4.6f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 0.0f, -1.5f)
                lineTo(12.5f, 12.0f)
                lineTo(12.5f, 6.65f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.5f, 0.0f)
                lineTo(11.0f, 12.8f)
                arcToRelative(6.48f, 6.48f, 0.0f, false, false, -8.0f, -0.79f)
                lineTo(3.0f, 6.25f)
                curveTo(3.0f, 4.45f, 4.46f, 3.0f, 6.25f, 3.0f)
                horizontalLineToRelative(11.5f)
                close()
                moveTo(6.5f, 14.0f)
                curveToRelative(-1.05f, 0.0f, -1.86f, 0.82f, -1.85f, 1.96f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 1.0f, -0.01f)
                curveToRelative(0.0f, -0.58f, 0.36f, -0.95f, 0.85f, -0.95f)
                curveToRelative(0.47f, 0.0f, 0.85f, 0.4f, 0.85f, 0.95f)
                curveToRelative(0.0f, 0.23f, -0.07f, 0.4f, -0.31f, 0.68f)
                lineToRelative(-0.1f, 0.11f)
                lineToRelative(-0.27f, 0.3f)
                curveToRelative(-0.48f, 0.53f, -0.67f, 0.89f, -0.67f, 1.46f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, false, 1.0f, 0.0f)
                curveToRelative(0.0f, -0.23f, 0.08f, -0.4f, 0.32f, -0.7f)
                lineToRelative(0.1f, -0.1f)
                lineToRelative(0.27f, -0.3f)
                curveToRelative(0.48f, -0.53f, 0.66f, -0.88f, 0.66f, -1.45f)
                curveToRelative(0.0f, -1.1f, -0.82f, -1.95f, -1.85f, -1.95f)
                close()
            }
        }
        return _shiftsQuestionMark!!
    }

private var _shiftsQuestionMark: ImageVector? = null
