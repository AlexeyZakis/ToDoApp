package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.presentation.screens.edit.EditScreenAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDeadlinePicker(
    isDatePickerHidden: Boolean,
    deadline: Long,
    screenAction: (EditScreenAction) -> Unit,
    closeDialog: () -> Unit
) {
    if (!isDatePickerHidden) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = deadline
        )
        val confirmEnabled by remember(datePickerState.selectedDateMillis) {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = closeDialog,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            screenAction(EditScreenAction.OnDeadlineSelect(it))
                            screenAction(EditScreenAction.OnDeadlineExistenceChange(true))
                        }
                        closeDialog()
                    },
                    enabled = confirmEnabled
                ) {
                    Text(stringResource(R.string.calendarOk))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(stringResource(R.string.calendarCancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun DatePickerPreview() {
    EditDeadlinePicker(
        isDatePickerHidden = false,
        deadline = System.currentTimeMillis(),
        screenAction = {},
        closeDialog = {}
    )
}