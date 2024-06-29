package com.example.todoapp.presentation.screens.edit

import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.constants.Mode
import java.time.LocalDate

data class EditScreenState(
    val text: String = "",
    val priority: Priority = Priority.NORMAL,
    val deadline: LocalDate? = null,
    val hasDeadline: Boolean = false,
    val mode: Mode = Mode.ADD_ITEM,
)