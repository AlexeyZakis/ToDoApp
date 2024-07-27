package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.presentation.screens.edit.EditScreenAction
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun EditTaskText(
    modifier: Modifier,
    text: String,
    screenAction: (EditScreenAction) -> Unit,
) {
    BasicTextField(
        value = text,
        textStyle = TextStyle(color = themeColors.labelPrimary),
        onValueChange = { screenAction(EditScreenAction.OnTextChange(it)) },
        minLines = 3,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = themeColors.backSecondary,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
    ) { textField ->
        Box(modifier = Modifier.padding(16.dp)) {
            if (text.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.taskTextHint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeColors.labelTertiary
                )
            }
            textField.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTaskTextPreview() {
    AppTheme(theme = MainTheme) {
        EditTaskText(
            text = "",
            modifier = Modifier,
            screenAction = {}
        )
    }
}
