package conventions

import AndroidConst
import android
import baseAndroidConfig

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    baseAndroidConfig()
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidConst.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {

}
