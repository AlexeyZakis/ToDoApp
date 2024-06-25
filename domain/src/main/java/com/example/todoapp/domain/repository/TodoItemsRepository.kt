package com.example.todoapp.domain.repository

import com.example.todoapp.domain.models.ItemList
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.domain.models.TodoItemParams

interface TodoItemsRepository {
    fun getItemList(): ItemList
    fun getTodoItem(todoItemId: TodoItemId): RecyclerItem.TodoItem
    fun addTodoItem(todoItem: RecyclerItem.TodoItem)
    fun deleteTodoItem(todoItemId: TodoItemId)
    fun editTodoItem(todoItemId: TodoItemId, newTodoItemParams: TodoItemParams)
    fun getAvailableTodoItemId(): TodoItemId
    val doneTaskCounter: Int
}