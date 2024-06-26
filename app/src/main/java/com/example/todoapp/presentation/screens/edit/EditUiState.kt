package com.example.todoapp.presentation.screens.edit

import com.example.todoapp.domain.models.Priority
import java.time.LocalDate

data class EditUiState(
    val text: String = "",
    val priority: Priority = Priority.NORMAL,
    val deadline: LocalDate? = null,
    val hasDeadline: Boolean = false,
)