package com.example.todoapp.presentation.screens.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.screens.edit.action.EditScreenAction
import com.example.todoapp.presentation.screens.edit.components.EditDeadline
import com.example.todoapp.presentation.screens.edit.components.EditDeleteBtn
import com.example.todoapp.presentation.screens.edit.components.EditDivider
import com.example.todoapp.presentation.screens.edit.components.EditTaskText
import com.example.todoapp.presentation.screens.edit.components.EditTopBar
import com.example.todoapp.presentation.screens.edit.components.EditPriority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import java.time.LocalDate

@Composable
fun EditScreen(
    screenState: EditScreenState,
    screenAction: (EditScreenAction) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            EditTopBar(
                screenAction = screenAction,
                navigateBack = navigateBack
            )
        },
        containerColor = themeColors.backPrimary
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(themeColors.backPrimary)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            EditTaskText(
                text = screenState.text,
                screenAction = screenAction,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            EditPriority(
                priority = screenState.priority,
                screenAction = screenAction,
                modifier = Modifier.padding(16.dp),
            )
            EditDivider(padding = PaddingValues(horizontal = 16.dp))
            EditDeadline(
                deadlineDate = screenState.deadline,
                hasDeadline = screenState.hasDeadline,
                screenAction = screenAction,
                modifier = Modifier.padding(16.dp),
            )
            EditDivider(padding = PaddingValues())
            EditDeleteBtn(
                enabled = screenState.text.isNotEmpty(),
                onClick = {
                    screenAction(EditScreenAction.DeleteTask)
                    navigateBack()
                },
            )
        }
    }
}

@Preview
@Composable
private fun EditScreenLightPreview() {
    AppTheme(theme = MainTheme, darkTheme = false) {
        EditScreen(
            screenState = EditScreenState(),
            screenAction = {},
            navigateBack = {}
        )
    }
}

@Preview
@Composable
private fun EditScreenDarkPreview() {
    AppTheme(theme = MainTheme, darkTheme = true) {
        EditScreen(
            screenState = EditScreenState(),
            screenAction = {},
            navigateBack = {}
        )
    }
}

@Preview
@Composable
private fun EditScreenDarkWithDeadlinePreview() {
    AppTheme(theme = MainTheme, darkTheme = true) {
        EditScreen(
            screenState = EditScreenState(
                hasDeadline = true,
                deadline = LocalDate.now()
            ),
            screenAction = {},
            navigateBack = {}
        )
    }
}

@Preview
@Composable
private fun EditScreenDarkWithTextPreview() {
    AppTheme(theme = MainTheme, darkTheme = true) {
        EditScreen(
            screenState = EditScreenState(
                text = "2.718281828459045"
            ),
            screenAction = {},
            navigateBack = {}
        )
    }
}

@Preview
@Composable
private fun EditScreenDarkWithHighPriorityPreview() {
    AppTheme(theme = MainTheme, darkTheme = true) {
        EditScreen(
            screenState = EditScreenState(
                priority = Priority.HIGH
            ),
            screenAction = {},
            navigateBack = {}
        )
    }
}
