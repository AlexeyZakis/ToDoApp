package com.example.todoapp.data.network

import android.util.Log
import com.example.todoapp.data.network.Constants.NetworkConstants
import com.example.todoapp.data.network.DTOs.ElementDto
import com.example.todoapp.data.network.DTOs.ErrorDto
import com.example.todoapp.data.network.DTOs.ListDto
import com.example.todoapp.data.network.DTOs.ResponseDto
import com.example.todoapp.data.network.DTOs.TodoItemDto
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.buildJsonObject

object Network {
    private var lastKnownRevision = 0

    suspend fun getList(): ListDto? {
        val result = client.safeRequest<ListDto, ErrorDto>(
            method = HttpMethod.Get,
            path = NetworkConstants.PATH,
//            params = StringValues.build {
//                append("", "")
//            }
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("getItemsList", result)
            }
            is NetworkResult.Error -> {
                onError("getItemsList", result)
            }
        }
    }
    suspend fun updateList(list: Items): ListDto? {
        // TODO : Get device ID
        val body = wrap(
            NetworkConstants.Wrappers.LIST,
            json.encodeToString(list.items.map { it.toDto("0") })
        ).toString()
        Log.d(NetworkConstants.DEBUG, body)
        val result = client.safeRequest<ListDto, ErrorDto>(
            method = HttpMethod.Patch,
            path = NetworkConstants.PATH,
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
            body = body
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("updateList", result)
            }
            is NetworkResult.Error -> {
                onError("updateList", result)
            }
        }
    }
    suspend fun getItem(id: String): ElementDto? {
        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Get,
            path = "${NetworkConstants.PATH}/$id",
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("getItem", result)
            }
            is NetworkResult.Error -> {
                onError("getItem", result)
            }
        }
    }
    suspend fun addItem(todoItem: TodoItem): ElementDto? {
        // TODO : Get device ID
        val body = wrap(
            NetworkConstants.Wrappers.ELEMENT,
            json.encodeToString(todoItem.toDto("0"))
        ).toString()
        Log.d(NetworkConstants.DEBUG, body)

        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Post,
            path = NetworkConstants.PATH,
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
            body = body
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("addItem", result)
            }
            is NetworkResult.Error -> {
                onError("addItem", result)
            }
        }
    }
    suspend fun updateItem(todoItem: TodoItem): ElementDto? {
        // TODO : Get device ID
        val body = wrap(
            NetworkConstants.Wrappers.ELEMENT,
            json.encodeToString(todoItem.toDto("0"))
        ).toString()
        Log.d(NetworkConstants.DEBUG, body)

        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Put,
            path = "${NetworkConstants.PATH}/${todoItem.id}",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
            ),
            body = body
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("updateItem", result)
            }
            is NetworkResult.Error -> {
                onError("updateItem", result)
            }
        }
    }
    suspend fun deleteItem(id: String): ElementDto? {
        val result = client.safeRequest<ElementDto, ErrorDto>(
            method = HttpMethod.Delete,
            path = "${NetworkConstants.PATH}/$id",
            headers = mapOf(
                NetworkConstants.Headers.REVISION to "$lastKnownRevision",
                NetworkConstants.Headers.FAILS to "50",
            ),
        )
        return when (result) {
            is NetworkResult.Success -> {
                onSuccess("deleteItem", result)
            }
            is NetworkResult.Error -> {
                onError("deleteItem", result)
            }
        }
    }
    // For debug
    suspend fun deleteAll() {
        val items = getList()?.list?.map {
            it.toTodoItem()
        }?.associateBy { it.id } ?: mapOf()
        Log.d(NetworkConstants.DEBUG,
            "Response deleteAll\n" +
                "--------------------------------------------------")
        items.forEach {
            deleteItem(it.value.id)
        }
    }
    private fun wrap(wrapper: String, body: String) = buildJsonObject {
        put(wrapper, json.parseToJsonElement(body))
    }
    private fun <T: ResponseDto> onSuccess(name: String, result: NetworkResult.Success<out T>): T {
        lastKnownRevision = result.data.revision
        Log.d(NetworkConstants.DEBUG,
            "Response $name\n" +
                "${result.data}\n" +
                "revision: ${result.data.revision}\n" +
                "--------------------------------------------------")
        return result.data
    }
    private fun onError(name: String, result: NetworkResult.Error<out ErrorDto>): Nothing? {
        Log.e(NetworkConstants.DEBUG,
            "Response $name\n" +
                "Error: ${result.message}\n" +
                "Code: ${result.errorCode}\n" +
                "--------------------------------------------------")
        return null
    }
}
