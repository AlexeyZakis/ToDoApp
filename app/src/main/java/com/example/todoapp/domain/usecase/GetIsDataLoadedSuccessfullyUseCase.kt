package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class GetIsDataLoadedSuccessfullyUseCase(private val todoItemsRepository: TodoItemsRepository) {
    operator fun invoke() = todoItemsRepository.isDataLoadedSuccessfully
}
