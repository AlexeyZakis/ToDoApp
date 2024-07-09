package com.example.todoapp.presentation.screens.edit

import com.example.todoapp.domain.models.Priority

/**
 * Set of actions on edit task screen
 **/
sealed class EditScreenAction {
    data object OnTaskSave : EditScreenAction()
    data object OnTaskDelete : EditScreenAction()
    data class OnTextChange(val text: String) : EditScreenAction()
    data class OnDeadlineExistenceChange(val hasDeadline: Boolean) : EditScreenAction()
    data class OnDeadlineSelect(val deadline: Long) : EditScreenAction()
    data class OnPrioritySelect(val priority: Priority) : EditScreenAction()
    data object OnErrorSnackBarClick : EditScreenAction()
    data object Nothing : EditScreenAction()
}
