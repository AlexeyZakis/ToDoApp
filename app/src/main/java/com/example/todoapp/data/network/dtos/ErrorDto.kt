package com.example.todoapp.data.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val error: String,
    val errorMessage: String
)
