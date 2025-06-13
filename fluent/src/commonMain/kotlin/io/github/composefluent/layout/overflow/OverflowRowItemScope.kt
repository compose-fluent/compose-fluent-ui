package io.github.composefluent.layout.overflow

/**
 * Scope for items within an [OverflowRow].
 *
 * This sealed interface indicates whether an item is displayed within the main content area of the
 * [OverflowRow] or in the overflow section (e.g., within a dropdown or similar).
 *
 * `isOverflow` property determines the placement:
 * - `false`: The item is visible in the primary row content.
 * - `true`: The item is in the overflow section.
 *
 * The two implementations are [OnContent] and [OnOverflow]:
 * - [OnContent]: Represents an item that is displayed in the primary content area.
 * - [OnOverflow]: Represents an item that is part of the overflow section.
 */
sealed interface OverflowRowItemScope {

    /**
     * Indicates whether the item is part of the overflow content or not.
     *
     * If `true`, the item is displayed in the overflow area.
     * If `false`, the item is part of the main content and is displayed normally.
     */
    val isOverflow: Boolean

    data object OnContent: OverflowRowItemScope {
        override val isOverflow: Boolean = false
    }

    data object OnOverflow: OverflowRowItemScope {
        override val isOverflow: Boolean = true
    }
}
