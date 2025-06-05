

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.PeopleQueue: ImageVector
    get() {
        if (_peopleQueue != null) {
            return _peopleQueue!!
        }
        _peopleQueue = fluentIcon(name = "Filled.PeopleQueue") {
            fluentPath {
                moveTo(10.5f, 6.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, true, -6.0f, 0.0f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 6.0f, 0.0f)
                close()
                moveTo(4.75f, 10.0f)
                curveTo(3.78f, 10.0f, 3.0f, 10.78f, 3.0f, 11.75f)
                verticalLineToRelative(4.75f)
                arcToRelative(4.5f, 4.5f, 0.0f, true, false, 9.0f, 0.0f)
                verticalLineToRelative(-4.75f)
                curveToRelative(0.0f, -0.97f, -0.78f, -1.75f, -1.75f, -1.75f)
                horizontalLineToRelative(-5.5f)
                close()
                moveTo(10.86f, 20.85f)
                arcTo(5.5f, 5.5f, 0.0f, false, false, 13.0f, 16.5f)
                verticalLineToRelative(-4.75f)
                curveToRelative(0.0f, -0.66f, -0.24f, -1.27f, -0.63f, -1.75f)
                horizontalLineToRelative(2.38f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(4.75f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, -5.64f, 4.35f)
                close()
                moveTo(11.5f, 6.0f)
                curveToRelative(0.0f, 1.0f, -0.37f, 1.91f, -0.97f, 2.61f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, -5.23f)
                curveToRelative(0.6f, 0.7f, 0.97f, 1.62f, 0.97f, 2.62f)
                close()
                moveTo(15.36f, 20.85f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, false, 2.14f, -4.35f)
                verticalLineToRelative(-4.75f)
                curveToRelative(0.0f, -0.66f, -0.24f, -1.27f, -0.63f, -1.75f)
                horizontalLineToRelative(2.38f)
                curveToRelative(0.97f, 0.0f, 1.75f, 0.78f, 1.75f, 1.75f)
                verticalLineToRelative(4.75f)
                arcToRelative(4.5f, 4.5f, 0.0f, false, true, -5.64f, 4.35f)
                close()
                moveTo(16.0f, 6.0f)
                curveToRelative(0.0f, 1.0f, -0.37f, 1.91f, -0.97f, 2.61f)
                arcToRelative(3.0f, 3.0f, 0.0f, true, false, 0.0f, -5.23f)
                curveToRelative(0.6f, 0.7f, 0.97f, 1.62f, 0.97f, 2.62f)
                close()
            }
        }
        return _peopleQueue!!
    }

private var _peopleQueue: ImageVector? = null
