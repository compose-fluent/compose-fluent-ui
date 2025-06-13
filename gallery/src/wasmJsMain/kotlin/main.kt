import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import io.github.composefluent.gallery.App
import io.github.composefluent.gallery.GalleryTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Compose Fluent Design Gallery") {
        GalleryTheme {
            App()
        }
    }
}
