package io.github.composefluent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.contentColorFor
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.filled.Checkmark
import io.github.composefluent.icons.filled.Dismiss

/**
 * A badge is a small, circular component that displays a status or numerical value.
 *
 * @param status The status of the badge, which determines its default background color.
 * @param backgroundColor The background color of the badge. Defaults to a color based on the [status].
 * @param contentColor The color of the content within the badge. Defaults to a color that provides
 * sufficient contrast against the [backgroundColor].
 * @param modifier Modifier to be applied to the badge.
 * @param content Optional composable content to display within the badge. If null, a small dot will be shown.
 * The provided lambda receives the current [status] as parameter.
 */
@Composable
fun Badge(
    status: BadgeStatus,
    backgroundColor: Color = BadgeDefaults.color(status),
    contentColor: Color = contentColorFor(backgroundColor),
    modifier: Modifier = Modifier,
    content: (@Composable (status: BadgeStatus) -> Unit)? = null
) {
    Badge(
        content = content?.let { { it(status) } },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = modifier
    )
}

/**
 * A badge is a small, visual indicator that represents the status or state of an item or object.
 *
 * @param backgroundColor The background color of the badge.
 * @param modifier Modifier to be applied to the badge.
 * @param contentColor The preferred color for content inside the badge.
 * @param content The content to be displayed inside the badge.
 * If the content is null, the badge will be displayed as a small circle.
 * If the content is not null, the badge will be displayed as a circle with the content inside.
 */
@Composable
fun Badge(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = contentColorFor(backgroundColor),
    content: (@Composable () -> Unit)? = null
) {
    val defaultSize = if (content != null) {
        16.dp
    } else {
        4.dp
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.defaultMinSize(defaultSize, defaultSize)
            .clip(CircleShape)
            .background(color = backgroundColor)
    ) {
        if (content != null) {
            CompositionLocalProvider(
                LocalContentColor provides contentColor,
                LocalContentAlpha provides contentColor.alpha,
                LocalTextStyle provides FluentTheme.typography.caption
            ) {
                Box(
                    content = { content() },
                    propagateMinConstraints = true,
                    modifier = Modifier.layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val hasText = placeable[FirstBaseline] > 0
                        val padding = when {
                            hasText -> 3.dp.roundToPx()
                            else -> 0
                        }
                        layout((placeable.width + 2 * padding), placeable.height) {
                            val offset = if (hasText) {
                                -(1.dp.roundToPx())
                            } else {
                                0
                            }
                            placeable.placeRelative(padding, offset)
                        }
                    }
                )
            }
        }
    }
}

/**
 * Contains the default values used by [Badge].
 */
object BadgeDefaults {

    /**
     * The default icon for informational badges.
     */
    inline val informationIcon: ImageVector
        get() = Icons.Filled.BadgeInformation

    /**
     * The default icon for a badge indicating a caution status.
     */
    inline val cautionIcon: ImageVector
        get() = Icons.Filled.BadgeImportant

    /**
     * The default icon used for the attention badge.
     */
    inline val attentionIcon: ImageVector
        get() = Icons.Filled.BadgeAttention

    /**
     * The default icon used for the success badge.
     */
    inline val successIcon: ImageVector
        get() = Icons.Filled.Checkmark

    /**
     * The default icon used for the critical badge status.
     */
    inline val criticalIcon: ImageVector
        get() = Icons.Filled.Dismiss

    /**
     * Retrieves the appropriate background color for a [Badge] based on its [BadgeStatus].
     *
     * @param status The [BadgeStatus] to determine the color for.
     * @return The [Color] associated with the given [BadgeStatus].
     */
    @Stable
    @Composable
    fun color(status: BadgeStatus): Color {
        return when (status) {
            BadgeStatus.Informational -> FluentTheme.colors.system.neutralBackground
            BadgeStatus.InformationalSafe -> FluentTheme.colors.system.solidNeutral
            BadgeStatus.Attention -> FluentTheme.colors.system.attention
            BadgeStatus.Caution -> FluentTheme.colors.system.caution
            BadgeStatus.Critical -> FluentTheme.colors.system.critical
            BadgeStatus.Success -> FluentTheme.colors.system.success
        }
    }

    /**
     * Creates an [Icon] based on the provided [status].
     *
     * @param status The [BadgeStatus] of the icon, which determines the icon's appearance.
     * @param contentDescription The content description for the icon. Defaults to the name of the [status].
     * @param modifier Modifier for styling and layout.
     */
    @Composable
    fun Icon(
        status: BadgeStatus,
        contentDescription: String? = status.name,
        modifier: Modifier = Modifier
    ) {
        this.Icon(
            imageVector = when (status) {
                BadgeStatus.Attention -> attentionIcon
                BadgeStatus.Caution -> cautionIcon
                BadgeStatus.Critical -> criticalIcon
                BadgeStatus.Success -> successIcon
                else -> informationIcon
            },
            contentDescription = contentDescription,
            modifier = modifier
        )
    }

    @Composable
    internal fun Icon(imageVector: ImageVector, contentDescription: String?, modifier: Modifier = Modifier) {
        io.github.composefluent.component.Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier.size(12.dp)
        )
    }

}

/**
 * Enumeration representing the different status levels for a [Badge].
 *
 * Badges can be used to inform users of the status or state of something.
 *
 * - [Informational]: Indicates general information.
 * - [InformationalSafe]: Indicates that a item is safe.
 * - [Caution]: Indicates something that the user should take notice of or proceed with caution.
 * - [Attention]: Indicates something that requires the user's attention.
 * - [Success]: Indicates a successful operation or state.
 * - [Critical]: Indicates a critical issue or error.
 */
enum class BadgeStatus {
    Informational,
    InformationalSafe,
    Caution,
    Attention,
    Success,
    Critical,
}