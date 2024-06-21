package com.example.todoapp.domain.usecase

import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.domain.models.TodoItemParams
import com.example.todoapp.domain.repository.TodoItemsRepository

class EditTodoItemUseCase(private val todoItemsRepository: TodoItemsRepository) {
    fun execute(todoItemId: TodoItemId, newTodoItemParams: TodoItemParams) {
        todoItemsRepository.editTodoItem(todoItemId, newTodoItemParams)
    }
}