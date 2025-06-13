package io.github.composefluent.plugin.build

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import io.github.composefluent.plugin.build.BuildConfig.branch
import io.github.composefluent.plugin.build.BuildConfig.integerVersionName
import io.github.composefluent.plugin.build.BuildConfig.isRelease
import io.github.composefluent.plugin.build.BuildConfig.libraryVersion
import io.github.composefluent.plugin.build.BuildConfig.snapshotLibraryVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

class BuildPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        setupLibraryVersion(target)

        target.allprojects.forEach { project ->
            project.afterEvaluate {

                project.extensions.findByType<MavenPublishBaseExtension>()?.apply {
                    setupMavenPortalPublishing(project)
                }
                /*project.extensions.findByType<PublishingExtension>()?.apply {
                    setupMavenPublishing(project)
                    project.extensions.findByType<SigningExtension>()?.let { signing ->
                        signing.setupSigning(this)

                        // workaround for publishing with javadoc see https://github.com/gradle/gradle/issues/26091#issuecomment-1722947958
                        project.tasks.withType<AbstractPublishToMaven>().configureEach {
                            val signingTask = project.tasks.withType<Sign>()
                            mustRunAfter(signingTask)
                        }
                    }
                }*/
            }
        }
    }

    private fun MavenPublishBaseExtension.setupMavenPortalPublishing(target: Project) {
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
        signAllPublications()
        coordinates(target.group.toString(), target.name, target.version.toString())

        pom {
            name.set("Compose Fluent UI")
            description.set("A Fluent Design UI library for Compose Multiplatform.")
            inceptionYear.set("2025")
            url.set("https://github.com/compose-fluent/compose-fluent-ui")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("konyaco")
                    name.set("Yaco")
                    url.set("https://github.com/konyaco")
                }
                developer {
                    id.set("sanlorng")
                    name.set("Sanlorng")
                    url.set("https://github.com/sanlorng")
                }
            }
            scm {
                url.set("https://github.com/compose-fluent/compose-fluent-ui")
                connection.set("scm:git:git://github.com/compose-fluent/compose-fluent-ui.git")
                developerConnection.set("scm:git:ssh://github.com/compose-fluent/compose-fluent-ui.git")
            }
        }
    }

    private fun SigningExtension.setupSigning(publishing: PublishingExtension) {
        useInMemoryPgpKeys(
            System.getenv("SIGNING_KEY_ID"),
            System.getenv("SIGNING_KEY"),
            System.getenv("SIGNING_PASSWORD")
        )
        sign(publishing.publications)
    }

    @Deprecated("use setupMavenPortalPublishing instead")
    private fun PublishingExtension.setupMavenPublishing(target: Project) {
        val javadocJar = target.tasks.findByName("javadocJar") ?: target.tasks.create("javadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
        }
        publications.withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("compose-fluent-ui")
                description.set("A Fluent Design UI library for compose-multiplatform.")
                url.set("https://github.com/compose-fluent/compose-fluent-ui")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        name.set("Kon Yaco")
                        email.set("atwzj233@gmail.com")
                        url.set("https://github.com/compose-fluent")
                    }
                }

                scm {
                    url.set("https://github.com/compose-fluent/compose-fluent-ui")
                    connection.set("scm:git:git://github.com/compose-fluent/compose-fluent-ui.git")
                    developerConnection.set("scm:git:ssh://github.com/compose-fluent/compose-fluent-ui.git")
                }
            }
        }
        repositories {
            maven {
                val snapshotsUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                val releasesUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                name = "OSSRH"
                url = target.uri(
                    if (target.version.toString().endsWith("SNAPSHOT")) snapshotsUrl
                    else releasesUrl
                )
                credentials {
                    username = System.getenv("OSSRH_USERNAME")
                    password = System.getenv("OSSRH_PASSWORD")
                }
            }
        }
    }


    private fun setupLibraryVersion(target: Project) {
        val providers = target.providers

        providers.exec {
            commandLine("git", "branch", "--show-current")
            isIgnoreExitValue = true
        }.standardOutput
            .asText
            .orNull
            ?.trim()
            ?.let { branch = it }

        val gitTag = providers.exec {
            commandLine("git", "describe", "--abbrev=0", "--tags")
            isIgnoreExitValue = true
        }.standardOutput.asText.get().trim()

        val relativeCommitCount = providers.exec {
            commandLine("git", "describe", "--tags")
            isIgnoreExitValue = true
        }.standardOutput.asText.get().trim()
            .removePrefix(gitTag)
            .let {
                if (it.isNotEmpty()) {
                    it.split("-")[1].toInt()
                } else {
                    0
                }
            }

        libraryVersion = when {
            isRelease -> gitTag
            else -> snapshotLibraryVersion
        }

        integerVersionName = libraryVersion
            .removePrefix("v")
            .removeSuffix("-SNAPSHOT")
            .substringBefore("-dev")
            .let {
                val parts = it.split(".")
                var major = parts.getOrNull(0) ?: "0"
                var minor = parts.getOrNull(1) ?: "0"
                if (major.startsWith("0")) {
                    major = "1"
                    minor = "0"
                }
                when (parts.size) {
                    1, 2 -> "${major}.$minor.$relativeCommitCount"
                    else -> {
                        val patchVersion = parts[2].toIntOrNull() ?: 0
                        "${major}.${minor}.${patchVersion * 200 + relativeCommitCount}"
                    }
                }
            }
    }
}
