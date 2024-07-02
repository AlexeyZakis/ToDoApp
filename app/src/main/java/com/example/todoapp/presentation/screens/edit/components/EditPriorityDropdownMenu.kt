package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.screens.edit.action.EditScreenAction
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun PriorityDropdownMenu(
    expanded: Boolean,
    lastPriority: Priority,
    screenAction: (EditScreenAction) -> Unit,
    onDismiss: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier.background(themeColors.backElevated)
    ) {
        for (priority in Priority.entries) {
            EditPriorityDropdownMenuElement(
                isSelected = lastPriority == priority,
                priority = priority,
                onClick = {
                    screenAction(EditScreenAction.UpdatePriority(priority))
                    onDismiss()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PriorityDropdownMenuPreview() {
    AppTheme(theme = MainTheme) {
        PriorityDropdownMenu(
            lastPriority = Priority.NORMAL,
            expanded = false,
            onDismiss = {},
            screenAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PriorityDropdownMenuExpandedPreview() {
    AppTheme(theme = MainTheme) {
        PriorityDropdownMenu(
            lastPriority = Priority.HIGH,
            expanded = true,
            onDismiss = {},
            screenAction = {}
        )
    }
}
