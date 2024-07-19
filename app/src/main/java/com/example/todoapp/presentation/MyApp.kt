package com.example.todoapp.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.presentation.data.LocalThemeRepository
import com.example.todoapp.presentation.data.models.ThemeMode
import com.example.todoapp.presentation.screens.navigation.AppNavigation
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme

@Composable
fun MyApp() {
    val themeRepository = LocalThemeRepository.current
    val themeMode by themeRepository.themeMode.collectAsState()

    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    AppTheme(MainTheme, darkTheme = isDarkTheme) {
        AppNavigation()
    }
}