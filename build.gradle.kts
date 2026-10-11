import io.github.composefluent.plugin.build.BuildConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.winui) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.build.konfig) apply false
    alias(libs.plugins.maven.publish) apply false
    id("io.github.composefluent.plugin.build")
}

group = BuildConfig.group

allprojects {
    pluginManager.withPlugin("compose-winui") {
        extensions.configure<org.jetbrains.compose.winui.ComposeWinUiExtension> {
            redirectGroup("dev.chrisbanes.haze", "2.0.1", "2.0.1-winui-SNAPSHOT", "io.github.compose-fluent.haze")
        }
    }
    repositories {
        providers.gradleProperty("composeWinUi.repository").orNull?.let { maven(it) }
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent { snapshotsOnly() }
            content { includeGroup("io.github.compose-fluent.compose.components") }
        }
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}
