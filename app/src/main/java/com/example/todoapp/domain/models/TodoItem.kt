package com.example.todoapp.domain.models

import java.time.LocalDate
import java.util.UUID

data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val taskText: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val priority: Priority = Priority.NORMAL,
    val isDone: Boolean = false,
    val deadlineDate: LocalDate? = null,
    val modificationDate: LocalDate? = null,
)