package com.example.todoapp.presentation.screens.list.action

import com.example.todoapp.domain.models.TodoItem

sealed class ListUiAction {
    data class UpdateTodoItem(val todoItem: TodoItem): ListUiAction()
    data class RemoveTodoItem(val todoItem: TodoItem) : ListUiAction()
    data class ChangeDoneTaskVisibility(val hideDoneTask: Boolean): ListUiAction()
}