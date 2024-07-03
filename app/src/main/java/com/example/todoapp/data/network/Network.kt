package com.example.todoapp.data.network

import android.util.Log
import com.example.todoapp.data.network.Constants.NetworkConstants
import com.example.todoapp.data.network.DTOs.ElementDto
import com.example.todoapp.data.network.DTOs.ErrorDto
import com.example.todoapp.data.network.DTOs.ListDto
import com.example.todoapp.data.network.DTOs.ResponseDto
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.buildJsonObject

object Network {
    private var lastKnownRevision = 0

    suspend fun getItemsList(): Map<String, TodoItem> {
        val result = client.safeRequest<ListDto, ErrorDto>(
            method = HttpMethod.Get,
            path = "list",
//            params = StringValues.build {
//                append("", "")
//            }
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("getItemsList", result)
                result.data.list.map {
                    it.toTodoItem()
                }.associateBy { it.id }
            }
            is NetworkResult.Error -> {
                onError("getItemsList", result)
                mapOf()
            }
        }
    }
    suspend fun updateList(list: Items) { // TODO : Convert list of items to JSON and put into body (maybe)
        val result = client.safeRequest<ListDto, ErrorDto>(
            method = HttpMethod.Patch,
            path = "list",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
        )
        when (result) {
            is NetworkResult.Success -> {
                onSuccess("updateList", result)
            }
            is NetworkResult.Error -> {
                onError("updateList", result)
            }
        }
    }
    suspend fun getItem(id: String): TodoItem? {
        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Get,
            path = "list/$id",
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("getItem", result)
                result.data.element.toTodoItem()
            }
            is NetworkResult.Error -> {
                onError("getItem", result)
                null
            }
        }
    }
    suspend fun addItem(todoItem: TodoItem) {
        // TODO : Get device ID
        val body = wrapper(json.encodeToString(todoItem.toDto("0"))).toString()
        Log.d(NetworkConstants.DEBUG, body)

        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Post,
            path = "list",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
            body = body
        )
        when (result) {
            is NetworkResult.Success -> {
                onSuccess("addItem", result)
            }
            is NetworkResult.Error -> {
                onError("addItem", result)
            }
        }
    }
    suspend fun updateItem(todoItem: TodoItem) {
        // TODO : Get device ID
        val body = wrapper(json.encodeToString(todoItem.toDto("0"))).toString()
        Log.d(NetworkConstants.DEBUG, body)

        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Put,
            path = "list/${todoItem.id}",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
            body = body
        )
        when (result) {
            is NetworkResult.Success -> {
                onSuccess("updateItem", result)
            }
            is NetworkResult.Error -> {
                onError("updateItem", result)
            }
        }
    }
    suspend fun deleteItem(id: String) {
        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Delete,
            path = "list/$id",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
        )
        when (result) {
            is NetworkResult.Success -> {
                onSuccess("deleteItem", result)
            }
            is NetworkResult.Error -> {
                onError("deleteItem", result)
            }
        }
    }
    suspend fun deleteAll() {
        val items = getItemsList()
        Log.d(NetworkConstants.DEBUG,
            "Response deleteAll\n" +
                "--------------------------------------------------")
        items.forEach {
            deleteItem(it.value.id)
        }
    }
    private fun wrapper(body: String) = buildJsonObject {
        put("element", json.parseToJsonElement(body))
    }
    private fun onSuccess(name: String, result: NetworkResult.Success<out ResponseDto>) {
        lastKnownRevision = result.data.revision
        Log.d(NetworkConstants.DEBUG,
            "Response $name\n" +
                "${result.data}\n" +
                "revision: ${result.data.revision}\n" +
                "--------------------------------------------------")
    }
    private fun onError(name: String, result: NetworkResult.Error<out ErrorDto>) {
        Log.e(NetworkConstants.DEBUG,
            "Response $name\n" +
                "Error: ${result.message}\n" +
                "Code: ${result.errorCode}\n" +
                "--------------------------------------------------")
    }
}
