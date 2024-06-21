package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.domain.repository.TodoItemsRepository

class GetTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute(todoItemId: TodoItemId) = todoItemsRepository.getTodoItem(todoItemId)
}