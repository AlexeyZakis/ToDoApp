package com.example.todoapp.tasks

import com.example.todoapp.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ValidateApkSizeTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val validationEnable: Property<Boolean>

    @get:Input
    abstract val maxApkSizeMB: Property<Int>

    @get:Input
    abstract val tgBotToken: Property<String>

    @get:Input
    abstract val tgUserChatId: Property<String>

    @get:OutputFile
    abstract val validationPassed: RegularFileProperty

    @get:OutputFile
    abstract val apkSizeFile: RegularFileProperty

    private val byteInMegabyte = 1024.0 * 1024.0

    @TaskAction
    fun validate() {
        if (!validationEnable.getOrElse(false)) {
            validationPassed.get().asFile.writeText(true.toString())
            return
        }
        val tgBotToken = tgBotToken.get()
        val tgUserChatId = tgUserChatId.get()
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { file ->
                val apkSize = file.length() / byteInMegabyte
                val maxApkSize = maxApkSizeMB.getOrElse(Int.MAX_VALUE)
                val passed = apkSize <= maxApkSize
                validationPassed.get().asFile.writeText(passed.toString())
                val message = "Apk name: ${file.name}\n" +
                        "Size: $apkSize MB\n" +
                        "Max apk size: $maxApkSize MB\n" +
                        "Passed: $passed"
                println(message)
                if (!passed) {
                    runBlocking {
                        telegramApi.sendMessage(
                            message = message,
                            tgBotToken = tgBotToken,
                            tgUserChatId = tgUserChatId,
                        ).apply {
                            println("Status = $status")
                        }
                    }
                } else {
                    apkSizeFile.get().asFile.writeText(apkSize.toString())
                }
            }
    }
}
