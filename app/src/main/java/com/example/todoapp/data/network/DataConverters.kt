package com.example.todoapp.data.network

import com.example.todoapp.data.network.dtos.TodoItemDto
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem

fun TodoItem.toDto(deviceId: String): TodoItemDto {
    return TodoItemDto(
        id = this.id,
        text = this.taskText,
        importance = when (this.priority) {
            Priority.LOW -> "low"
            Priority.NORMAL -> "basic"
            Priority.HIGH -> "important"
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
            "low" -> Priority.LOW
            "basic" -> Priority.NORMAL
            "important" -> Priority.HIGH
            else -> Priority.NORMAL
        },
        isDone = this.done,
        deadlineDate = this.deadline,
        modificationDate = this.changedAt
    )
}
