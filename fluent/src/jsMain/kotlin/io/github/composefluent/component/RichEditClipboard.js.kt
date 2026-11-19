@file:OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)

package io.github.composefluent.component

import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.ClipboardItem
import kotlin.js.Promise
import kotlinx.coroutines.await

internal actual fun ClipEntry.items(): List<ClipboardItem> = clipboardItems.asList()

internal actual suspend fun Promise<JsString?>.awaitPlainText(): String? = await<JsString?>()?.toString()
