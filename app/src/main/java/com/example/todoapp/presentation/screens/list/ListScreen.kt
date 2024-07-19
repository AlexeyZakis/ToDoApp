package com.example.todoapp.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.storage.RuntimeStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.presentation.data.LocalThemeRepository
import com.example.todoapp.presentation.data.models.ThemeMode
import com.example.todoapp.presentation.screens.list.components.ListTitle
import com.example.todoapp.presentation.screens.list.components.PullToRefreshLazyColumn
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ListScreen(
    screenState: ListScreenState,
    screenAction: (ListScreenAction, () -> Unit) -> Unit,
    navigateToAboutApp: () -> Unit,
    navigateToNewItem: () -> Unit,
    navigateToEditItem: (String) -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    val themeRepository = LocalThemeRepository.current
    var themeType by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(themeColors.backPrimary)
    ) {
        ListTitle(
            doneTaskCounter = screenState.doneTaskCounter,
            hideDoneTask = screenState.hideDoneTask,
            onVisibilitySwitchClick = { hideDoneTask ->
                screenAction(
                    ListScreenAction.OnDoneTaskVisibilityChange(hideDoneTask)
                ) {}
            },
            onThemeClick = {
                if (themeType) {
                    themeRepository.setThemeMode(ThemeMode.LIGHT)
                }
                else {
                    themeRepository.setThemeMode(ThemeMode.DARK)
                }
                themeType = !themeType
            },//TODO : REPLACE
            onAboutAppClick = navigateToAboutApp,
            modifier = Modifier
                .padding(
                    top = 60.dp,
                    start = 60.dp,
                    bottom = 16.dp
                )
        )
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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigateToNewItem()
                    },
                    containerColor = themeColors.colorBlue,
                    contentColor = themeColors.colorWhite,
                    shape = CircleShape,
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = stringResource(
                            id = R.string.floatingAddActionButtonDescription
                        )
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { paddingValues ->
                Box(
                    Modifier
                        .background(themeColors.backPrimary)
                        .padding(paddingValues)
                ) {
                    PullToRefreshLazyColumn(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            scope.launch {
                                isRefreshing = true
                                screenAction(ListScreenAction.OnRefreshData) {
                                    isRefreshing = false
                                }
                            }
                        },
                        todoItems = screenState.todoItems,
                        onItemClick = { todoItem ->
                            navigateToEditItem(todoItem.id)
                        },
                        onCompletionChange = { todoItem ->
                            screenAction(
                                ListScreenAction.OnTodoItemCompletionChange(
                                    todoItem.copy(
                                        isDone = !todoItem.isDone
                                    )
                                )
                            ) {}
                        },
                        onAddNewItemClick = navigateToNewItem,
                        onDeleteItem = { todoItem ->
                            screenAction(
                                ListScreenAction.OnTodoItemDelete(todoItem)
                            ) {}
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }
            }
        )
        LaunchedEffect(screenState.isSuccessfulAction) {
            if (!screenState.isSuccessfulAction) {
                return@LaunchedEffect
            }
            val message = when (screenState.snackBarOnErrorAction) {
                is ListScreenAction.OnTodoItemCompletionChange -> context.getString(R.string.editError)
                else -> ""
            }
            val snackBarResult = snackBarHostState.showSnackbar(
                message = message,
                actionLabel = context.getString(R.string.retry),
                duration = SnackbarDuration.Indefinite
            )
            if (snackBarResult == SnackbarResult.ActionPerformed) {
                screenAction(ListScreenAction.OnErrorSnackBarClick) {}
                screenAction(screenState.snackBarOnErrorAction) {}
            }
        }
    }
}

@Preview
@Composable
private fun ListScreenLightPreview() {
    val data: Items
    runBlocking {
        data = Items(RuntimeStorage().getList().data?.values?.toList() ?: listOf())
    }
    AppTheme(theme = MainTheme, darkTheme = false) {
        ListScreen(
            ListScreenState(data),
            screenAction = { _, _ -> },
            navigateToNewItem = {},
            navigateToEditItem = {},
            navigateToAboutApp = {},
        )
    }
}

@Preview
@Composable
private fun ListScreenDarkPreview() {
    val data: Items
    runBlocking {
        data = Items(RuntimeStorage().getList().data?.values?.toList()?.subList(1, 5) ?: listOf())
    }
    AppTheme(theme = MainTheme, darkTheme = true) {
        ListScreen(
            ListScreenState(data),
            screenAction = { _, _ -> },
            navigateToNewItem = {},
            navigateToEditItem = {},
            navigateToAboutApp = {},
        )
    }
}
