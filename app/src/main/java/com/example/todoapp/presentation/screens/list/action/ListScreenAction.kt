package com.example.todoapp.presentation.screens.list.action

import com.example.todoapp.domain.models.TodoItem

sealed class ListScreenAction {
    data class ChangeTodoItemCompletion(val todoItem: TodoItem): ListScreenAction()
    data class DeleteTodoItem(val todoItem: TodoItem) : ListScreenAction()
    data class ChangeDoneTaskVisibility(val hideDoneTask: Boolean): ListScreenAction()
}