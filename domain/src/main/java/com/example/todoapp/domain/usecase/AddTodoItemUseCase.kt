package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.repository.TodoItemsRepository

class AddTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute(todoItem: RecyclerItem.TodoItem) = todoItemsRepository.addTodoItem(todoItem)
}