package com.example.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MockHttpClientModule {
    @Singleton
    @Provides
    fun provideMockEngine(): MockEngine {
        return MockEngine { request ->
            when (request.url.encodedPath) {
                "/deleteTodo" -> {
                    respond(
                        content = """{"status":"success"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                "/saveTodo" -> {
                    respond(
                        content = """{"status":"success"}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> error("Error ${request.url.encodedPath}")
            }
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(mockEngine: MockEngine): HttpClient {
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}