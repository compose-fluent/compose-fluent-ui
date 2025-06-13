package io.github.composefluent.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.Dismiss

/**
 * An InfoBar is a transient UI element that presents brief, relevant information to the user.
 *
 * @param title The title content of the InfoBar. Usually a short, bold summary of the message.
 * @param message The main message content of the InfoBar. Provides more details related to the title.
 * @param modifier Modifier to be applied to the layout of the InfoBar.
 * @param colors The set of colors used for the InfoBar, such as background, content, and icon colors.
 *               Defaults to [InfoBarDefaults.informationalColors].
 * @param icon Optional icon content of the InfoBar. If null, no icon will be displayed. Defaults to a
 *             badge indicating informational severity.
 * @param action Optional action content for the InfoBar, such as a button.
 * @param closeAction Optional close action content for the InfoBar.
 */
@Composable
fun InfoBar(
    title: @Composable () -> Unit,
    message: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: InfoBarColors = InfoBarDefaults.informationalColors(),
    icon: (@Composable () -> Unit)? = { InfoBarDefaults.Badge(severity = InfoBarSeverity.Informational) },
    action: (@Composable () -> Unit)? = null,
    closeAction: (@Composable () -> Unit)? = null,
) {
    val currentColor = colors
    Layer(
        color = currentColor.backgroundColor,
        contentColor = currentColor.contentColor,
        backgroundSizing = BackgroundSizing.InnerBorderEdge,
        modifier = modifier
    ) {
        InfoBarLayout(
            title = {
                CompositionLocalProvider(
                    LocalTextStyle provides FluentTheme.typography.bodyStrong,
                    content = title
                )
            },
            message = {
                CompositionLocalProvider(
                    LocalTextStyle provides FluentTheme.typography.body,
                    content = message
                )
            },
            action = action,
            closeAction = closeAction,
            icon = icon?.let { icon ->
                {
                    CompositionLocalProvider(
                        LocalContentColor provides currentColor.iconColor,
                        LocalContentAlpha provides currentColor.iconColor.alpha,
                        content = icon
                    )
                }
            },
            modifier = Modifier
                .wrapContentHeight()
                .sizeIn(minHeight = InfoBarMinHeight)
                .padding(start = 16.dp)
        )
    }
}

/**
 * An [InfoBar] component that displays a title, message, and optional icon, action, and close action.
 * This overload provides a predefined severity level to quickly set up common InfoBar types.
 *
 * @param title The title of the InfoBar.
 * @param message The message displayed within the InfoBar.
 * @param severity The [InfoBarSeverity] level (e.g., Informational, Success, Warning, Critical).
 * This determines the default colors and icon if not explicitly overridden.
 * @param modifier Modifier for styling and layout of the InfoBar.
 * @param colors The [InfoBarColors] to be used for this InfoBar. If not specified,
 * it defaults to colors based on the provided [severity].
 * @param icon An optional composable to display an icon. If not specified, it defaults to a badge
 * with an icon determined by the provided [severity].
 * @param action An optional composable to display an action button.
 * @param closeAction An optional composable to display a close action button.
 */
@Composable
fun InfoBar(
    title: @Composable () -> Unit,
    message: @Composable () -> Unit,
    severity: InfoBarSeverity,
    modifier: Modifier = Modifier,
    colors: InfoBarColors = InfoBarDefaults.colors(severity),
    icon: (@Composable () -> Unit)? = {
        InfoBarDefaults.Badge(severity = severity, backgroundColor = colors.iconColor)
    },
    action: (@Composable () -> Unit)? = null,
    closeAction: (@Composable () -> Unit)? = null,
) {
    InfoBar(
        title = title,
        message = message,
        modifier = modifier,
        icon = icon,
        colors = colors,
        action = action,
        closeAction = closeAction
    )
}

/**
 * Represents the color scheme for the [InfoBar] component.
 *
 * @param backgroundColor The background color of the InfoBar.
 * @param contentColor The color of the text content within the InfoBar.
 * @param iconColor The color of the icon within the InfoBar.
 */
data class InfoBarColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val iconColor: Color
)

/**
 * Contains the default values used for [InfoBar].
 */
object InfoBarDefaults {

