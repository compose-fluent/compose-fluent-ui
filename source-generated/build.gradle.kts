import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import io.github.composefluent.plugin.build.applyTargets

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.winui)
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
}
val winui = providers.gradleProperty("fluent.winui").map(String::toBoolean).getOrElse(false)
if (!winui) apply(plugin = "com.android.kotlin.multiplatform.library")

kotlin {
    applyTargets(namespaceModule = ".generated", winui = winui)
    sourceSets {
        commonMain.dependencies {
            implementation(project(":fluent-icons-core"))
            implementation(project(":fluent-icons-extended"))
            implementation(libs.compose.ui)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
// Source processors populate this module's shared indices before it compiles.
val indexTask = if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata"
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn(":fluent:$indexTask", ":fluent-icons-core:$indexTask", ":fluent-icons-extended:$indexTask")
}
