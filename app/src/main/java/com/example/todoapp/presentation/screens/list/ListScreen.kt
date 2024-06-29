package com.example.todoapp.presentation.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.storage.localStorage.LocalStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.presentation.screens.list.action.ListScreenAction
import com.example.todoapp.presentation.screens.list.components.ListTitle
import com.example.todoapp.presentation.screens.list.components.ListTodoItemList
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun ListScreen(
    screenState: ListScreenState,
    screenAction: (ListScreenAction) -> Unit,
    navigateToNewItem: () -> Unit,
    navigateToEditItem: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(themeColors.backPrimary)
    ) {
        ListTitle(
            doneTaskCounter = screenState.doneTaskCounter,
            hideDoneTask = screenState.hideDoneTask,
            onVisibilitySwitchClick = { hideDoneTask ->
                screenAction(
                    ListScreenAction.ChangeDoneTaskVisibility(hideDoneTask)
                )
            },
            modifier = Modifier
                .padding(
                    top = 60.dp,
                    start = 60.dp,
                    bottom = 16.dp
                )
        )
        Scaffold(
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
                Column {
                    Modifier
                        .padding(paddingValues)
                }
                ListTodoItemList(
                    todoItems = screenState.todoItems,
                    onItemClick = { todoItem ->
                        navigateToEditItem(todoItem.id)
                    },
                    onCompletionChange = { todoItem ->
                        screenAction(
                            ListScreenAction.ChangeTodoItemCompletion(
                                todoItem.copy(
                                    isDone = !todoItem.isDone
                                )
                            )
                        )
                    },
                    onAddNewItemClick = navigateToNewItem,
                    onDeleteItem = { todoItem ->
                        screenAction(
                            ListScreenAction.DeleteTodoItem(todoItem)
                        )
                    },
                    modifier = Modifier
                        .background(themeColors.backPrimary)
                        .padding(16.dp)
                )
            }
        )
    }
}

@Preview
@Composable
private fun ListScreenLightPreview() {
    AppTheme(theme = MainTheme, darkTheme = false) {
        ListScreen(
            ListScreenState(Items(LocalStorage().get().values.toList())),
            screenAction = {},
            navigateToNewItem = {},
            navigateToEditItem = {}
        )
    }
}

@Preview
@Composable
private fun ListScreenDarkPreview() {
    AppTheme(theme = MainTheme, darkTheme = true) {
        ListScreen(
            ListScreenState(Items(LocalStorage().get().values.toList())),
            screenAction = {},
            navigateToNewItem = {},
            navigateToEditItem = {}
        )
    }
}