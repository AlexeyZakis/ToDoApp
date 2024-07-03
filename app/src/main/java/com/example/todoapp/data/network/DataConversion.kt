package com.example.todoapp.data.network

import com.example.todoapp.data.network.DTOs.TodoItemDto
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
        deadline = this.deadlineDate?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond(),
        done = this.isDone,
        color = null,
        created_at = this.startDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
        changed_at = this.modificationDate?.atStartOfDay(ZoneId.systemDefault())?.toEpochSecond() ?: this.startDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
        last_updated_by = deviceId
    )
}

fun TodoItemDto.toTodoItem(): TodoItem {
    return TodoItem(
        id = this.id,
        taskText = this.text,
        startDate = LocalDate.ofEpochDay(this.created_at / (24 * 60 * 60)),
        priority = when (this.importance) {
            "low" -> Priority.LOW
            "basic" -> Priority.NORMAL
            "important" -> Priority.HIGH
            else -> Priority.NORMAL
        },
        isDone = this.done,
        deadlineDate = this.deadline?.let { LocalDate.ofEpochDay(it / (24 * 60 * 60)) },
        modificationDate = LocalDate.ofEpochDay(this.changed_at / (24 * 60 * 60))
    )
}
