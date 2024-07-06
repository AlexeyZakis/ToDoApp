package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun ListTitle(
    modifier: Modifier,
    hideDoneTask: Boolean,
    doneTaskCounter: Int,
    onVisibilitySwitchClick: (Boolean) -> Unit
) {
    Row(modifier = modifier) {
        Column {
            Text(
                text = stringResource(id = R.string.listTitle),
                color = themeColors.labelPrimary,
                fontSize = 32.sp
            )
            Text(
                text = "${stringResource(id = R.string.doneTaskCounter)} - $doneTaskCounter",
                color = themeColors.labelTertiary,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ListHideDoneTaskSwitch(
            hideDoneTask = hideDoneTask,
            onClick = { onVisibilitySwitchClick(!hideDoneTask) },
            modifier = Modifier.padding(end = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTitlePreview() {
    AppTheme(theme = MainTheme) {
        ListTitle(
            doneTaskCounter = 5,
            hideDoneTask = true,
            modifier = Modifier,
            onVisibilitySwitchClick = {}
        )
    }
}
