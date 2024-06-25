package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class GetAvailableTodoItemIdUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute() = todoItemsRepository.getAvailableTodoItemId()
}