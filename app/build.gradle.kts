import java.util.Properties

plugins {
    id("conventions.android-app-convention")
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.serialization)

    id("telegram-reporter")
}

// From local.properties
val apiToken: String = getLocalProperty("API_TOKEN", project)
val tgToken: String = getLocalProperty("TG_TOKEN", project)
val tgChat: String = getLocalProperty("TG_CHAT", project)

tgReporter {
    tgBotToken.set(tgToken)
    tgUserChatId.set(tgChat)

    apkName.set("todolist")

    enableApkSizeValidator.set(true)
    maxApkSizeMB.set(16)

    enableApkDetailer.set(true)
}

android {
    defaultConfig {
        applicationId = "ru.yandex.school.ssmd24.todoapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_TOKEN", "\"$apiToken\"")
    }
}

dependencies {

}

fun getLocalProperty(propertyName: String, project: Project): String {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val properties = Properties()
        properties.load(localPropertiesFile.inputStream())
        return properties.getProperty(propertyName)
    } else {
        throw GradleException("Could not find 'local.properties' file.")
    }
}
