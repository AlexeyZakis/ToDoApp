package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.getPriorityEmoji
import com.example.todoapp.presentation.utils.priorityToRId

@Composable
fun EditPriority(
    modifier: Modifier,
    priority: Priority,
    onClick: () -> Unit,
) {
    val isHighPriority = remember(priority) { priority == Priority.HIGH }

    Column(
        modifier = modifier
            .clickable { onClick() }
    ) {
        val priorityText = getPriorityEmoji(priority) +
            stringResource(id = priorityToRId(priority))
        Text(
            text = stringResource(id = R.string.priorityTitle),
            style = MaterialTheme.typography.bodyMedium,
            color = themeColors.labelPrimary
        )
        Text(
            text = priorityText,
            style = MaterialTheme.typography.bodySmall,
            color = if (isHighPriority) {
                themeColors.colorRed
            } else {
                themeColors.labelTertiary
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityNormalPreview() {
    AppTheme(theme = MainTheme) {
        EditPriority(
            priority = Priority.NORMAL,
            onClick = {},
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityLowPreview() {
    AppTheme(theme = MainTheme) {
        EditPriority(
            priority = Priority.LOW,
            onClick = {},
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPriorityHighPreview() {
    AppTheme(theme = MainTheme) {
        EditPriority(
            priority = Priority.HIGH,
            onClick = {},
            modifier = Modifier,
        )
    }
}
