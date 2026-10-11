import io.github.composefluent.plugin.build.BuildConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = BuildConfig.group
version = BuildConfig.libraryVersion

kotlin {
    jvmToolchain(BuildConfig.Jvm.jvmToolchainVersion)
    jvm()
    sourceSets {
        jvmTest.dependencies {
            implementation(kotlin("test-junit"))
        }
        jvmMain.dependencies {
            implementation(libs.squareup.kotlinpoet)
            implementation("com.google.devtools.ksp:symbol-processing-api:${libs.versions.ksp.get()}")
            implementation("com.google.devtools.ksp:symbol-processing-aa-embeddable:${libs.versions.ksp.get()}")
            implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:${libs.versions.kotlin.get()}")
            implementation(project(":gallery:code-document"))
        }
    }
}
