package com.example.todoapp.data.storage

import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem

interface TaskStorage {
    suspend fun getList(): StorageResult<Map<String, TodoItem>>
    suspend fun updateList(items: Items): StorageResult<Nothing>
    suspend fun getItem(id: String): StorageResult<TodoItem>
    suspend fun addItem(todoItem: TodoItem): StorageResult<Nothing>
    suspend fun updateItem(todoItem: TodoItem): StorageResult<Nothing>
    suspend fun deleteItem(todoItem: TodoItem): StorageResult<Nothing>
}
