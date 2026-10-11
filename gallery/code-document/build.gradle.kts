import io.github.composefluent.plugin.build.applyTargets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
}
val winui = providers.gradleProperty("fluent.winui").map(String::toBoolean).getOrElse(false)
if (!winui) apply(plugin = "com.android.kotlin.multiplatform.library")

kotlin {
    applyTargets(".gallery.code", winui = winui)
    if (winui) jvm()
    jvmToolchain(17)
}
