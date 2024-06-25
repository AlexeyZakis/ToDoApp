package com.example.todoapp.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.domain.models.ItemList
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.domain.models.TodoItemParams
import com.example.todoapp.domain.usecase.AddTodoItemUseCase
import com.example.todoapp.domain.usecase.DeleteTodoItemUseCase
import com.example.todoapp.domain.usecase.EditTodoItemUseCase
import com.example.todoapp.domain.usecase.GetAvailableTodoItemIdUseCase
import com.example.todoapp.domain.usecase.GetItemListUseCase
import com.example.todoapp.domain.usecase.GetNumberOfDoneTaskUseCase
import com.example.todoapp.domain.usecase.GetTodoItemUseCase
import com.example.todoapp.presentation.Constants.Constants
import com.example.todoapp.presentation.adapters.DiffUtilImpl
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoItemListViewModel @Inject constructor(
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val editTodoItemUseCase: EditTodoItemUseCase,
    private val getItemListUseCase: GetItemListUseCase,
    private val getNumberOfDoneTaskUseCase: GetNumberOfDoneTaskUseCase,
    private val getAvailableTodoItemIdUseCase: GetAvailableTodoItemIdUseCase,
    private val getTodoItemUseCase: GetTodoItemUseCase,
): ViewModel() {
    private var _todoItems = MutableLiveData<ItemList>()
    val todoItems: LiveData<ItemList>
        get() = _todoItems

    var hideDoneTasks = MutableLiveData<Boolean>().apply {
        value = Constants.HIDE_DONE_TASK_DEFAULT
    }

    private var lastShownList = listOf<RecyclerItem>()

    val doneTaskCounter: Int
        get() = getNumberOfDoneTaskUseCase.execute()

    fun getTodoItems() {
        _todoItems.value = getItemListUseCase.execute()
    }
    fun getTodoItem(todoItemId: TodoItemId) = getTodoItemUseCase.execute(todoItemId)
    fun addTodoItem(todoItem: RecyclerItem.TodoItem) {
        addTodoItemUseCase.execute(todoItem)
    }
    fun deleteTodoItem(todoItemId: TodoItemId) {
        deleteTodoItemUseCase.execute(todoItemId)
    }
    fun editTodoItem(todoItemId: TodoItemId, newTodoItemParams: TodoItemParams) {
        editTodoItemUseCase.execute(todoItemId, newTodoItemParams)
    }
    fun getAvailableTodoItemId() = getAvailableTodoItemIdUseCase.execute()
    fun setDiffUtilData(hideDoneTasks: Boolean, adapter: ListDelegationAdapter<List<Any>>) {
        var newList = getItemListUseCase.execute().items.toMutableList()

        if (hideDoneTasks) {
            newList = newList.filter { item ->
                !(item is RecyclerItem.TodoItem && item.isDone)
            }.toMutableList()
        }
        newList += RecyclerItem.AddTaskItem

        val diffUtil = DiffUtilImpl(lastShownList, newList)
        lastShownList = newList.map { it.deepCopy() }
        adapter.items = newList
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(adapter)
    }
}