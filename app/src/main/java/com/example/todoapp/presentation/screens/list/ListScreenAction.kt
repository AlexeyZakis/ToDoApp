package com.example.todoapp.presentation.screens.list

import com.example.todoapp.domain.models.TodoItem

/**
 * Set of actions on task list screen
 **/
sealed class ListScreenAction {
    data class OnTodoItemCompletionChange(val todoItem: TodoItem) : ListScreenAction()
    data class OnTodoItemDelete(val todoItem: TodoItem) : ListScreenAction()
    data class OnDoneTaskVisibilityChange(val hideDoneTask: Boolean) : ListScreenAction()
    data object OnRefreshData : ListScreenAction()
    data object OnErrorSnackBarClick : ListScreenAction()
    data object Nothing : ListScreenAction()
}
