package com.example.todoapp.data.network

import com.example.todoapp.data.network.constants.NetworkConstants
import com.example.todoapp.data.network.dtos.TodoItemDto
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem

fun TodoItem.toDto(deviceId: String): TodoItemDto {
    return TodoItemDto(
        id = this.id,
        text = this.taskText,
        importance = when (this.priority) {
            Priority.LOW -> NetworkConstants.ServerPriorities.LOW
            Priority.NORMAL -> NetworkConstants.ServerPriorities.NORMAL
            Priority.HIGH -> NetworkConstants.ServerPriorities.HIGH
        },
        deadline = this.deadlineDate,
        done = this.isDone,
        color = null,
        createdAt = this.startDate,
        changedAt = this.modificationDate,
        lastUpdatedBy = deviceId
    )
}

fun TodoItemDto.toTodoItem(): TodoItem {
    return TodoItem(
        id = this.id,
        taskText = this.text,
        startDate = this.createdAt,
        priority = when (this.importance) {
            NetworkConstants.ServerPriorities.LOW -> Priority.LOW
            NetworkConstants.ServerPriorities.NORMAL -> Priority.NORMAL
            NetworkConstants.ServerPriorities.HIGH -> Priority.HIGH
            else -> Priority.NORMAL
        },
        isDone = this.done,
        deadlineDate = this.deadline,
        modificationDate = this.changedAt
    )
}
