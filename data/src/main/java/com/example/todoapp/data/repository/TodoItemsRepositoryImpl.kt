package com.example.todoapp.data.repository

import com.example.todoapp.data.storage.TaskStorage
import com.example.todoapp.domain.models.ItemList
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.domain.models.TodoItemParams
import com.example.todoapp.domain.repository.TodoItemsRepository

class TodoItemsRepositoryImpl(taskStorage: TaskStorage): TodoItemsRepository {
    private val taskMap = mutableMapOf<String, RecyclerItem>()
    private var availableTodoItemId = 0

    init {
        val taskList = taskStorage.get()
        taskList.tasks.forEach { task ->
            taskMap[task.id] = task
        }
        availableTodoItemId = taskMap.size + 1
    }

    override fun getItemList() = ItemList(taskMap.values.toList())

    override fun getTodoItem(todoItemId: TodoItemId): RecyclerItem.TodoItem {
        return taskMap[todoItemId.value] as RecyclerItem.TodoItem
    }

    override fun addTodoItem(todoItem: RecyclerItem.TodoItem) {
        taskMap[todoItem.id] = todoItem
        ++availableTodoItemId
    }

    override fun deleteTodoItem(todoItemId: TodoItemId) {
        taskMap.remove(todoItemId.value)
    }

    override fun editTodoItem(todoItemId: TodoItemId, newTodoItemParams: TodoItemParams) {
        (taskMap[todoItemId.value] as RecyclerItem.TodoItem).apply {
            newTodoItemParams.newTaskText?.let { taskText = it }
            newTodoItemParams.newPriority?.let { priority = it }
            deadlineDate = newTodoItemParams.newDeadlineDate
        }
    }

    override fun getAvailableTodoItemId(): TodoItemId {
        return TodoItemId(availableTodoItemId.toString())
    }

    override val doneTaskCounter: Int
        get() = taskMap.values.filterIsInstance<RecyclerItem.TodoItem>().count { it.isDone }
}