package com.example.todoapp.data.repository

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Responsible for querying and manipulating data from the underlying data source
 **/
class TodoItemsRepositoryImpl(
    private val taskStorage: TaskStorage
) : TodoItemsRepository {
    private val _todoItems: MutableStateFlow<Map<String, TodoItem>> = MutableStateFlow(emptyMap())
    override val todoItems = _todoItems.asStateFlow()

    private val _hasInternet = MutableStateFlow(false)
    override val hasInternet = _hasInternet

    private val _hideDoneTask = MutableStateFlow(Constants.HIDE_DONE_TASK_DEFAULT)
    override val hideDoneTask = _hideDoneTask.asStateFlow()

    private val _isDataLoadedSuccessfully = MutableStateFlow(false)
    override val isDataLoadedSuccessfully = _isDataLoadedSuccessfully.asStateFlow()

    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        loadTodoItems()
    }

    private fun loadTodoItems() {
        repositoryScope.launch {
            val response = taskStorage.getList()
            if (response.status.isSuccess()) {
                response.data?.let { items ->
                    _todoItems.value = items
                    _isDataLoadedSuccessfully.value = true
                }
            } else {
                _isDataLoadedSuccessfully.value = false
            }
        }
    }

    override fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        _hideDoneTask.update {
            hideDoneTask
        }
    }
    override val doneTaskCounter: Int
        get() = _todoItems.value.values.count { it.isDone }

    override suspend fun getTodoItem(todoItemId: String): StorageResult<TodoItem> {
        val response = taskStorage.getItem(todoItemId)
        if (response.status.isSuccess()) {
            return StorageResult(status = response.status, data = _todoItems.value[todoItemId])
        }
        return response
    }
    override suspend fun addTodoItem(todoItem: TodoItem): StorageResult<Nothing> {
        val response = taskStorage.addItem(todoItem)
        if (response.status.isSuccess()) {
            addOrEditItem(todoItem)
        }
        return response
    }
    override suspend fun deleteTodoItem(todoItemId: String): StorageResult<Nothing> {
        val response = taskStorage.deleteItem(todoItemId)
        if (
            response.status.isSuccess() &&
            _todoItems.value.contains(todoItemId)
        ) {
            _todoItems.update {
                val updatedValues = todoItems.value.toMutableMap()
                updatedValues.remove(todoItemId)
                updatedValues.toMap()
            }
        }
        return response
    }
    override suspend fun editTodoItem(todoItem: TodoItem): StorageResult<Nothing> {
        val response = taskStorage.updateItem(todoItem)
        if (
            response.status.isSuccess() &&
            _todoItems.value.contains(todoItem.id)
        ) {
            addOrEditItem(todoItem)
        }
        return response
    }

    override suspend fun refreshData(): StorageResult<Nothing> {
        val response = taskStorage.getList()
        if (response.status.isSuccess()) {
            response.data?.let { items ->
                _todoItems.value = items
                _isDataLoadedSuccessfully.value = true
            }
        } else {
            _isDataLoadedSuccessfully.value = false
        }
        return StorageResult(status = response.status, data = null)
    }
    private fun addOrEditItem(todoItem: TodoItem) {
        _todoItems.update {
            val updatedValues = todoItems.value.toMutableMap()
            updatedValues[todoItem.id] = todoItem
            updatedValues.toMap()
        }
    }

    override fun setConnectedStatus(isConnected: Boolean) {
        _hasInternet.value = isConnected
    }
    override fun destroy() {
        repositoryScope.cancel()
    }
}
