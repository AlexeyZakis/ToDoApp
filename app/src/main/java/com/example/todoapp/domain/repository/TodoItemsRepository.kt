package com.example.todoapp.domain.repository

import com.example.todoapp.domain.models.TodoItem
import kotlinx.coroutines.flow.StateFlow

interface TodoItemsRepository {
    val todoItems: StateFlow<Map<String, TodoItem>>
    val hideDoneTask: StateFlow<Boolean>
    val doneTaskCounter: Int

    fun addTodoItem(todoItem: TodoItem)
    fun changeDoneTaskVisibility(hideDoneTask: Boolean)
    fun deleteTodoItem(todoItemId: String)
    fun editTodoItem(todoItem: TodoItem)
    fun getTodoItem(todoItemId: String): TodoItem?
}