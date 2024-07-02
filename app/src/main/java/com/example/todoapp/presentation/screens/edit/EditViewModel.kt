package com.example.todoapp.presentation.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.domain.usecase.AddTodoItemUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetTodoItemUseCase
import com.example.todoapp.presentation.constants.Mode
import com.example.todoapp.presentation.screens.edit.action.EditScreenAction
import com.example.todoapp.presentation.screens.navigation.routes.EditRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private var todoItem = TodoItem()

    private val _screenState = MutableStateFlow(EditScreenState())
    val screenState = _screenState.asStateFlow()

    private var screenMode = Mode.ADD_ITEM

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(EditRoute.ID) ?: ""
            getTodoItemUseCase.execute(id)?.let { item ->
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
            EditScreenAction.SaveTask -> onSaveButtonClick()
            EditScreenAction.DeleteTask -> onRemoveButtonClick()
            is EditScreenAction.UpdateText -> _screenState.update {
                screenState.value.copy(
                    text = action.text
                )
            }
            is EditScreenAction.UpdateDeadlineExistence -> _screenState.update {
                screenState.value.copy(
                    hasDeadline = action.hasDeadline
                )
            }
            is EditScreenAction.UpdatePriority -> _screenState.update {
                screenState.value.copy(
                    priority = action.priority
                )
            }
            is EditScreenAction.UpdateDeadline -> _screenState.update {
                screenState.value.copy(
                    deadline = action.deadline
                )
            }
        }
    }
    private fun onSaveButtonClick() {
        if (screenState.value.text.isBlank()) {
            return
        }

        todoItem = todoItem.copy(
            taskText = screenState.value.text,
            priority = screenState.value.priority,
            deadlineDate = if (screenState.value.hasDeadline) {
                screenState.value.deadline
            } else {
                null
            },
            modificationDate = LocalDate.now(),
        )

        viewModelScope.launch(Dispatchers.IO) {
            when (screenMode) {
                Mode.ADD_ITEM -> {
                    addTodoItemUseCase.execute(todoItem)
                }
                Mode.EDIT_ITEM -> {
                    editTodoItemUseCase.execute(todoItem)
                }
            }
        }
    }
    private fun onRemoveButtonClick() {
        if (screenMode == Mode.EDIT_ITEM) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteTodoItemUseCase.execute(todoItem.id)
            }
        }
    }
}