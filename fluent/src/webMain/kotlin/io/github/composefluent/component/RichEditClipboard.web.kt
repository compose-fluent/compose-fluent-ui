@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package io.github.composefluent.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardItem
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import kotlin.js.Promise

private fun canReadEntries(): Boolean =
    js("Boolean(navigator.clipboard && navigator.clipboard.read && navigator.clipboard.write && typeof ClipboardItem !== 'undefined')")

private fun canReadText(): Boolean =
    js("Boolean(navigator.clipboard && navigator.clipboard.readText)")

private fun readTextFallback(): Promise<JsString?> =
    js("navigator.clipboard.readText().catch(function() { return null })")

internal expect fun ClipEntry.items(): List<ClipboardItem>
internal expect suspend fun Promise<JsString?>.awaitPlainText(): String?

private fun hasPlainText(item: ClipboardItem): Boolean = js("item.types.includes('text/plain')")

private fun readPlainText(item: ClipboardItem): Promise<JsString?> =
    js("""item.getType('text/plain').then(function(blob) { return blob.text() })
        .catch(function() { return null })""")

internal suspend fun ClipEntry.plainText(): String? {
    val item = items().firstOrNull(::hasPlainText) ?: return null
    return readPlainText(item).awaitPlainText()
}

@Composable
internal actual fun rememberPlainTextPaste(): (suspend () -> String?)? {
    val clipboard = LocalClipboard.current
    return when {
        canReadEntries() -> suspend { clipboard.getClipEntry()?.plainText() }
        canReadText() -> suspend { readTextFallback().awaitPlainText() }
        else -> null
    }
}

@Composable
internal actual fun RichEditFieldContext(content: @Composable () -> Unit) = content()

internal actual val usesCommandKey: Boolean = js("/Mac|iPhone|iPad|iPod/i.test(navigator.platform)")
