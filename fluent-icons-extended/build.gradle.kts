import io.github.composefluent.plugin.build.BuildConfig
import io.github.composefluent.plugin.build.applyTargets
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.winui)
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.maven.publish)
}
val winui = providers.gradleProperty("fluent.winui").map(String::toBoolean).getOrElse(false)
if (!winui) apply(plugin = "com.android.kotlin.multiplatform.library")

group = BuildConfig.group
version = BuildConfig.libraryVersion

kotlin {
    applyTargets(namespaceModule = ".icons.extended", winui = winui)
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(project(":fluent-icons-core"))
        }
    }
}

dependencies {
    val processor = (project(":source-generated-processor"))
    add(if (winui) "kspMingwX64" else "kspCommonMainMetadata", processor)
}

// workaround for KSP only in Common Main.
// https://github.com/google/ksp/issues/567
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != (if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")) {
        dependsOn(if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")
    }
}

ksp {
    arg("fluent.projects.current.name", "FluentIconExtended")
    arg("source.generated.module.enabled", false.toString())
    arg("source.generated.icon.enabled", true.toString())
}