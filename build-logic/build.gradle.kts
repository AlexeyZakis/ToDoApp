plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("telegram-reporter") {
        id = "telegram-reporter"
        implementationClass = "com.example.todoapp.plugins.TelegramReporterPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.ktor)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
