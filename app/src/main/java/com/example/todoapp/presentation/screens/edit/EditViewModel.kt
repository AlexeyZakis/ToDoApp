package com.example.todoapp.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository
import com.example.todoapp.presentation.constants.Mode
import com.example.todoapp.presentation.screens.edit.action.EditUiAction
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
    private val repository: TodoItemsRepository,
): ViewModel() {
    private var todoItem = TodoItem()

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private var screenMode = Mode.EDIT_ITEM

    init {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.getTodoItem(id)?.let { item ->
                screenMode = Mode.EDIT_ITEM
                todoItem = item
                _uiState.update {
                    uiState.value.copy(
                        text = item.taskText,
                        priority = item.priority,
                        deadline = item.deadlineDate ?: uiState.value.deadline,
                        hasDeadline = item.deadlineDate != null,
                    )
                }
            }
        }
    }
    fun onUiAction(action: EditUiAction) {
        when (action) {
            EditUiAction.SaveTask -> onSaveButtonClick()
            EditUiAction.DeleteTask -> onRemoveButtonClick()
            is EditUiAction.UpdateText -> _uiState.update {
                uiState.value.copy(text = action.text)
            }
            is EditUiAction.UpdateDeadlineExistence -> _uiState.update {
                uiState.value.copy(hasDeadline = action.hasDeadline)
            }
            is EditUiAction.UpdatePriority -> _uiState.update {
                uiState.value.copy(priority = action.priority)
            }
            is EditUiAction.UpdateDeadline -> _uiState.update {
                uiState.value.copy(deadline = action.deadline)
            }
        }
    }
    private fun onSaveButtonClick() {
        if (uiState.value.text.isBlank()) return

        todoItem = todoItem.copy(
            taskText = uiState.value.text,
            priority = uiState.value.priority,
            deadlineDate = if (uiState.value.hasDeadline) {
                uiState.value.deadline
            } else {
                null
            },
            modificationDate = LocalDate.now(),
        )

        viewModelScope.launch(Dispatchers.IO) {
            when (screenMode) {
                Mode.ADD_ITEM -> {
                    repository.addTodoItem(todoItem)
                }
                Mode.EDIT_ITEM -> {
                    repository.editTodoItem(todoItem)
                }
            }
        }
    }
    private fun onRemoveButtonClick() {
        if (screenMode == Mode.EDIT_ITEM) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteTodoItem(todoItem.id)
            }
        }
    }
}