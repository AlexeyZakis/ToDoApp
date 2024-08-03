package com.example.todoapp.data

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem

class EmptyStorage: TaskStorage {
    override suspend fun getList(): StorageResult<Map<String, TodoItem>> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun updateList(items: Items): StorageResult<Nothing> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun getItem(id: String): StorageResult<TodoItem> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun addItem(todoItem: TodoItem): StorageResult<Nothing> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun updateItem(todoItem: TodoItem): StorageResult<Nothing> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun deleteItem(todoItem: TodoItem): StorageResult<Nothing> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }
}