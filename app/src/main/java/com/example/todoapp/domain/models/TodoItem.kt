package com.example.todoapp.domain.models

import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val taskText: String = "",
    val startDate: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.NORMAL,
    val isDone: Boolean = false,
    val deadlineDate: Long? = null,
    val modificationDate: Long = System.currentTimeMillis(),
)