package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.usecase.ChangeDoneTaskVisibilityUseCase
import com.example.todoapp.domain.usecase.CheckHasInternetUseCase
import com.example.todoapp.domain.usecase.CheckIsDoneTaskHiddenUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.DestroyRepositoryUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetIsDataLoadedSuccessfullyUseCase
import com.example.todoapp.domain.usecase.GetItemListUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.domain.usecase.SyncDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val getNumberOfDoneTaskUseCase: GetNumberOfDoneTaskUseCase,
    private val changeDoneTaskVisibilityUseCase: ChangeDoneTaskVisibilityUseCase,
    private val getItemListUseCase: GetItemListUseCase,
    private val checkIsDoneTaskHiddenUseCase: CheckIsDoneTaskHiddenUseCase,
    private val getIsDataLoadedSuccessfullyUseCase: GetIsDataLoadedSuccessfullyUseCase,
    private val destroyRepositoryUseCase: DestroyRepositoryUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val checkHasInternetUseCase: CheckHasInternetUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(ListScreenState())
    private val isConnectedStateFlow: StateFlow<Boolean> = checkHasInternetUseCase()

    val screenState = combine(
        _screenState,
        getItemListUseCase(),
        checkIsDoneTaskHiddenUseCase(),
        getIsDataLoadedSuccessfullyUseCase(),
    ) { state, items, hideDoneTask, isDataLoadedSuccessfully ->
        state.copy(
            todoItems = Items(
                (
                        if (hideDoneTask) {
                            items.values.filter { !it.isDone }
                        } else {
                            items.values
                        }
                        ).toList()
            ),
            doneTaskCounter = getNumberOfDoneTaskUseCase(),
            hideDoneTask = hideDoneTask,
            isDataLoadedSuccessfully = isDataLoadedSuccessfully,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ListScreenState()
    )

    init {
        viewModelScope.launch {
            isConnectedStateFlow.collect { isConnected ->
                if (isConnected) {
                    syncDataUseCase()
                }
            }
        }
    }

    fun screenAction(action: ListScreenAction, callback: () -> Unit = {}) {
        when (action) {
            is ListScreenAction.OnTodoItemCompletionChange -> changeTodoItemCompletion(action.todoItem)
            is ListScreenAction.OnDoneTaskVisibilityChange -> changeDoneTaskVisibility(action.hideDoneTask)
            is ListScreenAction.OnTodoItemDelete -> deleteTodoItem(action.todoItem)
            is ListScreenAction.OnRefreshData -> refreshData(callback)
            is ListScreenAction.OnErrorSnackBarClick -> errorSnackBarClick()
            else -> {}
        }
    }

    private fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        viewModelScope.launch {
            changeDoneTaskVisibilityUseCase(hideDoneTask)
        }
    }

    private fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            deleteTodoItemUseCase(todoItem)
        }
    }

    private fun changeTodoItemCompletion(todoItem: TodoItem) {
        viewModelScope.launch {
            val result = editTodoItemUseCase(todoItem.copy(
                modificationDate = System.currentTimeMillis(),
            ))
            updateSnackBarData(
                result = result,
                action = ListScreenAction.OnTodoItemCompletionChange(todoItem),
            )
        }
    }

    private fun refreshData(onComplete: () -> Unit) {
        viewModelScope.launch {
            syncDataUseCase()
            withContext(Dispatchers.Main) {
                onComplete()
            }
        }
    }

    private fun errorSnackBarClick() {
        _screenState.update {
            screenState.value.copy(
                isSuccessfulAction = !screenState.value.isSuccessfulAction
            )
        }
    }

    private fun <T> updateSnackBarData(result: StorageResult<T>, action: ListScreenAction) {
        if (result.status != StorageResultStatus.SUCCESS) {
            _screenState.update {
                screenState.value.copy(
                    isSuccessfulAction = true,
                    snackBarOnErrorAction = action,
                )
            }
        } else {
            _screenState.update {
                screenState.value.copy(
                    isSuccessfulAction = false,
                    snackBarOnErrorAction = ListScreenAction.Nothing,
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyRepositoryUseCase()
    }
}
