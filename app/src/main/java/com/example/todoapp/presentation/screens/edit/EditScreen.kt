package com.example.todoapp.presentation.screens.edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.constants.Mode
import com.example.todoapp.presentation.screens.BottomSheet
import com.example.todoapp.presentation.screens.edit.components.EditDeadline
import com.example.todoapp.presentation.screens.edit.components.EditDeleteBtn
import com.example.todoapp.presentation.screens.edit.components.EditDivider
import com.example.todoapp.presentation.screens.edit.components.EditPriority
import com.example.todoapp.presentation.screens.edit.components.EditTaskText
import com.example.todoapp.presentation.screens.edit.components.EditTopBar
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.getPriorityEmoji
import com.example.todoapp.presentation.utils.priorityToRId
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditScreen(
    screenState: EditScreenState,
    screenAction: (EditScreenAction) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    // Priority animation
    var animate by remember { mutableStateOf(false) }

    val initialColor = themeColors.backPrimary
    val targetColor = themeColors.colorRed.copy(alpha = 0.2f)
    val durationMillis = 2000

    val backgroundColor by animateColorAsState(
        targetValue = if (animate) targetColor else initialColor,
        animationSpec = tween(durationMillis)
    )

    LaunchedEffect(animate) {
        delay(durationMillis.toLong())
        animate = false
    }

    val scope = rememberCoroutineScope()
    val sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    BottomSheet(
        sheetState = sheetState,
        values = Priority.entries.toTypedArray(),
        valueToStringResId = { priority -> priorityToRId(priority) },
        selected = screenState.priority,
        onSelected = { priority ->
            if (priority == Priority.HIGH) {
                animate = true
            }
            screenAction(EditScreenAction.OnPrioritySelect(priority))
        },
        valueMapColors = mapOf(
            Priority.HIGH to themeColors.colorRed
        ),
        valueMapPrefix = mapOf(
            Priority.LOW to getPriorityEmoji(Priority.LOW),
            Priority.HIGH to getPriorityEmoji(Priority.HIGH),
        )
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackBarHostState) { data ->
                    Snackbar(
                        backgroundColor = themeColors.backElevated,
                        contentColor = themeColors.labelPrimary,
                        actionColor = themeColors.labelPrimary,
                        snackbarData = data
                    )
                }
            },
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
                    onClick = {
                        scope.launch {
                            sheetState.show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .padding(16.dp),
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
                    enabled = screenState.mode == Mode.EDIT_ITEM,
                    screenAction = screenAction,
                )
            }
        }
    }
    LaunchedEffect(screenState.isSuccessfulAction) {
        if (!screenState.isSuccessfulAction) {
            return@LaunchedEffect
        }
        val message = when (screenState.snackBarOnErrorAction) {
            EditScreenAction.OnTaskSave -> context.getString(R.string.saveError)
            EditScreenAction.OnTaskDelete -> context.getString(R.string.deleteError)
            else -> ""
        }
        val snackBarResult = snackBarHostState.showSnackbar(
            message = message,
            actionLabel = context.getString(R.string.retry),
            duration = SnackbarDuration.Indefinite
        )
        if (snackBarResult == SnackbarResult.ActionPerformed) {
            screenAction(EditScreenAction.OnErrorSnackBarClick)
            screenAction(screenState.snackBarOnErrorAction)
        }
    }
    LaunchedEffect(screenState.isLeaving) {
        if (!screenState.isLeaving) {
            return@LaunchedEffect
        }
        navigateBack()
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
                deadline = System.currentTimeMillis()
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
