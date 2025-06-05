

package io.github.composefluent.icons.filled

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Filled.StarProhibited: ImageVector
    get() {
        if (_starProhibited != null) {
            return _starProhibited!!
        }
        _starProhibited = fluentIcon(name = "Filled.StarProhibited") {
            fluentPath {
                moveTo(10.79f, 3.1f)
                curveToRelative(0.5f, -1.0f, 1.92f, -1.0f, 2.42f, 0.0f)
                lineToRelative(2.36f, 4.78f)
                lineToRelative(5.27f, 0.77f)
                curveToRelative(1.1f, 0.16f, 1.55f, 1.52f, 0.75f, 2.3f)
                lineToRelative(-0.9f, 0.88f)
                arcToRelative(6.5f, 6.5f, 0.0f, false, false, -9.44f, 7.43f)
                lineToRelative(-3.97f, 2.08f)
                arcToRelative(1.35f, 1.35f, 0.0f, false, true, -1.96f, -1.42f)
                lineToRelative(0.9f, -5.25f)
                lineToRelative(-3.81f, -3.72f)
                curveToRelative(-0.8f, -0.78f, -0.36f, -2.14f, 0.75f, -2.3f)
                lineToRelative(5.27f, -0.77f)
                lineToRelative(2.36f, -4.78f)
                close()
                moveTo(22.99f, 17.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, true, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, true, 11.0f, 0.0f)
                close()
                moveTo(13.49f, 17.5f)
                curveToRelative(0.0f, 0.83f, 0.26f, 1.6f, 0.7f, 2.25f)
                lineToRelative(5.56f, -5.56f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, false, -6.25f, 3.3f)
                close()
                moveTo(17.49f, 21.5f)
                arcToRelative(4.0f, 4.0f, 0.0f, false, false, 3.32f, -6.25f)
                lineToRelative(-5.56f, 5.56f)
                curveToRelative(0.64f, 0.43f, 1.41f, 0.69f, 2.25f, 0.69f)
                close()
            }
        }
        return _starProhibited!!
    }

private var _starProhibited: ImageVector? = null
