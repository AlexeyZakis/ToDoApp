package com.example.todoapp.presentation.screens.aboutApp

import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetReader {
    fun loadJSONFromAsset(context: Context, fileName: String): JSONObject {
        val inputStream = context.assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?

        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        return JSONObject(stringBuilder.toString())
    }
}