package com.example.todoapp.data.storage.localStorage

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.TaskList
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.RecyclerItem
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import java.time.LocalDate

class LocalStorage: TaskStorage {
    override fun get(): TaskList {
        val lorem: Lorem = LoremIpsum.getInstance()
        return TaskList(mutableListOf(
            RecyclerItem.TodoItem("s1", lorem.getWords(5, 10), priority = Priority.LOW, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("s2", lorem.getWords(5, 10), priority = Priority.LOW, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("s3", lorem.getWords(5, 10), priority = Priority.LOW, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("s4", lorem.getWords(5, 10), priority = Priority.LOW, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("s5", lorem.getWords(5, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("s6", lorem.getWords(5, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("s7", lorem.getWords(5, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("s8", lorem.getWords(5, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("s9", lorem.getWords(5, 10), priority = Priority.HIGH, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("s10", lorem.getWords(5, 10), priority = Priority.HIGH, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("s11", lorem.getWords(5, 10), priority = Priority.HIGH, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("s12", lorem.getWords(5, 10), priority = Priority.HIGH, isDone = true, deadlineDate = LocalDate.now()),

            RecyclerItem.TodoItem("m1", lorem.getParagraphs(1, 10), priority = Priority.LOW, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("m2", lorem.getParagraphs(1, 10), priority = Priority.LOW, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("m3", lorem.getParagraphs(1, 10), priority = Priority.LOW, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("m4", lorem.getParagraphs(1, 10), priority = Priority.LOW, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("m5", lorem.getParagraphs(1, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("m6", lorem.getParagraphs(1, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("m7", lorem.getParagraphs(1, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("m8", lorem.getParagraphs(1, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("m9", lorem.getParagraphs(1, 10), priority = Priority.HIGH, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("m10", lorem.getParagraphs(1, 10), priority = Priority.HIGH, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("m11", lorem.getParagraphs(1, 10), priority = Priority.HIGH, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("m12", lorem.getParagraphs(1, 10), priority = Priority.HIGH, isDone = true, deadlineDate = LocalDate.now()),

            RecyclerItem.TodoItem("l1", lorem.getParagraphs(10, 10), priority = Priority.LOW, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("l2", lorem.getParagraphs(10, 10), priority = Priority.LOW, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("l3", lorem.getParagraphs(10, 10), priority = Priority.LOW, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("l4", lorem.getParagraphs(10, 10), priority = Priority.LOW, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("l5", lorem.getParagraphs(10, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("l6", lorem.getParagraphs(10, 10), priority = Priority.NORMAL, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("l7", lorem.getParagraphs(10, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("l8", lorem.getParagraphs(10, 10), priority = Priority.NORMAL, isDone = true, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("l9", lorem.getParagraphs(10, 10), priority = Priority.HIGH, isDone = false, deadlineDate = null),
            RecyclerItem.TodoItem("l10", lorem.getParagraphs(10, 10), priority = Priority.HIGH, isDone = false, deadlineDate = LocalDate.now()),
            RecyclerItem.TodoItem("l11", lorem.getParagraphs(10, 10), priority = Priority.HIGH, isDone = true, deadlineDate = null),
            RecyclerItem.TodoItem("l12", lorem.getParagraphs(10, 10), priority = Priority.HIGH, isDone = true, deadlineDate = LocalDate.now()),
        ).also { it.shuffle() })
    }
}