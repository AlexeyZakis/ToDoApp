package com.example.todoapp.presentation.screens.list.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun ListHideDoneTaskSwitch(
    modifier: Modifier = Modifier,
    hideDoneTask: Boolean = false,
    onClick: () -> Unit,
) {
    val contentDescription = stringResource(id = R.string.hideDoneTaskBtnDescription)
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = themeColors.colorBlue
        ),
        onClick = { onClick() },
        modifier = modifier
            .semantics { this.contentDescription = contentDescription }
    ) {
        Icon(
            painter = painterResource(
                id = if (hideDoneTask) {
                    R.drawable.baseline_visibility_off_24
                } else {
                    R.drawable.baseline_visibility_24
                }
            ),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListHideDoneTaskSwitchPreview() {
    AppTheme(theme = MainTheme) {
        ListHideDoneTaskSwitch(
            hideDoneTask = false,
            onClick = {}
        )
    }
}
