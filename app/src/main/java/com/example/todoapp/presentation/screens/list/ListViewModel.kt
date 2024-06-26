package com.example.todoapp.presentation.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.models.ItemList
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.usecase.ChangeDoneTaskVisibilityUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.presentation.screens.list.action.ListUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val getNumberOfDoneTaskUseCase: GetNumberOfDoneTaskUseCase,
    private val changeDoneTaskVisibilityUseCase: ChangeDoneTaskVisibilityUseCase,
): ViewModel() {
    private var _todoItems = MutableLiveData<ItemList>()
    val todoItems: LiveData<ItemList>
        get() = _todoItems

    var hideDoneTasks = MutableLiveData<Boolean>().apply {
        value = Constants.HIDE_DONE_TASK_DEFAULT
    }

    val doneTaskCounter: Int
        get() = getNumberOfDoneTaskUseCase.execute()

    fun onUiAction(action: ListUiAction) {
        when (action) {
            is ListUiAction.UpdateTodoItem -> editTodoItem(action.todoItem)
            is ListUiAction.RemoveTodoItem -> deleteTodoItem(action.todoItem.id)
            is ListUiAction.ChangeDoneTaskVisibility -> changeDoneTaskVisibility(action.hideDoneTask)
        }
    }
    fun changeDoneTaskVisibility(hideDoneTask: Boolean) {
        viewModelScope.launch {
            changeDoneTaskVisibilityUseCase.execute(hideDoneTask)
        }
    }
    fun deleteTodoItem(todoItemId: String) {
        viewModelScope.launch {
            deleteTodoItemUseCase.execute(todoItemId)
        }
    }
    fun editTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            editTodoItemUseCase.execute(todoItem)
        }
    }
}