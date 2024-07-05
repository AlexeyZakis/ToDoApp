package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class RefreshDataUseCase(private val todoItemsRepository: TodoItemsRepository) {
    suspend operator fun invoke() = todoItemsRepository.refreshData()
}