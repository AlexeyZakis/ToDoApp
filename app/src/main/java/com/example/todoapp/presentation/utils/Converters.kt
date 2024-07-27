package com.example.todoapp.presentation.utils

import com.example.todoapp.R
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.presentation.data.models.ThemeMode

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

fun themeModeToRId(themeMode: ThemeMode): Int {
    return when (themeMode) {
        ThemeMode.DARK -> R.string.themeModeDark
        ThemeMode.LIGHT -> R.string.themeModeLight
        ThemeMode.SYSTEM -> R.string.themeModeSystem
    }
}

fun getThemeModeEmoji(themeMode: ThemeMode) = when(themeMode) {
    ThemeMode.DARK -> Constants.DARK_THEME_EMOJI
    ThemeMode.LIGHT -> Constants.LIGHT_THEME_EMOJI
    ThemeMode.SYSTEM -> Constants.SYSTEM_THEME_EMOJI
    else -> ""
}
