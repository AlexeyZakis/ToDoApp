package com.example.todoapp.data.network.DTOs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ElementDto(
    @SerialName("status")
    override val status: String,
    @SerialName("revision")
    override val revision: Int,
    @SerialName("element")
    val element: TodoItemDto,
) : ResponseDto