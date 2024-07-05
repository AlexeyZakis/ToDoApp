package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class CheckHasInternetUseCase(private val todoItemsRepository: TodoItemsRepository) {
    operator fun invoke() = todoItemsRepository.hasInternet
}