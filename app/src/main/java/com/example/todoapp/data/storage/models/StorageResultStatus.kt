package com.example.todoapp.data.storage.models

enum class StorageResultStatus {
    SUCCESS,
    ERROR,
    ;
    fun isSuccess() = this == SUCCESS
}