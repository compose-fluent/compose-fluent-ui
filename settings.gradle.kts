// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
pluginManagement {
    repositories {
        providers.gradleProperty("composeWinUi.repository").orNull?.let { maven(it) }
        maven("https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent { snapshotsOnly() }
            content { includeGroup("io.github.compose-fluent") }
        }
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    resolutionStrategy.eachPlugin {
        if (requested.id.id == "compose-winui") {
            useModule("io.github.compose-fluent:compose-winui:${requested.version}")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "FluentDesign"
includeBuild("build-plugin")
include("fluent", "fluent-icons-core", "fluent-icons-extended")
include("fluent-icons-generator")
include("gallery:sharedApp", "gallery-processor", "gallery:code-document")
include("source-generated", "source-generated-processor")
if (providers.gradleProperty("fluent.winui").map(String::toBoolean).getOrElse(false)) {
    include("gallery:mingwApp")
    includeBuild("external/haze/winui")
} else {
    include("gallery:androidApp", "gallery:jvmApp", "gallery:webApp", "gallery:macosApp")
}
