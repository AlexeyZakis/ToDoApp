package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.domain.repository.TodoItemsRepository

class DeleteTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    suspend operator fun invoke(todoItem: TodoItem) = todoItemsRepository.deleteTodoItem(todoItem)
}