    /**
     * A composable function that creates a close action button, typically used to dismiss
     * an InfoBar.
     *
     * @param onClick The callback to be invoked when the button is clicked.
     * @param modifier The modifier to be applied to the button.
     * @param enabled Controls the enabled state of the button. When `false`, the button will be
     *        disabled and will not respond to user interactions.
     */
    @Composable
    fun CloseActionButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
    ) {
        SubtleButton(
            iconOnly = true,
            onClick = onClick,
            content = {
                Icon(Icons.Default.Dismiss, contentDescription = null)
            },
            disabled = !enabled,
            modifier = modifier.defaultMinSize(38.dp, 38.dp)
        )
    }

    /**
     * A badge to show the severity of the [InfoBar].
     *
     * @param modifier The [Modifier] to be applied to this badge.
     * @param severity The [InfoBarSeverity] that this badge will display.
     * @param contentDescription The content description for the icon.
     * Defaults to [InfoBarSeverity.name].
     * @param backgroundColor The background color of the badge.
     * Defaults to color from [FluentTheme] based on the [severity].
     */
    @Composable
    fun Badge(
        modifier: Modifier = Modifier,
        severity: InfoBarSeverity = InfoBarSeverity.Informational,
        contentDescription: String? = severity.name,
        backgroundColor: Color = when (severity) {
            InfoBarSeverity.Success -> FluentTheme.colors.system.success
            InfoBarSeverity.Warning -> FluentTheme.colors.system.caution
            InfoBarSeverity.Critical -> FluentTheme.colors.system.critical
            InfoBarSeverity.Informational -> FluentTheme.colors.system.attention
        }
    ) {
        Badge(
            content = {
                BadgeDefaults.Icon(
                    imageVector = when (severity) {
                        InfoBarSeverity.Success -> BadgeDefaults.successIcon
                        InfoBarSeverity.Warning -> BadgeDefaults.cautionIcon
                        InfoBarSeverity.Critical -> BadgeDefaults.criticalIcon
                        InfoBarSeverity.Informational -> BadgeDefaults.informationIcon
                    },
                    contentDescription = contentDescription
                )
            },
            backgroundColor = backgroundColor,
            modifier = modifier
        )
    }

    /**
     * Returns the [InfoBarColors] that match the provided [severity].
     *
     * @param severity The [InfoBarSeverity] used to determine the colors.
     * @return The [InfoBarColors] associated with the given [severity].
     */
    @Stable
    @Composable
    fun colors(severity: InfoBarSeverity = InfoBarSeverity.Informational): InfoBarColors {
        return when (severity) {
            InfoBarSeverity.Success -> successColors()
            InfoBarSeverity.Warning -> warningColors()
            InfoBarSeverity.Critical -> criticalColors()
            InfoBarSeverity.Informational -> informationalColors()
        }
    }

    /**
     * Creates a set of [InfoBarColors] that represent the informational color scheme of an InfoBar.
     *
     * @param backgroundColor The background color of the InfoBar. Defaults to `FluentTheme.colors.background.card.secondary`.
     * @param contentColor The color of the text and other content in the InfoBar. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param iconColor The color of the icon in the InfoBar. Defaults to `FluentTheme.colors.system.attention`.
     * @return An [InfoBarColors] object with the specified colors.
     */
    @Stable
    @Composable
    fun informationalColors(
        backgroundColor: Color = FluentTheme.colors.background.card.secondary,
        contentColor: Color = FluentTheme.colors.text.text.primary,
        iconColor: Color = FluentTheme.colors.system.attention
    ): InfoBarColors = InfoBarColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        iconColor = iconColor
    )

    /**
     * Creates a set of [InfoBarColors] that represent the success color scheme of an InfoBar.
     *
     * @param backgroundColor The background color of the InfoBar. Defaults to `FluentTheme.colors.system.successBackground`.
     * @param contentColor The color of the text and other content in the InfoBar. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param iconColor The color of the icon in the InfoBar. Defaults to `FluentTheme.colors.system.success`.
     * @return An [InfoBarColors] object with the specified colors.
     */
    @Stable
    @Composable
    fun successColors(
        backgroundColor: Color = FluentTheme.colors.system.successBackground,
        contentColor: Color = FluentTheme.colors.text.text.primary,
        iconColor: Color = FluentTheme.colors.system.success
    ) = InfoBarColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        iconColor = iconColor
    )

    /**
     * Creates a set of [InfoBarColors] that represent the warning color scheme of an InfoBar.
     *
     * @param backgroundColor The background color of the InfoBar. Defaults to `FluentTheme.colors.system.cautionBackground`.
     * @param contentColor The color of the text and other content in the InfoBar. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param iconColor The color of the icon in the InfoBar. Defaults to `FluentTheme.colors.system.caution`.
     * @return An [InfoBarColors] object with the specified colors.
     */
    @Stable
    @Composable
    fun warningColors(
        backgroundColor: Color = FluentTheme.colors.system.cautionBackground,
        contentColor: Color = FluentTheme.colors.text.text.primary,
        iconColor: Color = FluentTheme.colors.system.caution
    ) = InfoBarColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        iconColor = iconColor
    )

    /**
     * Creates a set of [InfoBarColors] that represent the critical color scheme of an InfoBar.
     *
     * @param backgroundColor The background color of the InfoBar. Defaults to `FluentTheme.colors.system.criticalBackground`.
     * @param contentColor The color of the text and other content in the InfoBar. Defaults to `FluentTheme.colors.text.text.primary`.
     * @param iconColor The color of the icon in the InfoBar. Defaults to `FluentTheme.colors.system.critical`.
     * @return An [InfoBarColors] object with the specified colors.
     */
    @Stable
    @Composable
    fun criticalColors(
        backgroundColor: Color = FluentTheme.colors.system.criticalBackground,
        contentColor: Color = FluentTheme.colors.text.text.primary,
        iconColor: Color = FluentTheme.colors.system.critical
    ) = InfoBarColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        iconColor = iconColor
    )
}

