package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
    onAboutAppClick: () -> Unit,
    onThemeClick: () -> Unit,
    onVisibilitySwitchClick: (Boolean) -> Unit
) {
    Row(modifier = modifier) {
        Column {
            Text(
                text = stringResource(id = R.string.listTitle),
                style = MaterialTheme.typography.labelLarge,
                color = themeColors.labelPrimary,
                fontSize = 32.sp
            )
            Text(
                text = "${stringResource(id = R.string.doneTaskCounter)} - $doneTaskCounter",
                style = MaterialTheme.typography.titleSmall,
                color = themeColors.labelTertiary,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ListThemeButton(
                onClick = { onThemeClick() },
            )
            ListAboutAppButton(
                onClick = { onAboutAppClick() },
            )
            ListHideDoneTaskSwitch(
                hideDoneTask = hideDoneTask,
                onClick = { onVisibilitySwitchClick(!hideDoneTask) },
                modifier = Modifier
                    .padding(end = 12.dp)
                    .testTag("hideDoneTaskBtn")
            )
        }
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
            onVisibilitySwitchClick = {},
            onThemeClick = {},
            onAboutAppClick = {},
        )
    }
}
