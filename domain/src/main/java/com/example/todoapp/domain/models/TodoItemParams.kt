package com.example.todoapp.domain.models

import java.time.LocalDate

class TodoItemParams(
    val newTaskText: String? = null,
    val newPriority: Priority? = null,
    val newDeadlineDate: LocalDate? = null,
)