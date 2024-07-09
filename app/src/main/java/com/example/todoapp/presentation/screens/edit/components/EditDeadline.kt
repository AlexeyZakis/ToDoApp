package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.presentation.screens.edit.EditScreenAction
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.DateFormat

@Composable
fun EditDeadline(
    modifier: Modifier,
    hasDeadline: Boolean,
    deadlineDate: Long?,
    screenAction: (EditScreenAction) -> Unit,
) {
    var isDeadlineHidden by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = if (hasDeadline) {
            modifier.clickable {
                isDeadlineHidden = false
            }
        } else {
            modifier
        }
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.taskDeadline),
                color = themeColors.labelPrimary,
            )
            Text(
                text = DateFormat.getDateString(deadlineDate),
                color = themeColors.colorBlue,
                modifier = Modifier.alpha(
                    if (hasDeadline) {
                        1f
                    } else {
                        0f
                    }
                ),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = hasDeadline,
            colors = SwitchDefaults.colors(
                checkedThumbColor = themeColors.colorBlue,
                checkedTrackColor = themeColors.colorBlue,
                uncheckedThumbColor = themeColors.backElevated,
                uncheckedTrackColor = themeColors.supportOverlay
            ),
            onCheckedChange = { checked ->
                screenAction(EditScreenAction.OnDeadlineExistenceChange(checked))
                if (checked) {
                    isDeadlineHidden = false
                } else {
                    screenAction(EditScreenAction.OnDeadlineExistenceChange(false))
                }
            }
        )
        EditDeadlinePicker(
            isDatePickerHidden = isDeadlineHidden,
            deadline = deadlineDate ?: System.currentTimeMillis(),
            screenAction = screenAction,
            closeDialog = {
                isDeadlineHidden = true
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditDeadlineNoDeadlinePreview() {
    AppTheme(theme = MainTheme) {
        EditDeadline(
            deadlineDate = System.currentTimeMillis(),
            hasDeadline = false,
            screenAction = {},
            modifier = Modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditDeadlinePreview() {
    AppTheme(theme = MainTheme) {
        EditDeadline(
            deadlineDate = System.currentTimeMillis(),
            hasDeadline = true,
            screenAction = {},
            modifier = Modifier,
        )
    }
}
