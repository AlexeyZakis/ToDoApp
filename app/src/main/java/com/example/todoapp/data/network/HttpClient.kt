package com.example.todoapp.data.network

import com.example.todoapp.data.network.Constants.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.concurrent.TimeUnit

val client by lazy {
    HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
            threadsCount = 8
        }
        followRedirects = false

        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 10_000L
            requestTimeoutMillis = 10_000L
            socketTimeoutMillis = 10_000L
        }
    }
}

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor())
    .hostnameVerifier { _, _ -> true }
//    .certificatePinner(
//        CertificatePinner.Builder()
//            .add(NetworkConstants.HOST, NetworkConstants.HOST_SHA)
//            .build()
//    )
    .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
    .connectionPool(
        ConnectionPool(
            maxIdleConnections = 5,
            keepAliveDuration = 5,
            timeUnit = TimeUnit.MINUTES
        )
    )
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

val json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}
