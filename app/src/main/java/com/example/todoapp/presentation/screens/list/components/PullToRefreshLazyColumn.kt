package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.storage.disposableStorage.DisposableStorage
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLazyColumn(
    modifier: Modifier = Modifier,
    todoItems: Items,
    onCompletionChange: (TodoItem) -> Unit,
    onItemClick: (TodoItem) -> Unit,
    onDeleteItem: (TodoItem) -> Unit,
    onAddNewItemClick: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        ListTodoItemList(
            lazyListState = lazyListState,
            todoItems = todoItems,
            onItemClick = onItemClick,
            onCompletionChange = onCompletionChange,
            onAddNewItemClick = onAddNewItemClick,
            onDeleteItem = onDeleteItem,
            modifier = Modifier
                .padding(16.dp)
        )

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PullToRefreshLazyColumnPreview() {
    val data: Items
    runBlocking {
        data = Items(DisposableStorage().getList().data?.values?.toList() ?: listOf())
    }
    PullToRefreshLazyColumn(
        modifier = Modifier,
        isRefreshing = false,
        onRefresh = {},
        onDeleteItem = {},
        onItemClick = {},
        onAddNewItemClick = {},
        onCompletionChange = {},
        todoItems = data,
    )
}
