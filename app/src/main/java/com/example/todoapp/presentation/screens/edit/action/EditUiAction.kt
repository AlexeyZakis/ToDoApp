package com.example.todoapp.presentation.screens.edit.action

import com.example.todoapp.domain.models.Priority
import java.time.LocalDate

sealed class EditUiAction {
    data object SaveTask: EditUiAction()
    data object DeleteTask : EditUiAction()
    data class UpdateText(val text: String) : EditUiAction()
    data class UpdateDeadlineExistence(val hasDeadline: Boolean): EditUiAction()
    data class UpdateDeadline(val deadline: LocalDate) : EditUiAction()
    data class UpdatePriority(val priority: Priority) : EditUiAction()
}