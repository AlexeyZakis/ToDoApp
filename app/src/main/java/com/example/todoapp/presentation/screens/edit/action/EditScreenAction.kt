package com.example.todoapp.presentation.screens.edit.action

import com.example.todoapp.domain.models.Priority
import java.time.LocalDate

sealed class EditScreenAction {
    data object SaveTask: EditScreenAction()
    data object DeleteTask : EditScreenAction()
    data class UpdateText(val text: String) : EditScreenAction()
    data class UpdateDeadlineExistence(val hasDeadline: Boolean): EditScreenAction()
    data class UpdateDeadline(val deadline: LocalDate) : EditScreenAction()
    data class UpdatePriority(val priority: Priority) : EditScreenAction()
}