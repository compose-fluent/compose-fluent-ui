

package io.github.composefluent.icons.regular

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.fluentIcon
import io.github.composefluent.icons.fluentPath

public val Icons.Regular.ReceiptAdd: ImageVector
    get() {
        if (_receiptAdd != null) {
            return _receiptAdd!!
        }
        _receiptAdd = fluentIcon(name = "Regular.ReceiptAdd") {
            fluentPath {
                moveTo(5.0f, 6.25f)
                curveTo(5.0f, 5.01f, 6.0f, 4.0f, 7.25f, 4.0f)
                horizontalLineToRelative(8.5f)
                curveTo(16.99f, 4.0f, 18.0f, 5.0f, 18.0f, 6.25f)
                lineTo(18.0f, 14.0f)
                horizontalLineToRelative(3.5f)
                verticalLineToRelative(3.25f)
                curveToRelative(0.0f, 1.8f, -1.46f, 3.25f, -3.25f, 3.25f)
                horizontalLineToRelative(-5.98f)
                curveToRelative(0.24f, -0.47f, 0.43f, -0.97f, 0.56f, -1.5f)
                horizontalLineToRelative(3.67f)
                lineTo(16.5f, 6.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, -0.75f)
                horizontalLineToRelative(-8.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, false, -0.75f, 0.75f)
                lineTo(6.5f, 11.0f)
                curveToRelative(-0.52f, 0.0f, -1.02f, 0.06f, -1.5f, 0.17f)
                lineTo(5.0f, 6.25f)
                close()
                moveTo(14.25f, 13.0f)
                horizontalLineToRelative(-3.06f)
                arcTo(6.51f, 6.51f, 0.0f, false, false, 9.0f, 11.5f)
                horizontalLineToRelative(5.25f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                close()
                moveTo(18.0f, 19.0f)
                horizontalLineToRelative(0.25f)
                curveToRelative(0.97f, 0.0f, 1.75f, -0.78f, 1.75f, -1.75f)
                lineTo(20.0f, 15.5f)
                horizontalLineToRelative(-2.0f)
                lineTo(18.0f, 19.0f)
                close()
                moveTo(8.0f, 8.75f)
                curveToRelative(0.0f, -0.41f, 0.34f, -0.75f, 0.75f, -0.75f)
                horizontalLineToRelative(5.5f)
                arcToRelative(0.75f, 0.75f, 0.0f, false, true, 0.0f, 1.5f)
                horizontalLineToRelative(-5.5f)
                arcTo(0.75f, 0.75f, 0.0f, false, true, 8.0f, 8.75f)
                close()
                moveTo(12.0f, 17.5f)
                arcToRelative(5.5f, 5.5f, 0.0f, true, false, -11.0f, 0.0f)
                arcToRelative(5.5f, 5.5f, 0.0f, false, false, 11.0f, 0.0f)
                close()
                moveTo(7.0f, 18.0f)
                verticalLineToRelative(2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, true, -1.0f, 0.0f)
                lineTo(6.0f, 18.0f)
                lineTo(3.5f, 18.0f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.0f, -1.0f)
                lineTo(6.0f, 17.0f)
                verticalLineToRelative(-2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, true, true, 1.0f, 0.0f)
                lineTo(7.0f, 17.0f)
                horizontalLineToRelative(2.5f)
                arcToRelative(0.5f, 0.5f, 0.0f, false, true, 0.0f, 1.0f)
                lineTo(7.0f, 18.0f)
                close()
            }
        }
        return _receiptAdd!!
    }

private var _receiptAdd: ImageVector? = null
