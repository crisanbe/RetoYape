// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply(plugin = "kover")

apply {
    from("$rootDir/buildSrc/src/main/java/sonarqube.gradle")// sonar enlace local
}



plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("org.sonarqube") version "3.5.0.2730"
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
    }
}
subprojects {
    apply(plugin = "org.sonarqube")
    apply(plugin = "org.jetbrains.kotlinx.kover")
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}