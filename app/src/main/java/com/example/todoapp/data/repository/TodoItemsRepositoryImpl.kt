package com.example.todoapp.data.repository

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class TodoItemsRepositoryImpl(taskStorage: TaskStorage): TodoItemsRepository {
    private var _todoItems: MutableStateFlow<Map<String, TodoItem>>
    override var todoItems: StateFlow<Map<String, TodoItem>>
    private val _hideDoneTask = MutableStateFlow(Constants.HIDE_DONE_TASK_DEFAULT)
    override val hideDoneTask = _hideDoneTask.asStateFlow()

    init {
        runBlocking {
            _todoItems = MutableStateFlow(taskStorage.get())
            todoItems = _todoItems.asStateFlow()
        }
    }

    override val doneTaskCounter: Int
        get() = _todoItems.value.values.count { it.isDone }

    override fun getTodoItem(todoItemId: String): TodoItem? {
        return _todoItems.value[todoItemId]
    }

    override fun addTodoItem(todoItem: TodoItem) {
        addOrEditItem(todoItem)
    }

    override fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        _hideDoneTask.update {
            hideDoneTask
        }
    }

    override fun deleteTodoItem(todoItemId: String) {
        if (_todoItems.value.contains(todoItemId)) {
            _todoItems.update {
                val updatedValues = todoItems.value.toMutableMap()
                updatedValues.remove(todoItemId)
                updatedValues.toMap()
            }
        }
    }

    override fun editTodoItem(todoItem: TodoItem) {
        if (_todoItems.value.contains(todoItem.id)) {
            addOrEditItem(todoItem)
        }
    }
    private fun addOrEditItem(todoItem: TodoItem) {
        _todoItems.update {
            val updatedValues = todoItems.value.toMutableMap()
            updatedValues[todoItem.id] = todoItem
            updatedValues.toMap()
        }
    }
}