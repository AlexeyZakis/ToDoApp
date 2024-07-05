package com.example.todoapp.data.network

import com.example.todoapp.data.network.DTOs.ListDto
import com.example.todoapp.data.network.DTOs.TodoItemDto
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem
import java.time.LocalDate
import java.time.ZoneId

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
        created_at = this.startDate,
        changed_at = this.modificationDate,
        last_updated_by = deviceId
    )
}

fun TodoItemDto.toTodoItem(): TodoItem {
    return TodoItem(
        id = this.id,
        taskText = this.text,
        startDate = this.created_at,
        priority = when (this.importance) {
            "low" -> Priority.LOW
            "basic" -> Priority.NORMAL
            "important" -> Priority.HIGH
            else -> Priority.NORMAL
        },
        isDone = this.done,
        deadlineDate = this.deadline,
        modificationDate = this.changed_at
    )
}
