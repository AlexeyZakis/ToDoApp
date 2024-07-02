package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.storage.localStorage.LocalStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors

@Composable
fun ListTodoItemList(
    modifier: Modifier,
    todoItems: Items,
    onCompletionChange: (TodoItem) -> Unit,
    onItemClick: (TodoItem) -> Unit,
    // For swipe
    // onDeleteItem: (TodoItem) -> Unit,
    onAddNewItemClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(themeColors.backSecondary)
    ) {
        items(todoItems.items, key = { it.id }) { todoItem ->
            // TODO : Fix item fade bad
//            SwipeLazyColumn(
//                item = todoItem,
//                onSwipeEndToStart = { onDeleteItem(todoItem) },
//                onSwipeStartToEnd = { onCompletionChange(todoItem) }
//            ) {
                ListTodoItem(
                    todoItem = todoItem,
                    onCheckboxClick = { onCompletionChange(todoItem) },
                    onItemClick = { onItemClick(todoItem) }
                )
//            }
        }
        item {
            Text(
                text = stringResource(R.string.newTaskBtn),
                color = themeColors.labelTertiary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAddNewItemClick() }
                    .padding(start = 42.dp, top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTodoItemListPreview() {
    AppTheme(theme = MainTheme) {
        ListTodoItemList(
            todoItems = Items(LocalStorage().get().values.toList()),
            onCompletionChange = {},
            onItemClick = {},
            onAddNewItemClick = {},
            //onDeleteItem = {},
            modifier = Modifier,
        )
    }
}
