import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    id("kotlin-kapt")
    alias(libs.plugins.serialization)
}

// Helper function to read a property from local.properties
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

// Read the API token from local.properties
val apiToken: String = getLocalProperty("API_TOKEN", project)

android {
    namespace = "com.example.todoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_TOKEN", "\"$apiToken\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.hilt.work)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Lorem
    implementation(libs.lorem)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.navigation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.animation)
    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.constraintlayout)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)

    // OkHttp
    implementation(libs.okhttp)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.json)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.work.runtime.ktx)
}

kapt {
    correctErrorTypes = true
}