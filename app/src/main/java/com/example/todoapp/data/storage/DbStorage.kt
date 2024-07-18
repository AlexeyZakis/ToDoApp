package com.example.todoapp.data.storage

import com.example.todoapp.data.db.todoItems.TodoItemDao
import com.example.todoapp.data.db.todoItems.toTodoItem
import com.example.todoapp.data.db.todoItems.toTodoItemsDb
import com.example.todoapp.data.storage.models.StorageResult
import com.example.todoapp.data.storage.models.StorageResultStatus
import com.example.todoapp.domain.models.Items
import com.example.todoapp.domain.models.TodoItem
import javax.inject.Inject

class DbStorage @Inject constructor(
    private val todoItemDao: TodoItemDao
) : TaskStorage {
    override suspend fun getList(): StorageResult<Map<String, TodoItem>> {
        val items = todoItemDao.getAll()
            .map { it.toTodoItem() }.associateBy { it.id }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = items
        )
    }

    override suspend fun updateList(items: Items): StorageResult<Nothing> {
        todoItemDao.deleteAll()
        items.items.forEach { item ->
            todoItemDao.insertItem(item.toTodoItemsDb())
        }
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun getItem(id: String): StorageResult<TodoItem> {
        val item = todoItemDao.getItemById(id)
        return item?.let {
            StorageResult(
                status = StorageResultStatus.SUCCESS,
                data = it.toTodoItem()
            )
        } ?: StorageResult(
            status = StorageResultStatus.ERROR,
            data = null
        )
    }

    override suspend fun addItem(todoItem: TodoItem): StorageResult<Nothing> {
        todoItemDao.insertItem(todoItem.toTodoItemsDb())
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun updateItem(todoItem: TodoItem): StorageResult<Nothing> {
        todoItemDao.updateItem(todoItem.toTodoItemsDb())
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }

    override suspend fun deleteItem(todoItem: TodoItem): StorageResult<Nothing> {
        todoItemDao.deleteItem(todoItem.toTodoItemsDb())
        return StorageResult(
            status = StorageResultStatus.SUCCESS,
            data = null
        )
    }
}
