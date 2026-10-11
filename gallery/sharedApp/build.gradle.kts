import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.google.devtools.ksp.gradle.KspAATask
import io.github.composefluent.plugin.build.BuildConfig
import io.github.composefluent.plugin.build.applyTargets
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.winui)
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.ksp)
    alias(libs.plugins.build.konfig)
}
val winui = providers.gradleProperty("fluent.winui").map(String::toBoolean).getOrElse(false)
if (!winui) apply(plugin = "com.android.kotlin.multiplatform.library")

kotlin {
    applyTargets("gallery.shared", winui = winui)
    if (!winui) listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.foundation)
                implementation(libs.compose.components.resources)
                implementation(project(":fluent"))
                implementation(project(":fluent-icons-extended"))
                implementation(libs.compose.ui.util)
                api(project(":gallery:code-document"))
                implementation(libs.androidx.navigation3.ui)
                implementation(libs.androidx.navigationevent.compose)
                implementation(project(":source-generated"))
            }
            kotlin.srcDir(
                if (winui) "build/generated/ksp/mingwX64/mingwX64Main/kotlin"
                else "build/generated/ksp/metadata/commonMain/kotlin"
            )
        }
        if (winui) {
            mingwX64Main {
                // Generated declarations are shared Gallery APIs, so keep them in commonMain only.
                kotlin.exclude { it.file.invariantSeparatorsPath.contains("/build/generated/ksp/") }
            }
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        if (!winui) jvmMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.jna.platform)
            implementation(libs.jna)
        }
    }
}

buildkonfig {
    packageName = "${BuildConfig.packageName}.build"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "LIBRARY_VERSION", BuildConfig.libraryVersion, const = true)
        buildConfigField(FieldSpec.Type.STRING, "GALLERY_VERSION", BuildConfig.integerVersionName, const = true)
        buildConfigField(FieldSpec.Type.STRING, "COMPOSE_VERSION", libs.versions.compose.get(), const = true)
        buildConfigField(FieldSpec.Type.STRING, "KOTLIN_VERSION", libs.versions.kotlin.get(), const = true)
        buildConfigField(FieldSpec.Type.STRING, "HAZE_VERSION", libs.versions.haze.get(), const = true)

        buildConfigField(FieldSpec.Type.STRING, "CURRENT_BRANCH", BuildConfig.branch, const = true)
    }
}

ksp {
    arg("fluent.projects.root.path", rootDir.absolutePath)
    arg("fluent.projects.current.path", projectDir.absolutePath)
    arg("fluent.projects.current.name", project.name)
}

dependencies {
    val processor = project(":gallery-processor")
    add(if (winui) "kspMingwX64" else "kspCommonMainMetadata", processor)
}

// workaround for KSP only in Common Main.
// https://github.com/google/ksp/issues/567
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != (if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")) {
        dependsOn(if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")
    }
}

tasks.withType<KspAATask>().configureEach {
    if (name != (if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")) {
        dependsOn(if (winui) "kspKotlinMingwX64" else "kspCommonMainKotlinMetadata")
    }
}

compose.resources {
    packageOfResClass = "${BuildConfig.packageName}.gallery.resources"
    publicResClass = true
}
