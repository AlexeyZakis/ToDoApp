package com.example.todoapp.presentation.screens.list

import com.example.todoapp.domain.models.ItemList

data class ListUiState(
    val todoItems: ItemList,
    val doneTaskCounter: Int = 0,
    val hideDoneTask: Boolean = false,
)