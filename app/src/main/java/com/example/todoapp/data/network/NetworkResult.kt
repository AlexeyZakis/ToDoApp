package com.example.todoapp.data.network

sealed interface NetworkResult<T, E> {
    data class Success<T>(val data: T): NetworkResult<T, Nothing>
    data class Error<E>(val errorCode: Int, val message: String, val errorData: E? = null):
        NetworkResult<Nothing, E>
}