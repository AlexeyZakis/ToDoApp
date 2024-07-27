package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.storage.RuntimeStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.DateFormat
import kotlinx.coroutines.runBlocking

@Composable
fun ListTodoItemList(
    modifier: Modifier,
    todoItems: Items,
    lazyListState: LazyListState,
    onCompletionChange: (TodoItem) -> Unit,
    onItemClick: (TodoItem) -> Unit,
    onDeleteItem: (TodoItem) -> Unit,
    onAddNewItemClick: () -> Unit,
) {
    val addTaskActionDescription = stringResource(id = R.string.addTaskActionDescription)
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(themeColors.backSecondary)
    ) {
        items(todoItems.items, key = { it.id }) { todoItem ->
            ListTodoItem(
                todoItem = todoItem,
                onCheckboxClick = { onCompletionChange(todoItem) },
                onItemClick = { onItemClick(todoItem) },
                modifier = Modifier.testTag("todoItem")
            )
        }
        item {
            Text(
                text = stringResource(R.string.newTaskBtn),
                color = themeColors.labelTertiary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClickLabel = addTaskActionDescription
                    ) { onAddNewItemClick() }
                    .padding(start = 42.dp, top = 8.dp, bottom = 8.dp)
                    .clearAndSetSemantics {
                        contentDescription = addTaskActionDescription
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTodoItemListPreview() {
    val data: Items
    runBlocking {
        data = Items(RuntimeStorage().getList().data?.values?.toList()?.subList(0, 3) ?: listOf())
    }
    AppTheme(theme = MainTheme) {
        ListTodoItemList(
            lazyListState = rememberLazyListState(),
            todoItems = data,
            onCompletionChange = {},
            onItemClick = {},
            onAddNewItemClick = {},
            onDeleteItem = {},
            modifier = Modifier,
        )
    }
}
