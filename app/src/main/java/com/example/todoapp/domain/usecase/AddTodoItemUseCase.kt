package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository

class AddTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute(todoItem: TodoItem) = todoItemsRepository.addTodoItem(todoItem)
}