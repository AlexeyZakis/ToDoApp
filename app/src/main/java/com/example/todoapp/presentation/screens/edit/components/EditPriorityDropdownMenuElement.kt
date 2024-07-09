package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.getPriorityEmoji
import com.example.todoapp.presentation.utils.priorityToRId

@Composable
fun EditPriorityDropdownMenuElement(
    isSelected: Boolean,
    priority: Priority,
    onClick: () -> Unit,
) {
    var selected by remember { mutableStateOf(isSelected) }
    val color = when (priority) {
        Priority.HIGH -> themeColors.colorRed
        else -> themeColors.labelPrimary
    }
    val priorityText = getPriorityEmoji(priority) +
        stringResource(id = priorityToRId(priority))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected = !selected
                onClick()
            }
            .padding(8.dp)
    ) {
        Text(
            text = priorityText,
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityDropdownMenuItemNormalPreview() {
    AppTheme(theme = MainTheme) {
        EditPriorityDropdownMenuElement(
            isSelected = true,
            priority = Priority.NORMAL,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityDropdownMenuItemLowPreview() {
    AppTheme(theme = MainTheme) {
        EditPriorityDropdownMenuElement(
            isSelected = true,
            priority = Priority.LOW,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityDropdownMenuItemHighPreview() {
    AppTheme(theme = MainTheme) {
        EditPriorityDropdownMenuElement(
            isSelected = true,
            priority = Priority.HIGH,
            onClick = {},
        )
    }
}
