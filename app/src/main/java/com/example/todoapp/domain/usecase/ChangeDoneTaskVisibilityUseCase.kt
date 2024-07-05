package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class ChangeDoneTaskVisibilityUseCase(private val todoItemsRepository: TodoItemsRepository) {
    operator fun invoke(hideDoneTask: Boolean) = todoItemsRepository.changeDoneTaskVisibility(hideDoneTask)
}