package io.github.composefluent.gallery.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import io.github.composefluent.component.Button
import io.github.composefluent.component.Text
import io.github.composefluent.gallery.GalleryTheme
import io.github.composefluent.gallery.code.KotlinCodeDocument
import io.github.composefluent.gallery.component.GalleryPageScope

// ApplicationScope owns native windows, independently of the UI composition in each window.
val LocalOpenTabViewWindow = staticCompositionLocalOf<() -> Unit> {
    error("The WinUI Gallery host must provide a TabView window opener")
}

@Composable
internal actual fun GalleryPageScope.PlatformTabViewSection() {
    Section(title = "TabView Window", sourceCode = KotlinCodeDocument.Empty) {
        val openWindow = LocalOpenTabViewWindow.current
        Button(onClick = openWindow) { Text("Open Window") }
    }
}

@Composable
fun WinuiTabViewWindowContent() {
    GalleryTheme { TabViewWindowContent() }
}
