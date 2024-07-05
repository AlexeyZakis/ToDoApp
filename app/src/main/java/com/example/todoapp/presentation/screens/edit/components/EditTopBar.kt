package com.example.todoapp.presentation.screens.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.presentation.screens.edit.EditScreenAction
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun EditTopBar(
    screenAction: (EditScreenAction) -> Unit,
    navigateBack: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.closeBtnDescription),
            tint = themeColors.labelPrimary,
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { navigateBack() }
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = {
                screenAction(EditScreenAction.OnTaskSave)
          },
            colors = ButtonDefaults.textButtonColors(
                contentColor = themeColors.colorBlue,
            ),
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTopAppBarPreview() {
    AppTheme(theme = MainTheme) {
        EditTopBar(
            screenAction = {},
            navigateBack = {},
        )
    }
}