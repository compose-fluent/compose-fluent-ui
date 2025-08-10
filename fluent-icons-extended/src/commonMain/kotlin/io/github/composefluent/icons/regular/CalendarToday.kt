

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.CalendarToday: ImageVector
    get() {
        if (_calendarToday != null) {
            return _calendarToday!!
        }
        _calendarToday = fluentIcon(name = "Regular.CalendarToday") {
            fluentPath {
                moveTo(21.0f, 6.25f)
                curveTo(21.0f, 4.45f, 19.54f, 3.0f, 17.75f, 3.0f)
                lineTo(6.25f, 3.0f)
                arcTo(3.25f, 3.25f, 0.0f, false, false, 3.0f, 6.25f)
                verticalLineToRelative(11.5f)
                curveTo(3.0f, 19.55f, 4.46f, 21.0f, 6.25f, 21.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(-1.5f)
                horizontalLineToRelative(-4.0f)
                curveToRelative(-0.97f, 0.0f, -1.75f, -0.78f, -1.75f, -1.75f)
                lineTo(4.5f, 8.5f)
                horizontalLineToRelative(15.0f)
                verticalLineToRelative(9.25f)
                curveToRelative(0.0f, 0.97f, -0.78f, 1.75f, -1.75f, 1.75f)
                horizontalLineToRelative(-4.0f)
                lineTo(13.75f, 21.0f)
                horizontalLineToRelative(4.0f)
                curveToRelative(1.8f, 0.0f, 3.25f, -1.46f, 3.25f, -3.25f)
                lineTo(21.0f, 6.25f)
                close()
                moveTo(6.25f, 4.5f)
                horizontalLineToRelative(11.5f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                lineTo(19.5f, 7.0f)
                horizontalLineToRelative(-15.0f)
                verticalLineToRelative(-0.75f)
                curveToRelative(0.0f, -0.97f, 0.78f, -1.75f, 1.75f, -1.75f)
                close()
                moveTo(13.25f, 11.75f)
                arcToRelative(1.25f, 1.25f, 0.0f, true, true, -2.5f, 0.0f)
                arcToRelative(1.25f, 1.25f, 0.0f, false, true, 2.5f, 0.0f)
                close()
                moveTo(13.75f, 18.31f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.0f, -1.12f)
                lineToRelative(-2.25f, -2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -1.0f, 0.0f)
                lineToRelative(-2.25f, 2.0f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, 1.0f, 1.12f)
                lineToRelative(1.0f, -0.89f)
                verticalLineToRelative(3.83f)
                arcToRelative(0.75f, 0.75f, 0.0f, true, false, 1.5f, 0.0f)
                verticalLineToRelative(-3.83f)
                lineToRelative(1.0f, 0.9f)
                close()
            }
        }
        return _calendarToday!!
    }

private var _calendarToday: ImageVector? = null
