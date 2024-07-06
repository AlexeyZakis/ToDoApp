package com.example.todoapp.data.network.dtos

import kotlinx.serialization.SerialName

interface ResponseDto {
    @SerialName("status")
    val status: String

    @SerialName("revision")
    val revision: Int
}
