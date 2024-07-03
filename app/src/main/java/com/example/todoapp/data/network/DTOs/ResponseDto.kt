package com.example.todoapp.data.network.DTOs

import kotlinx.serialization.SerialName

interface ResponseDto {
    @SerialName("status")
    val status: String
    @SerialName("revision")
    val revision: Int
}