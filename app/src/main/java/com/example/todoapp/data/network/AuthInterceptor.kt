package com.example.todoapp.data.network

import com.example.todoapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            .build()
        return chain.proceed(newRequest)
    }
}