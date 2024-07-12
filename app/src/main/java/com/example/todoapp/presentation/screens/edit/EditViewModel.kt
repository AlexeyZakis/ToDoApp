package com.example.todoapp.presentation.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.usecase.AddTodoItemUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.DestroyRepositoryUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetTodoItemUseCase
import com.example.todoapp.presentation.constants.Mode
import com.example.todoapp.presentation.screens.navigation.routes.EditRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val destroyRepositoryUseCase: DestroyRepositoryUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var todoItem = TodoItem()

    private val _screenState = MutableStateFlow(EditScreenState())
    val screenState = _screenState.asStateFlow()

    private var screenMode = Mode.ADD_ITEM

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(EditRoute.ID)
            if (id == null || id == "{${EditRoute.ID}}") {
                return@launch
            }
            getTodoItemUseCase(id).data?.let { item ->
                screenMode = Mode.EDIT_ITEM
                todoItem = item
                _screenState.update {
                    screenState.value.copy(
                        text = item.taskText,
                        priority = item.priority,
                        hasDeadline = item.deadlineDate != null,
                        deadline = item.deadlineDate ?: screenState.value.deadline,
                        mode = Mode.EDIT_ITEM
                    )
                }
            }
        }
    }

    fun screenAction(action: EditScreenAction) {
        when (action) {
            EditScreenAction.OnTaskSave -> saveItem()
            EditScreenAction.OnTaskDelete -> deleteItem()
            is EditScreenAction.OnTextChange -> changeText(action)
            is EditScreenAction.OnDeadlineExistenceChange -> deadlineExistenceChange(action)
            is EditScreenAction.OnPrioritySelect -> prioritySelect(action)
            is EditScreenAction.OnDeadlineSelect -> deadlineSelect(action)
            EditScreenAction.OnErrorSnackBarClick -> errorSnackBarClick()
            else -> {}
        }
    }

    private fun saveItem() {
        todoItem = todoItem.copy(
            taskText = screenState.value.text,
            priority = screenState.value.priority,
            deadlineDate = if (screenState.value.hasDeadline) {
                screenState.value.deadline
            } else {
                null
            },
            modificationDate = System.currentTimeMillis(),
        )

        viewModelScope.launch {
            val result = when (screenMode) {
                Mode.ADD_ITEM -> {
                    addTodoItemUseCase(todoItem)
                }

                Mode.EDIT_ITEM -> {
                    editTodoItemUseCase(todoItem)
                }
            }
            updateSnackBarData(
                result = result,
                action = EditScreenAction.OnTaskSave,
                isLeaving = true
            )
        }
    }

    private fun deleteItem() {
        if (screenMode == Mode.EDIT_ITEM) {
            viewModelScope.launch {
                val result = deleteTodoItemUseCase(todoItem)
                updateSnackBarData(
                    result = result,
                    action = EditScreenAction.OnTaskDelete,
                    isLeaving = true
                )
            }
        }
    }

    private fun changeText(action: EditScreenAction.OnTextChange) {
        _screenState.update {
            screenState.value.copy(
                text = action.text
            )
        }
    }

    private fun deadlineExistenceChange(action: EditScreenAction.OnDeadlineExistenceChange) {
        _screenState.update {
            screenState.value.copy(
                hasDeadline = action.hasDeadline
            )
        }
    }

    private fun prioritySelect(action: EditScreenAction.OnPrioritySelect) {
        _screenState.update {
            screenState.value.copy(
                priority = action.priority
            )
        }
    }

    private fun deadlineSelect(action: EditScreenAction.OnDeadlineSelect) {
        _screenState.update {
            screenState.value.copy(
                deadline = action.deadline
            )
        }
    }

    private fun errorSnackBarClick() {
        _screenState.update {
            screenState.value.copy(
                isSuccessfulAction = !screenState.value.isSuccessfulAction
            )
        }
    }

    private fun <T> updateSnackBarData(
        result: StorageResult<T>,
        action: EditScreenAction,
        isLeaving: Boolean = false
    ) {
        if (result.status != StorageResultStatus.SUCCESS) {
            _screenState.update {
                screenState.value.copy(
                    isSuccessfulAction = true,
                    snackBarOnErrorAction = action,
                    isLeaving = false,
                )
            }
        } else {
            _screenState.update {
                screenState.value.copy(
                    isSuccessfulAction = false,
                    snackBarOnErrorAction = EditScreenAction.Nothing,
                    isLeaving = isLeaving
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        destroyRepositoryUseCase()
    }
}
