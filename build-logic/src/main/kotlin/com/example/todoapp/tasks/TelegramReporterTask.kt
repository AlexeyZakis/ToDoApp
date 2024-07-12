package com.example.todoapp.tasks

import com.example.todoapp.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val tgBotToken: Property<String>

    @get:Input
    abstract val tgUserChatId: Property<String>

    @get:Input
    abstract val apkName: Property<String>

    @get:InputFile
    abstract val apkSizeFile: RegularFileProperty

    @get:Input
    abstract val validationEnabled: Property<Boolean>

    @TaskAction
    fun report() {
        val tgBotToken = tgBotToken.get()
        val tgUserChatId = tgUserChatId.get()
        val apkSize = apkSizeFile.get().asFile.readText()
        val validationEnabled = validationEnabled.get()
        // TODO : add CI (8)
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { file ->
                val renamedFile = File(file.parent, apkName.get())
                file.renameTo(renamedFile)

                runBlocking {
                    telegramApi.sendMessage(
                        message = "Build finished",
                        tgBotToken = tgBotToken,
                        tgUserChatId = tgUserChatId
                    ).apply {
                        println("Status = $status")
                    }
                }
                runBlocking {
                    telegramApi.upload(
                        file = renamedFile,
                        token = tgBotToken,
                        chatId = tgUserChatId
                    ).apply {
                        println("Status = $status")
                    }
                    if (validationEnabled) {
                        telegramApi.sendMessage(
                            message = "Apk size: $apkSize MB",
                            tgBotToken = tgBotToken,
                            tgUserChatId = tgUserChatId
                        ).apply {
                            println("Status = $status")
                        }
                    }
                }
            }
    }
}
