import io.github.composefluent.windows.toolkit.gradle.WindowsPackageType

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.winui)
    id("io.github.compose-fluent.windows-toolkit") version "0.1.0"
}

kotlin {
    jvmToolchain(25)
    mingwX64 {
        binaries.executable {
            runTaskProvider?.configure {
                if (providers.gradleProperty("gallery.winui.smokeTest").map(String::toBoolean).getOrElse(false)) {
                    args("--smoke-test")
                }
            }
        }
    }
    sourceSets.commonMain.dependencies {
        implementation(project(":gallery:sharedApp"))
        implementation(libs.compose.ui)
        implementation(libs.compose.components.resources)
    }
}

windows {
    packageReferences {
        windowsSdk("10.0.26100.0", generateProjection = false)
        nugetPackage("Microsoft.WindowsAppSDK.WinUI", "2.2.1") { generateProjection = false }
        nugetPackage("Microsoft.WindowsAppSDK.Runtime", "2.2.0") { generateProjection = false }
        nugetPackage("Microsoft.WindowsAppSDK.DWrite", "2.1.0") { generateProjection = false }
    }
    application {
        mainClass.set("io.github.composefluent.gallery.MainKt")
        executableBaseName.set("FluentGallery")
        packageType.set(WindowsPackageType.None)
        selfContained()
        // Keep diagnostics visible while this branch experiments with MinGW.
        console.set(true)
    }
}
