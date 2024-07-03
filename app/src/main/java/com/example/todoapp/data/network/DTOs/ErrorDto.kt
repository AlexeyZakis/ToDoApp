package com.example.todoapp.data.network.DTOs

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val error: String,
    val errorMessage: String
)