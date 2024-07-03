package com.example.todoapp.data.network.Constants

enum class NetworkErrorCode(val value: Int) {
    Serialization(-1),
    ServerResponse(-2),
    Internet(-3),
    IO(-4),
    Unknown(-5),
}