package conventions

import AndroidConst
import baseAndroidConfig
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.androidTestImplementation
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.debugImplementation
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.implementation
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.kapt
import gradle.kotlin.dsl.accessors._ffe51ee91362977ddc98906f5440a003.testImplementation
import libs
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

configure<BaseAppModuleExtension> {
    baseAndroidConfig()
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AndroidConst.KOTLIN_COMPILER_EXTENSION_VERSION
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    kapt(libs.hilt.android.compiler)
    implementation(libs.bundles.hilt)

    // Compose
    implementation(libs.bundles.compose)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)

    implementation(libs.lorem)

    implementation(libs.okhttp)

    implementation(libs.bundles.ktor)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.work.runtime.ktx)

    // Room
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
}

kapt {
    correctErrorTypes = true
}
