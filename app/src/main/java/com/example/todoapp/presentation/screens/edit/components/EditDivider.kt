package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun EditDivider(
    padding: PaddingValues,
) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        color = themeColors.supportSeparator
    )
}

@Preview(showBackground = true)
@Composable
private fun EditDividerPreview() {
    AppTheme(theme = MainTheme) {
        EditDivider(padding = PaddingValues(horizontal = 16.dp))
    }
}