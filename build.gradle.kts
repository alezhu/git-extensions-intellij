import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import java.nio.file.Paths

plugins {
    id("java") // Java support
    id("org.jetbrains.intellij.platform")
    id("com.github.ben-manes.versions")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

group = "com.dmitryzhelnin.intellij.plugin.git.extensions"
version = "0.4.8-az"

dependencies {
    testImplementation("junit:junit:4.13.2")
    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        //        intellijIdeaCommunity("2023.3")
//        intellijIdea("262.4852.50")
        try {
//        androidStudio("253.32098.37")
            androidStudio("2025.3.4.7")
        } catch (ignored: Throwable) {
            val localAppData = System.getenv("LOCALAPPDATA")
            val path = Paths.get(localAppData, "Programs/Android Studio/bin/studio64.exe")
            local(path)
        }

//        bundledPlugin("com.intellij.java")

//        pluginVerifier()

        testFramework(TestFrameworkType.Platform)
    }

}

intellijPlatform {
    projectName = project.name
    pluginConfiguration {
        ideaVersion {
            sinceBuild.set("233")
        }
    }

}
tasks {
    printProductsReleases {
        channels = listOf(
            ProductRelease.Channel.EAP,
            ProductRelease.Channel.RELEASE,
            ProductRelease.Channel.BETA,
            ProductRelease.Channel.PATCH,
            ProductRelease.Channel.CANARY
        )
        types = listOf(IntelliJPlatformType.IntellijIdea, IntelliJPlatformType.AndroidStudio)
        untilBuild = provider { null }
    }
}
