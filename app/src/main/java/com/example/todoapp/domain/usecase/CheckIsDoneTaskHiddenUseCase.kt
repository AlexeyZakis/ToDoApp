package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class CheckIsDoneTaskHiddenUseCase(private val todoItemsRepository: TodoItemsRepository) {
    operator fun invoke() = todoItemsRepository.hideDoneTask
}
