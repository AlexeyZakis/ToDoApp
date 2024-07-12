package com.example.todoapp.plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.example.todoapp.api.TelegramApi
import com.example.todoapp.tasks.ApkFileDetailerTask
import com.example.todoapp.tasks.TelegramReporterTask
import com.example.todoapp.tasks.ValidateApkSizeTask
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import java.io.File

class TelegramReporterPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android not found")

        val android = project.extensions.getByType(AppExtension::class.java)
        val versionCodeProvider = project.provider {
            android.defaultConfig.versionCode
        }

        val extension = project.extensions.create("tgReporter", TelegramExtension::class)
        val telegramApi = TelegramApi(HttpClient(OkHttp))
        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            val variantName = variant.name.capitalize()

            val apkFile = File(project.buildDir, "apkSizeFile")

            val apkSizeValidator = project.tasks.register(
                "apkSizeValidateFor$variantName",
                ValidateApkSizeTask::class.java,
                telegramApi
            ).apply {
                configure {
                    validationEnable.set(extension.enableApkSizeValidator)
                    maxApkSizeMB.set(extension.maxApkSizeMB)
                    tgUserChatId.set(extension.tgUserChatId)
                    tgBotToken.set(extension.tgBotToken)
                    apkSizeFile.set(apkFile)
                    apkDir.set(artifacts)
                }
            }

            val apkNameString = extension.apkName.get()
            val versionCode = versionCodeProvider.get()

            val telegramSender = project.tasks.register(
                "reportTelegramApkFor$variantName",
                TelegramReporterTask::class.java,
                telegramApi
            ).apply {
                configure {
                    apkDir.set(artifacts)
                    tgBotToken.set(extension.tgBotToken)
                    tgUserChatId.set(extension.tgUserChatId)
                    apkSizeFile.set(apkSizeValidator.get().apkSizeFile)
                    validationEnabled.set(extension.enableApkSizeValidator)
                    apkName.set("$apkNameString-$variantName-$versionCode.apk")
                    onlyIf {
                        apkSizeValidator.get().validationPassed.get()
                    }
                }
            }
            telegramSender.dependsOn(apkSizeValidator)

            val apkFileDetailer = project.tasks.register(
                "detailApkFor$variantName",
                ApkFileDetailerTask::class.java,
                telegramApi
            ).apply {
                configure {
                    detailerEnabled.set(extension.enableApkDetailer)
                    apkDir.set(artifacts)
                    tgBotToken.set(extension.tgBotToken)
                    tgUserChatId.set(extension.tgUserChatId)
                }
            }
            // TODO : sometimes
            //  Cannot query the value of task ':app:apkSizeValidateForDebug' property 'validationPassed' because it has no value available.
            //  if ./gradlew :app:detailApkForDebug
            apkFileDetailer.dependsOn(telegramSender)
        }
    }
}

interface TelegramExtension {
    val tgUserChatId: Property<String>
    val tgBotToken: Property<String>

    val apkName: Property<String>

    val enableApkSizeValidator: Property<Boolean>
    val maxApkSizeMB: Property<Int>

    val enableApkDetailer: Property<Boolean>
}
