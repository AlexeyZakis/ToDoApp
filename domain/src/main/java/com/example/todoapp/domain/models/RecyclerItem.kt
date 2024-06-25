package com.example.todoapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

sealed interface RecyclerItem {
    val id: String
    fun deepCopy(): RecyclerItem

    @Parcelize
    data class TodoItem(
        override val id: String,
        var taskText: String = "",
        val startDate: LocalDate = LocalDate.now(),
        var priority: Priority = Priority.NORMAL,
        var isDone: Boolean = false,
        var deadlineDate: LocalDate? = null,
        var lastChangeDate: LocalDate? = null,
    ): RecyclerItem, Parcelable {
        override fun deepCopy() = copy()
    }
    data object AddTaskItem: RecyclerItem {
        override val id: String = "ADD_TASK_ID"
        override fun deepCopy() = this
    }
}