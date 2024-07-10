package com.example.todoapp.data.storage.runtimeStorage

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum

/**
 * Temp storage for testing. Do not save data
 **/
class RuntimeStorage : TaskStorage {
    private val items = mutableMapOf<String, TodoItem>()
    private val lorem: Lorem = LoremIpsum.getInstance()

    init {
        items.putAll(
            listOf(
                TodoItem(taskText = randText, priority = Priority.LOW, isDone = false, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.LOW, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randText, priority = Priority.LOW, isDone = true, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.LOW, isDone = true, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = false, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = true, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.NORMAL, isDone = true, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randText, priority = Priority.HIGH, isDone = false, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.HIGH, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randText, priority = Priority.HIGH, isDone = true, deadlineDate = null),
                TodoItem(taskText = randText, priority = Priority.HIGH, isDone = true, deadlineDate = System.currentTimeMillis()),

                TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = false, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = true, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.LOW, isDone = true, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = false, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = true, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.NORMAL, isDone = true, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = false, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = false, deadlineDate = System.currentTimeMillis()),
                TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = true, deadlineDate = null),
                TodoItem(taskText = randBigText, priority = Priority.HIGH, isDone = true, deadlineDate = System.currentTimeMillis()),
            ).associateBy { it.id }
        )
    }
    override suspend fun getList(): StorageResult<Map<String, TodoItem>> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = items
        )
    }

    override suspend fun updateList(items: Items): StorageResult<Nothing> {
        this.items.clear()
        this.items.putAll(items.items.associateBy { it.id })
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun getItem(id: String): StorageResult<TodoItem> {
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = items[id]
        )
    }

    override suspend fun addItem(todoItem: TodoItem): StorageResult<Nothing> {
        items[todoItem.id] = todoItem
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun updateItem(todoItem: TodoItem): StorageResult<Nothing> {
        items[todoItem.id] = todoItem
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun deleteItem(id: String): StorageResult<Nothing> {
        items.remove(id)
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    private val randText: String
        get() = lorem.getWords(1, 10)
    private val randBigText: String
        get() = lorem.getParagraphs(1, 10)
}
