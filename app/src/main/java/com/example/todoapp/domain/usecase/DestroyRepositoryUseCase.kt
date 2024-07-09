package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class DestroyRepositoryUseCase(private val todoItemsRepository: TodoItemsRepository) {
    operator fun invoke() = todoItemsRepository.destroy()
}
