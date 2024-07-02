package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.usecase.ChangeDoneTaskVisibilityUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetIsDoneTaskHiddenUseCase
import com.example.todoapp.domain.usecase.GetItemListUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.presentation.screens.list.action.ListScreenAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val getNumberOfDoneTaskUseCase: GetNumberOfDoneTaskUseCase,
    private val changeDoneTaskVisibilityUseCase: ChangeDoneTaskVisibilityUseCase,
    getItemListUseCase: GetItemListUseCase,
    getIsDoneTaskHiddenUseCase: GetIsDoneTaskHiddenUseCase
): ViewModel() {
    private val _screenState = MutableStateFlow(ListScreenState())
    val screenState = combine(
        _screenState,
        getItemListUseCase.execute(),
        getIsDoneTaskHiddenUseCase.execute()
    ) { state, items, hideDoneTask ->
        state.copy(
            todoItems = Items((
                if (hideDoneTask) {
                    items.values.filter { !it.isDone }
                }
                else {
                    items.values
                }).toList()
            ),
            doneTaskCounter = getNumberOfDoneTaskUseCase.execute(),
            hideDoneTask = hideDoneTask
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ListScreenState()
    )

    fun screenAction(action: ListScreenAction) {
        when (action) {
            is ListScreenAction.ChangeTodoItemCompletion -> changeTodoItemCompletion(action.todoItem)
            is ListScreenAction.ChangeDoneTaskVisibility -> changeDoneTaskVisibility(action.hideDoneTask)
            is ListScreenAction.DeleteTodoItem -> deleteTodoItem(action.todoItem.id)
        }
    }
    private fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        viewModelScope.launch {
            changeDoneTaskVisibilityUseCase.execute(hideDoneTask)
        }
    }
    private fun deleteTodoItem(todoItemId: String) {
        viewModelScope.launch {
            deleteTodoItemUseCase.execute(todoItemId)
        }
    }
    private fun changeTodoItemCompletion(todoItem: TodoItem) {
        viewModelScope.launch {
            editTodoItemUseCase.execute(todoItem)
        }
    }
}