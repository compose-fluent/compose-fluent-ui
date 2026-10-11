@file:OptIn(
    kotlinx.cinterop.ExperimentalForeignApi::class,
    androidx.compose.ui.InternalComposeUiApi::class
)

package io.github.composefluent.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.composefluent.gallery.component.SourceCode
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.currentComposeViewForTest
import androidx.compose.ui.window.rememberWindowState
import io.github.composefluent.gallery.code.sampleCodeDocuments
import io.github.composefluent.gallery.screen.navigation.LocalOpenTabViewWindow
import io.github.composefluent.gallery.screen.navigation.WinuiTabViewWindowContent
import io.github.composefluent.gallery.resources.Res
import io.github.composefluent.gallery.resources.icon
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKStringFromUtf16
import kotlinx.cinterop.value
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import org.jetbrains.compose.resources.painterResource
import platform.windows.CommandLineToArgvW
import platform.windows.GetCommandLineW
import platform.windows.LocalFree

fun main() {
    val smokeTest = "--smoke-test" in commandLineArguments()
    if (smokeTest) {
        check(sampleCodeDocuments.isNotEmpty())
        sampleCodeDocuments.forEach { (name, document) ->
            check(document.source.isNotBlank() && document.spans.isNotEmpty()) { "Empty AST document: $name" }
        }
        println("FLUENT_AST_DOCUMENTS_OK count=${sampleCodeDocuments.size}")
    }
    Application {
        var tabViewWindowVisible by remember { mutableStateOf(false) }
        Window(
            title = "Compose Fluent Design Gallery · WinUI",
            state = rememberWindowState(width = 1280.dp, height = 800.dp),
            onCloseRequest = { exitApplication() }
        ) {
            GalleryTheme {
                CompositionLocalProvider(LocalOpenTabViewWindow provides { tabViewWindowVisible = true }) {
                    Box(Modifier.fillMaxSize()) {
                        App(
                            title = "Compose Fluent Design Gallery",
                            icon = painterResource(Res.drawable.icon)
                        )
                        if (smokeTest) {
                            SourceCode(
                                sampleCodeDocuments.values.first(),
                                Modifier.align(Alignment.BottomEnd).width(520.dp).height(320.dp)
                            )
                        }
                    }
                }
            }
            if (smokeTest) {
                LaunchedEffect(Unit) {
                    withTimeout(15_000) {
                        while ((currentComposeViewForTest?.renderVersionForTest ?: 0) == 0L) delay(100)
                    }
                    delay(1_000)
                    val view = checkNotNull(currentComposeViewForTest)
                    check(view.renderFailureForTest == null) { view.renderFailureForTest.orEmpty() }
                    val size = checkNotNull(view.lastRenderSizeForTest)
                    check(size.width > 0 && size.height > 0)
                    println("FLUENT_WINUI_SMOKE_OK frames=${view.renderVersionForTest} size=$size")
                    exitApplication()
                }
            }
        }
        if (tabViewWindowVisible) {
            Window(
                title = "TabView Window",
                state = rememberWindowState(width = 1280.dp, height = 800.dp),
                onCloseRequest = { tabViewWindowVisible = false }
            ) {
                CompositionLocalProvider(LocalOpenTabViewWindow provides { tabViewWindowVisible = true }) {
                    WinuiTabViewWindowContent()
                }
            }
        }
    }
}

private fun commandLineArguments(): List<String> = memScoped {
    val count = alloc<IntVar>()
    val arguments = CommandLineToArgvW(GetCommandLineW(), count.ptr) ?: return@memScoped emptyList()
    try {
        List(count.value) { index -> arguments[index]?.toKStringFromUtf16().orEmpty() }.drop(1)
    } finally {
        LocalFree(arguments)
    }
}
