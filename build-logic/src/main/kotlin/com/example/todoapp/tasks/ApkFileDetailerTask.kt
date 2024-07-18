package com.example.todoapp.tasks

import com.example.todoapp.api.TelegramApi
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.util.zip.ZipFile
import javax.inject.Inject

abstract class ApkFileDetailerTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val detailerEnabled: Property<Boolean>

    @get:Input
    abstract val tgBotToken: Property<String>

    @get:Input
    abstract val tgUserChatId: Property<String>

    private val byteInMegabyte = 1024.0 * 1024.0

    // If there are to many files (i.e. long message), telegram return error code 400
    private val maxNumOfFiles = 30

    @TaskAction
    fun detailing() {
        if (!detailerEnabled.getOrElse(false)) {
            return
        }
        val tgBotToken = tgBotToken.get()
        val tgUserChatId = tgUserChatId.get()

        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { file ->
                val apkFile = ZipFile(file)
                val entries = apkFile.entries()

                val message = StringBuilder()

                var fileCounter = 0

                while (entries.hasMoreElements() &&
                    fileCounter++ < maxNumOfFiles
                ) {
                    val entry = entries.nextElement()
                    val name = entry.name.substringAfterLast('/')
                    val size = entry.size / byteInMegabyte
                    message.append("$name $size MB\n")
                }
                apkFile.close()

                println(message)

                runBlocking {
                    telegramApi.sendMessage(
                        message = message.toString(),
                        tgBotToken = tgBotToken,
                        tgUserChatId = tgUserChatId,
                    ).apply {
                        println("Status = $status")
                    }
                }
            }
    }
}
