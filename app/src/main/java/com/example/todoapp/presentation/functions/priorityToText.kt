package com.example.todoapp.presentation.functions

import com.example.todoapp.R
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.presentation.constants.Emoji

fun priorityToRId(priority: Priority): Int {
    return when (priority) {
        Priority.HIGH -> R.string.priorityHigh
        Priority.NORMAL -> R.string.priorityNormal
        Priority.LOW -> R.string.priorityLow
    }
}

fun getPriorityEmoji(priority: Priority) = when(priority) {
    Priority.HIGH -> Constants.CRITICAL_PRIORITY_TASK_EMOJI
    Priority.LOW -> Constants.LOW_PRIORITY_TASK_EMOJI
    else -> ""
}
