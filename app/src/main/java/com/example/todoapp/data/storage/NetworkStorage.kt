package com.example.todoapp.data.storage

import com.example.todoapp.data.network.NetworkApi
import com.example.todoapp.data.network.NetworkConstants
import com.example.todoapp.data.network.dtos.ResponseDto
import com.example.todoapp.data.network.toTodoItem
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem

/**
 * Provide access to data on the server
 **/
class NetworkStorage(private val networkApi: NetworkApi) : TaskStorage {
    override suspend fun getList(): StorageResult<Map<String, TodoItem>> {
        val networkResult = networkApi.getList()

        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        val result = networkResult?.list?.map {
            it.toTodoItem()
        }?.associateBy { it.id } ?: mapOf()

        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = result
        )
    }

    override suspend fun updateList(items: Items): StorageResult<Nothing> {
        val networkResult = networkApi.updateList(items)
        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun getItem(id: String): StorageResult<TodoItem> {
        val networkResult = networkApi.getItem(id)
        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = networkResult?.element?.toTodoItem()
        )
    }

    override suspend fun addItem(todoItem: TodoItem): StorageResult<Nothing> {
        val networkResult = networkApi.addItem(todoItem)
        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun updateItem(todoItem: TodoItem): StorageResult<Nothing> {
        val networkResult = networkApi.updateItem(todoItem)
        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun deleteItem(todoItem: TodoItem): StorageResult<Nothing> {
        val networkResult = networkApi.deleteItem(todoItem)
        if (isError(networkResult)) {
            return StorageResult(
                status = StorageResultStatus.ERROR,
                data = null
            )
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    private fun isError(networkResult: ResponseDto?) =
        networkResult == null ||
                networkResult.status != NetworkConstants.SUCCESS_STATUS
}
