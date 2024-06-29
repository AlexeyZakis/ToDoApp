package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class GetIsDoneTaskHiddenUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute() = todoItemsRepository.hideDoneTask
}