package com.example.todoapp.presentation.screens.list

import com.example.todoapp.domain.models.Items

/**
 * Task list screen state
 **/
data class ListScreenState(
    val todoItems: Items = Items(),
    val doneTaskCounter: Int = 0,
    val hideDoneTask: Boolean = false,
    val isDataLoadedSuccessfully: Boolean = false,
    val isSuccessfulAction: Boolean = false,
    val snackBarOnErrorAction: ListScreenAction = ListScreenAction.Nothing,
    val hasInternet: Boolean = false,
)
