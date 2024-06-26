package com.example.todoapp.data.storage

import com.example.todoapp.domain.models.TodoItem

interface TaskStorage {
    fun get(): Map<String, TodoItem>
}