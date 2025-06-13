package io.github.composefluent

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Aligns style properties related to geometry, such as [Shape] and [CornerRadius].
 *
 * This interface serves as a common structure for defining geometric properties
 * across different types of visual elements within a UI. It provides a consistent
 * way to access geometry settings for different design use cases:
 * - [overlay]: Geometry for elements that appear above other content, such as dialogs or flyouts.
 * - [control]: Geometry for interactive controls, such as buttons, text fields, or checkboxes.
 * - [intersectionEdge]: Geometry for elements at the intersection of other elements,
 *   typically used for visual separation or emphasis at boundaries.
 *
 * The type parameter [Type] allows this interface to be used with different data types
 * representing geometric properties, such as [Shape] or [Dp] for corner radius.
 *
 * Based on the Microsoft Fluent Design System principles for geometry:
 * See [https://learn.microsoft.com/en-us/windows/apps/design/signature-experiences/geometry]
 */
interface Geometry<Type> {
    val overlay: Type
    val control: Type
    val intersectionEdge: Type
}

/**
 * Defines the shapes used for different elements in the Fluent design system.
 *
 * This class aligns with the principles described in the Microsoft documentation on Geometry.
 * See [https://learn.microsoft.com/en-us/windows/apps/design/signature-experiences/geometry]
 * for more details.
 *
 * @property overlay The shape used for elements like overlays.
 * @property control The shape used for control elements like buttons and text fields.
 * @property intersectionEdge The shape used for elements at the intersection edge.
 */
@Immutable
class Shapes(
    override val overlay: Shape,
    override val control: Shape,
    override val intersectionEdge: Shape,
): Geometry<Shape>

internal val LocalShapes = staticCompositionLocalOf {
    Shapes(
        overlay = createShape(overlayCornerRadius),
        control = createShape(controlCornerRadius),
        intersectionEdge = createShape(intersectionEdgeCornerRadius)
    )
}

internal fun createShape(cornerRadius: Dp): Shape {
    return if (cornerRadius == 0.dp) {
        RectangleShape
    } else {
        RoundedCornerShape(cornerRadius)
    }
}

/**
 * Converts a [CornerRadius] object to a [Shapes] object.
 *
 * This function maps the overlay, control, and intersection edge [Dp] values from the [CornerRadius]
 * to corresponding [Shape] objects using the [createShape] function.
 *
 * @return A [Shapes] object representing the corner radius as shapes.
 */
fun CornerRadius.toShapes(): Shapes {
    return Shapes(
        overlay = createShape(overlay),
        control = createShape(control),
        intersectionEdge = createShape(intersectionEdge)
    )
}

/**
 * Corner radius values for different geometric elements in the UI.
 *
 * These values are based on the Microsoft Fluent design system's guidance on geometry.
 * https://learn.microsoft.com/en-us/windows/apps/design/signature-experiences/geometry
 *
 * @property overlay The corner radius for overlay elements.
 * @property control The corner radius for control elements.
 * @property intersectionEdge The corner radius for elements at intersection edges.
 */
@Immutable
class CornerRadius(
    override val overlay: Dp,
    override val control: Dp,
    override val intersectionEdge: Dp
): Geometry<Dp>

internal val LocalCornerRadius = staticCompositionLocalOf {
    CornerRadius(
        overlay = overlayCornerRadius,
        control = controlCornerRadius,
        intersectionEdge = intersectionEdgeCornerRadius
    )
}

private val overlayCornerRadius = 8.dp
private val controlCornerRadius = 4.dp
private val intersectionEdgeCornerRadius = 0.dp