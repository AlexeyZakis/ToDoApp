import java.util.Properties

plugins {
    id("conventions.android-app-convention")
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.serialization)

    id("telegram-reporter")
}

tgReporter {
    tgBotToken.set(providers.environmentVariable("TG_TOKEN"))
    tgUserChatId.set(providers.environmentVariable("TG_CHAT"))

    apkName.set("todolist")

    enableApkSizeValidator.set(true)
    maxApkSizeMB.set(16)

    enableApkDetailer.set(false)
}

val apiToken: String = getLocalProperty("API_TOKEN", project)

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

// Print all environment variables (For debug)
tasks.register("checkEnvironmentVariables") {
    doLast {
        System.getenv().forEach { (key, value) ->
            println("$key = $value")
        }
    }
}

fun getLocalProperty(propertyName: String, project: Project): String {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        val properties = Properties()
        properties.load(localPropertiesFile.inputStream())
        return properties.getProperty(propertyName)
    }
    return ""
//    else {
//        throw GradleException("Could not find 'local.properties' file.")
//    }
}
