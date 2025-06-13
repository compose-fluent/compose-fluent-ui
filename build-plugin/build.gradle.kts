plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())

    implementation(kotlin("gradle-plugin", libs.versions.kotlin.get()))
    implementation(
        "com.vanniktech.maven.publish",
        "com.vanniktech.maven.publish.gradle.plugin",
        libs.versions.mavenPublish.get()
    )
}

kotlin {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

gradlePlugin {
    plugins {
        create("BuildPlugin") {
            id = "io.github.composefluent.plugin.build"
            implementationClass = "io.github.composefluent.plugin.build.BuildPlugin"
        }
    }
}