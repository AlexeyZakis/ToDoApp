package com.example.todoapp.presentation.themes

import androidx.compose.ui.graphics.Color

abstract class Theme {
    abstract val lightSupportSeparator: Color
    abstract val lightSupportOverlay: Color

    abstract val lightLabelPrimary: Color
    abstract val lightLabelSecondary: Color
    abstract val lightLabelTertiary: Color
    abstract val lightLabelDisable: Color

    abstract val lightRed: Color
    abstract val lightGreen: Color
    abstract val lightBlue: Color
    abstract val lightGray: Color
    abstract val lightGrayLight: Color
    abstract val lightWhite: Color

    abstract val lightBackPrimary: Color
    abstract val lightBackSecondary: Color
    abstract val lightBackElevated: Color

    // Dark
    abstract val darkSupportSeparator: Color
    abstract val darkSupportOverlay: Color
    abstract val darkLabelPrimary: Color
    abstract val darkLabelSecondary: Color
    abstract val darkLabelTertiary: Color
    abstract val darkLabelDisable: Color

    abstract val darkRed: Color
    abstract val darkGreen: Color
    abstract val darkBlue: Color
    abstract val darkGray: Color
    abstract val darkGrayLight: Color
    abstract val darkWhite: Color

    abstract val darkBackPrimary: Color
    abstract val darkBackSecondary: Color
    abstract val darkBackElevated: Color
}