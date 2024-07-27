package com.example.todoapp.presentation.data

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.todoapp.presentation.data.models.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeRepository {
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }
}

val LocalThemeRepository = staticCompositionLocalOf<ThemeRepository> {
    error("No ThemeRepository provided")
}