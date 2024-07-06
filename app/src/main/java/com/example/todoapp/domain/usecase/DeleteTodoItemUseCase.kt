package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.repository.TodoItemsRepository

class DeleteTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    suspend operator fun invoke(todoItemId: String) = todoItemsRepository.deleteTodoItem(todoItemId)
}
