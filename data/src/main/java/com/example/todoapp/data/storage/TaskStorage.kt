package com.example.todoapp.data.storage

import com.example.todoapp.data.storage.models.TaskList

interface TaskStorage {
    fun get(): TaskList
}