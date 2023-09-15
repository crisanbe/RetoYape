// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply(plugin = "kover")

apply {
    from("$rootDir/buildSrc/src/main/java/sonarqube.gradle")// sonar enlace local
}



plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "3.5.0.2730"
    id("org.owasp.dependencycheck") version "8.1.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath (Build.hiltAndroidGradlePlugin)
        classpath(Build.kotlinGradlePlugin)
        classpath(Testing.kover)
        classpath(Testing.sonarqube)
        classpath(Testing.dependencyCheck)
    }
}
koverMerged {
    enable()

    xmlReport {
        onCheck.set(true)
    }
    htmlReport {
        onCheck.set(true)
    }
    verify {
        onCheck.set(true)
    }
    filters {
        classes {
            excludes += listOf(
                "dagger.hilt.internal.aggregatedroot.codegen.*",
                "hilt_aggregated_deps.*",
                "*ComposablesSingletons*",
                "*_HiltModules*",
                "*Hilt_*",
                "*BuildConfig",
                ".*_Factory.*",
                ".*.*_Factory*",
                "*_Factory\$*",
                "*_*Factory",
                "*_*Factory\$*",
                "**/di/**",
                "**/modelo/**",
                "*entity.*",
                "**/*_presentacion/**",
                "*core_ui.*",
                "*util*"
            )
        }
    }
}

subprojects {
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.kotlinx.kover")
}
allprojects {
    apply(plugin = "org.owasp.dependencycheck")
    apply {from("$rootDir/buildSrc/src/main/java/owasp.gradle") }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}