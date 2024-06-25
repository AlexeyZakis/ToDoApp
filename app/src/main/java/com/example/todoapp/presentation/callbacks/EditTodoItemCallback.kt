package com.example.todoapp.presentation.callbacks

import com.example.todoapp.domain.models.TodoItemId

interface EditTodoItemCallback {
    fun onEditTodoItem(todoItemId: TodoItemId)
}