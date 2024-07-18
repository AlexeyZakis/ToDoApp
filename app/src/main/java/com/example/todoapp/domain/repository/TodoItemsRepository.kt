package com.example.todoapp.domain.repository

import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.domain.models.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    val todoItems: StateFlow<Map<String, TodoItem>>
    val hideDoneTask: StateFlow<Boolean>
    val isDataLoadedSuccessfully: StateFlow<Boolean>
    val hasInternet: StateFlow<Boolean>
    val doneTaskCounter: Int

    fun changeDoneTaskVisibility(hideDoneTask: Boolean)
    suspend fun addTodoItem(todoItem: TodoItem): StorageResult<Nothing>
    suspend fun deleteTodoItem(todoItem: TodoItem): StorageResult<Nothing>
    suspend fun editTodoItem(todoItem: TodoItem): StorageResult<Nothing>
    suspend fun getTodoItem(todoItemId: String): StorageResult<TodoItem>
    fun destroy()
    fun setConnectedStatus(isConnected: Boolean)
    suspend fun sync(): StorageResult<Nothing>
}
