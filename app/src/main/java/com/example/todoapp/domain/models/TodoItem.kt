package com.example.todoapp.domain.models

import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val taskText: String = "",
    val creationDate: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.NORMAL,
    val isDone: Boolean = false,
    val deadlineDate: Long? = null,
    val modificationDate: Long = System.currentTimeMillis(),
) {
    companion object {
        val idComparator: (TodoItem, TodoItem) -> Boolean = { item1, item2 ->
            item1.id == item2.id
        }
    }
}