package com.example.todoapp.data.network

import com.example.todoapp.data.network.constants.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.UUID
import java.util.concurrent.TimeUnit

val client by lazy {
    HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
            threadsCount = NetworkConstants.HttpClientSettings.THREADS_COUNT
        }
        followRedirects = false

        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = NetworkConstants.HttpClientSettings.CONNECT_TIMEOUT_MILLIS
            requestTimeoutMillis = NetworkConstants.HttpClientSettings.REQUEST_TIMEOUT_MILLIS
            socketTimeoutMillis = NetworkConstants.HttpClientSettings.SOCKET_TIMEOUT_MILLIS
        }
    }
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor())
    .hostnameVerifier { _, _ -> true }
    .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
    .connectionPool(
        ConnectionPool(
            maxIdleConnections = NetworkConstants.HttpClientSettings.MAX_IDLE_CONNECTIONS,
            keepAliveDuration = NetworkConstants.HttpClientSettings.KEEP_ALIVE_DURATION_MINUTES,
            timeUnit = TimeUnit.MINUTES
        )
    )
    .connectTimeout(NetworkConstants.HttpClientSettings.CONNECT_TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(NetworkConstants.HttpClientSettings.READ_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(NetworkConstants.HttpClientSettings.WRITE_TIMEOUT, TimeUnit.SECONDS)
    .build()

val json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}

val deviceId = UUID.randomUUID().toString()
