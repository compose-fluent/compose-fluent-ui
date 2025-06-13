package io.github.composefluent.layout.overflow

import androidx.compose.runtime.Composable

/**
 * Scope for the children of [OverflowRow].
 *
 * Within this scope, you can use [item] or [items] to add items to the row.
 *
 * These functions will automatically handle the logic for overflowing items,
 * which will be hidden when there is not enough space and can be accessed
 * by clicking the overflow indicator, or by swiping.
 */
sealed interface OverflowRowScope {

    /**
     * Add an item to the overflow row.
     *
     * @param key an optional unique keys representing the item.
     *   Using the same key for multiple items in the list is not allowed.
     *   Type of the key should be saveable via the [androidx.compose.runtime.saveable.Saver] mechanism.
     *   If null, the list index is used as the key.
     * @param contentType the type of content for this item. If not `null` this will be used to
     * determine if the item should be kept across recompositions or if it should be discarded.
     * @param content the composable content to display for this item.
     */
    fun item(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable OverflowRowItemScope.() -> Unit
    )

    /**
     * Adds [count] items to the row.
     *
     * @param count the count of items to add
     * @param key an optional factory of stable and unique keys representing the item.
     *   Using the same key for multiple items in the list is not allowed.
     *   Type of the key should be saveable via the [androidx.compose.runtime.saveable.Saver] mechanism.
     *   If null, the list index is used as the key.
     * @param contentType a factory of the content types for the item. The key, if provided, should
     *   be stable and represent the same semantic type for the same item in the list.
     * @param itemContent the content of the item
     */
    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable OverflowRowItemScope.(index: Int) -> Unit
    )
}