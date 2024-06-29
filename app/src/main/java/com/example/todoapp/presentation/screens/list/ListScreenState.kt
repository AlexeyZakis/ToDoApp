package com.example.todoapp.presentation.screens.list

import com.example.todoapp.domain.models.Items

data class ListScreenState(
    val todoItems: Items = Items(),
    val doneTaskCounter: Int = 0,
    val hideDoneTask: Boolean = false,
)