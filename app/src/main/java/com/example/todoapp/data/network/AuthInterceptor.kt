package com.example.todoapp.data.network

import com.example.todoapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * For adding authorization token to request
 **/
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(
                NetworkConstants.Headers.AUTHORIZATION,
                "${NetworkConstants.TOKEN_TYPE} ${BuildConfig.API_TOKEN}"
            )
            .build()
        return chain.proceed(newRequest)
    }
}
