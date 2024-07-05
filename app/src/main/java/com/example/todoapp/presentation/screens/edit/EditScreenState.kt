package com.example.todoapp.presentation.screens.edit

import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.constants.Mode

data class EditScreenState(
    val text: String = "",
    val priority: Priority = Priority.NORMAL,
    val deadline: Long? = null,
    val hasDeadline: Boolean = false,
    val mode: Mode = Mode.ADD_ITEM,
    val isSuccessfulAction: Boolean = false,
    val isLeaving: Boolean = false,
    val snackBarOnErrorAction: EditScreenAction = EditScreenAction.Nothing,
)