/**
 * Defines the severity levels for an [InfoBar].
 * Each severity level indicates the type of information being conveyed, such as informational, success, warning, or critical.
 */
enum class InfoBarSeverity {
    Informational,
    Success,
    Warning,
    Critical,
}

@Composable
private fun InfoBarLayout(
    title: @Composable () -> Unit,
    message: @Composable () -> Unit,
    action: (@Composable () -> Unit)?,
    closeAction: (@Composable () -> Unit)?,
    icon: (@Composable () -> Unit)?,
    modifier: Modifier
) {
    Layout(
        contents = listOf(
            title,
            message,
            action ?: {},
            closeAction ?: {},
            icon ?: {},
        ),
        modifier = modifier,
        measurePolicy = remember { InfoBarLayoutMeasurePolicy() }
    )
}

private class InfoBarLayoutMeasurePolicy : MultiContentMeasurePolicy {

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<List<IntrinsicMeasurable>>,
        width: Int
    ): Int {
        return 0
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<List<IntrinsicMeasurable>>,
        width: Int
    ): Int {
        val (titleMeasurables, messageMeasurables, actionMeasurables, closeActionMeasurables, iconMeasurables) = measurables
        val titleMeasurable = titleMeasurables.firstOrNull()
        val messageMeasurable = messageMeasurables.firstOrNull()
        val actionMeasurable = actionMeasurables.firstOrNull()
        val closeActionMeasurable = closeActionMeasurables.firstOrNull()
        val iconMeasurable = iconMeasurables.firstOrNull()
        val isMultilineContent = isMultilineContent(
            titleMeasurable,
            messageMeasurable,
            actionMeasurable,
            closeActionMeasurable,
            iconMeasurable,
            width,
            Constraints.Infinity
        )

        return if (isMultilineContent) {
            val iconWidthWithSpacing = iconMeasurable?.minIntrinsicWidth(Constraints.Infinity)
                ?.plus(IconEndSpacing.roundToPx()) ?: 0
            val closeActionWidthWithSpacing =
                closeActionMeasurable?.minIntrinsicWidth(Constraints.Infinity)
                    ?.plus(CloseActionEndSpacing.roundToPx()) ?: 0
            val messageEndSpacing = MessageEndSpacing.roundToPx()
            val contentWidth =
                (width - iconWidthWithSpacing - messageEndSpacing - closeActionWidthWithSpacing).coerceAtLeast(
                    0
                )
            maxOf(
                InfoBarMinHeight.roundToPx(),
                (MultiLineTopSpacing + MultiLineBottomSpacing).roundToPx() +
                        (titleMeasurable?.maxIntrinsicHeight(contentWidth) ?: 0) +
                        (messageMeasurable?.maxIntrinsicHeight(contentWidth) ?: 0) +
                        (actionMeasurable?.maxIntrinsicHeight(contentWidth)
                            ?.plus(MultiLineBottomSpacing.roundToPx()) ?: 0),
            )
        } else {
            InfoBarMinHeight.roundToPx()
        }
    }

    override fun MeasureScope.measure(
        measurables: List<List<Measurable>>,
        constraints: Constraints
    ): MeasureResult {
        val (titleMeasurables, messageMeasurables, actionMeasurables, closeActionMeasurables, iconMeasurables) = measurables
        val titleMeasurable = titleMeasurables.firstOrNull()
        val messageMeasurable = messageMeasurables.firstOrNull()
        val actionMeasurable = actionMeasurables.firstOrNull()
        val closeActionMeasurable = closeActionMeasurables.firstOrNull()
        val iconMeasurable = iconMeasurables.firstOrNull()

        val isMultilineContent = isMultilineContent(
            titleMeasurable,
            messageMeasurable,
            actionMeasurable,
            closeActionMeasurable,
            iconMeasurable,
            constraints.maxWidth,
            constraints.maxHeight
        )
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val iconSpacing = IconEndSpacing.roundToPx()
        val closeActionSpacing = CloseActionEndSpacing.roundToPx()
        val actionSpacing = SingleLineActionEndSpacing.roundToPx()
        val titleSpacing = SingleLineTitleEndSpacing.roundToPx()
        val messageSpacing = MessageEndSpacing.roundToPx()
        return if (!isMultilineContent) {
            val iconPlaceables = iconMeasurable?.measure(looseConstraints)
            val actionPlaceables = actionMeasurable?.measure(looseConstraints)
            val closeActionPlaceables =
                closeActionMeasurable?.measure(looseConstraints)
            val iconWidthWithSpacing = iconPlaceables?.width?.plus(iconSpacing) ?: 0
            val closeActionWidthWithSpacing =
                closeActionPlaceables?.width?.plus(closeActionSpacing) ?: 0
            val actionWidthWithSpacing = actionPlaceables?.width?.plus(actionSpacing) ?: 0

            val titleConstraints = looseConstraints.copy(
                maxWidth = constraints.maxWidth - iconWidthWithSpacing - closeActionWidthWithSpacing -
                        titleSpacing - messageSpacing - actionWidthWithSpacing
            )
            val titlePlaceables = titleMeasurable?.measure(titleConstraints)
            val contentPlaceables = messageMeasurable?.measure(
                titleConstraints.copy(
                    maxWidth = titleConstraints.maxWidth - (titlePlaceables?.width ?: 0)
                )
            )
            var width = 0
            var height = 0
            iconPlaceables?.let { placeable ->
                width += placeable.width + iconSpacing
                height = maxOf(height, placeable.height)
            }
            actionPlaceables?.let { placeable ->
                width += placeable.width
                height = maxOf(height, placeable.height)
            }
            closeActionPlaceables?.let { placeable ->
                width += placeable.width + closeActionSpacing
                height = maxOf(height, placeable.height)
            }
            titlePlaceables?.let { placeable ->
                width += placeable.width + titleSpacing
                height = maxOf(height, placeable.height)
            }
            contentPlaceables?.let { placeable ->
                width += placeable.width + messageSpacing
                height = maxOf(height, placeable.height)
            }
            val layoutWidth = if (constraints.hasFixedWidth) {
                constraints.maxWidth
            } else {
                width
            }
            val layoutHeight = if (constraints.hasFixedHeight) {
                constraints.maxHeight
            } else {
                height
            }
            val alignment = Alignment.CenterVertically
            layout(layoutWidth, layoutHeight) {
                var offset = 0
                iconPlaceables?.let { placeable ->
                    placeable.placeRelative(offset, alignment.align(placeable.height, layoutHeight))
                    offset += placeable.width + iconSpacing
                }

                titlePlaceables?.let { placeable ->
                    placeable.placeRelative(offset, alignment.align(placeable.height, layoutHeight))
                    offset += placeable.width + titleSpacing
                }

                contentPlaceables?.let { placeable ->
                    placeable.placeRelative(offset, alignment.align(placeable.height, layoutHeight))
                }

                offset = 0
                closeActionPlaceables?.let { placeable ->
                    offset += placeable.width + closeActionSpacing
                    placeable.placeRelative(
                        layoutWidth - offset,
                        alignment.align(placeable.height, layoutHeight)
                    )
                    offset += actionSpacing
                }

                actionPlaceables?.let { placeable ->
                    placeable.placeRelative(
                        layoutWidth - offset - placeable.width,
                        alignment.align(placeable.height, layoutHeight)
                    )
                }

            }
        } else {
            val topPadding = MultiLineTopSpacing.roundToPx()
            val bottomPadding = MultiLineBottomSpacing.roundToPx()
            val iconPlaceable = iconMeasurable?.measure(looseConstraints)
            val closeActionPlaceable = closeActionMeasurable?.measure(looseConstraints)
            val iconWidthWithSpacing = iconPlaceable?.width?.plus(iconSpacing) ?: 0
            val closeActionWidthWithSpacing =
                closeActionPlaceable?.width?.plus(closeActionSpacing + messageSpacing) ?: 0
            val contentWidth =
                (constraints.maxWidth - iconWidthWithSpacing - closeActionWidthWithSpacing)
                    .coerceAtLeast(0)
            if (constraints.hasBoundedHeight) {
                var verticalMaxHeight = looseConstraints.maxHeight - topPadding - bottomPadding
                val actionButtonPlaceable = actionMeasurable
                    ?.measure(
                        looseConstraints.copy(
                            maxWidth = contentWidth,
                            maxHeight = (verticalMaxHeight - bottomPadding).coerceAtLeast(0)
                        )
                    )
                val actionButtonHeightWithSpacing =
                    actionButtonPlaceable?.height?.plus(bottomPadding) ?: 0
                verticalMaxHeight -= actionButtonHeightWithSpacing
                val titlePlaceable = titleMeasurable?.measure(
                    looseConstraints.copy(
                        maxWidth = contentWidth,
                        maxHeight = verticalMaxHeight.coerceAtLeast(0)
                    )
                )
                verticalMaxHeight -= (titlePlaceable?.height ?: 0)
                val messagePlaceable = messageMeasurable?.measure(
                    looseConstraints.copy(
                        maxWidth = contentWidth,
                        maxHeight = verticalMaxHeight.coerceAtLeast(0)
                    )
                )
                val layoutHeight = maxOf(
                    a = constraints.minHeight,
                    b = (titlePlaceable?.height ?: 0)
                            + (messagePlaceable?.height ?: 0)
                            + actionButtonHeightWithSpacing
                            + topPadding + bottomPadding
                )
                val layoutWidth = maxOf(
                    a = constraints.minWidth,
                    b = iconWidthWithSpacing + closeActionWidthWithSpacing
                            + maxOf(
                        a = titlePlaceable?.width ?: 0,
                        b = messagePlaceable?.width ?: 0,
                        c = actionButtonPlaceable?.width ?: 0
                    )
                )
                layout(layoutWidth, layoutHeight) {
                    val alignment = Alignment.CenterVertically
                    iconPlaceable?.let { placeable ->
                        val iconY = alignment.align(placeable.height, constraints.minHeight)
                        placeable.placeRelative(
                            0,
                            iconY
                        )
                    }
                    closeActionPlaceable?.let { placeable ->
                        val closeActionY = alignment.align(placeable.height, constraints.minHeight)
                        placeable.placeRelative(
                            layoutWidth - placeable.width - closeActionSpacing,
                            closeActionY
                        )
                    }
                    val startOffset = iconWidthWithSpacing
                    var offsetAcc = titlePlaceable?.let {
                        val firstBaseline = it[FirstBaseline]
                        val lastBaseline = it[LastBaseline]
                        alignment.align(it.height + firstBaseline - lastBaseline, constraints.minHeight)
                    } ?: topPadding
                    titlePlaceable?.let { placeable ->
                        placeable.placeRelative(startOffset, offsetAcc)
                        offsetAcc += placeable.height
                    }
                    messagePlaceable?.let { placeable ->
                        placeable.placeRelative(startOffset, offsetAcc)
                        offsetAcc +=  placeable.height
                    }
                    actionButtonPlaceable?.placeRelative(startOffset, offsetAcc + bottomPadding)
                }
            } else {
                val contentConstraints = looseConstraints.copy(maxWidth = contentWidth)
                val titlePlaceable = titleMeasurable?.measure(contentConstraints)
                val messagePlaceable = messageMeasurable?.measure(contentConstraints)
                val actionButtonPlaceable = actionMeasurable?.measure(contentConstraints)
                val iconWidthWithSpacing = iconPlaceable?.width?.plus(iconSpacing) ?: 0
                val closeActionWidthWithSpacing =
                    closeActionPlaceable?.width?.plus(closeActionSpacing + messageSpacing) ?: 0
                val contentWidth =
                    iconWidthWithSpacing + closeActionWidthWithSpacing +
                            maxOf(
                                a = titlePlaceable?.width ?: 0,
                                b = messagePlaceable?.width ?: 0,
                                c = actionButtonPlaceable?.width ?: 0
                            )
                val layoutWidth = if (constraints.hasBoundedWidth) {
                    maxOf(constraints.minWidth, contentWidth)
                } else {
                    contentWidth
                }

                val titleHeight = titlePlaceable?.height ?: 0
                val messageHeight = messagePlaceable?.height ?: 0
                val actionHeight = actionButtonPlaceable?.height?.plus(bottomPadding) ?: 0
                val contentHeight =
                    topPadding + bottomPadding + titleHeight + messageHeight + actionHeight
                val layoutHeight = maxOf(constraints.minHeight, contentHeight)

                layout(layoutWidth, layoutHeight) {
                    iconPlaceable?.placeRelative(0, 0)
                    closeActionPlaceable?.placeRelative(layoutWidth - closeActionPlaceable.width, 0)
                    val startOffset = (iconWidthWithSpacing)
                    var offset = topPadding
                    titlePlaceable?.placeRelative(startOffset, 0)
                    messagePlaceable?.let { placeable ->
                        placeable.placeRelative(startOffset, titlePlaceable?.height ?: 0)
                        offset += placeable.height
                    }
                    actionButtonPlaceable?.placeRelative(startOffset, offset + bottomPadding)
                }
            }
        }
    }

    private fun IntrinsicMeasureScope.isMultilineContent(
        titleMeasurables: IntrinsicMeasurable?,
        messageMeasurables: IntrinsicMeasurable?,
        actionMeasurables: IntrinsicMeasurable?,
        closeActionMeasurables: IntrinsicMeasurable?,
        iconMeasurables: IntrinsicMeasurable?,
        width: Int,
        height: Int,
    ): Boolean {
        val iconEndSpacing = IconEndSpacing.roundToPx()
        val closeActionEndSpacing = CloseActionEndSpacing.roundToPx()
        val titleEndSpacing = SingleLineTitleEndSpacing.roundToPx()
        val messageEndSpacing = MessageEndSpacing.roundToPx()
        val actionSpacing = SingleLineActionEndSpacing.roundToPx()
        val iconIntrinsicWidth =
            iconMeasurables?.minIntrinsicWidth(height)?.plus(iconEndSpacing) ?: 0
        val actionIntrinsicWidth =
            actionMeasurables?.minIntrinsicWidth(height)?.plus(actionSpacing) ?: 0
        val closeActionIntrinsicWidth = closeActionMeasurables?.minIntrinsicWidth(height)
            ?.plus(closeActionEndSpacing) ?: 0
        val titleIntrinsicWidth =
            width - iconIntrinsicWidth - actionIntrinsicWidth - closeActionIntrinsicWidth - titleEndSpacing - messageEndSpacing
        val titleIntrinsicHeight =
            titleMeasurables?.maxIntrinsicHeight(titleIntrinsicWidth.coerceAtLeast(0)) ?: 0
        val singleLineHeight = 30.dp.roundToPx()
        val isTitleMultiline = titleIntrinsicHeight > singleLineHeight
        var isMultilineContent = isTitleMultiline
        if (!isTitleMultiline) {
            val titleWidth = titleMeasurables?.maxIntrinsicWidth(height) ?: 0
            val contentIntrinsicHeight =
                messageMeasurables?.maxIntrinsicHeight(
                    (titleIntrinsicWidth - titleWidth).coerceAtLeast(
                        0
                    )
                ) ?: 0
            isMultilineContent = contentIntrinsicHeight > singleLineHeight
        }
        return isMultilineContent
    }

}

private val InfoBarMinHeight = 48.dp
private val IconEndSpacing = 12.dp
private val MessageEndSpacing = 16.dp
private val CloseActionEndSpacing = 5.dp
private val SingleLineTitleEndSpacing = 12.dp
private val SingleLineActionEndSpacing = 8.dp
private val MultiLineTopSpacing = 14.dp
private val MultiLineBottomSpacing = 16.dp