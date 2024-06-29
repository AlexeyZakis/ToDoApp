package com.example.todoapp.data.storage.localStorage

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.TaskList
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import java.time.LocalDate
import java.util.UUID

class LocalStorage: TaskStorage {
    override fun get(): Map<String, TodoItem> {
        val items = listOf(
            TodoItem(taskText = randText, priority = Priority.LOW, isDone = false, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.LOW, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randText, priority = Priority.LOW, isDone = true, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.LOW, isDone = true, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = false, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = true, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = true, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randText, priority = Priority.HIGH, isDone = false, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.HIGH, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randText, priority = Priority.HIGH, isDone = true, deadlineDate = null),
            TodoItem(taskText = randText, priority = Priority.HIGH, isDone = true, deadlineDate = LocalDate.now()),

            TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = false, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = true, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = true, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = false, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = true, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = true, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = false, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = false, deadlineDate = LocalDate.now()),
            TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = true, deadlineDate = null),
            TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = true, deadlineDate = LocalDate.now()),
        )
        return items.associateBy { it.id }
    }
    private val lorem: Lorem = LoremIpsum.getInstance()
    private val randText: String
        get() = lorem.getWords(1, 10)
    private val randBigText: String
        get() = lorem.getParagraphs(1, 10)
}