package com.example.todoapp.data.db.todoItems

import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem

fun TodoItemDb.toTodoItem() = TodoItem(
    id = id,
    taskText = taskText,
    creationDate = creationDate,
    priority = PriorityDbConverter.toItem(priority),
    isDone = isDone,
    deadlineDate = deadlineDate,
    modificationDate = modificationDate,
)

fun TodoItem.toTodoItemsDb() = TodoItemDb(
    id = id,
    taskText = taskText,
    creationDate = creationDate,
    priority = PriorityDbConverter.toItemDb(priority),
    isDone = isDone,
    deadlineDate = deadlineDate,
    modificationDate = modificationDate,
)

private object PriorityDbConverter {
    fun toItemDb(priority: Priority) = when (priority) {
        Priority.HIGH -> 0
        Priority.NORMAL -> 1
        Priority.LOW -> 2
    }

    fun toItem(priority: Int): Priority = when (priority) {
        0 -> Priority.HIGH
        1 -> Priority.NORMAL
        2 -> Priority.LOW
        else -> Priority.NORMAL
    }
}
