package com.example.todoapp.data.network

import android.util.Log
import com.example.todoapp.data.network.Constants.NetworkConstants
import com.example.todoapp.data.network.Constants.NetworkErrorCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import kotlinx.serialization.SerializationException
import okio.IOException
import java.net.UnknownHostException

suspend inline fun <reified T, reified E>HttpClient.safeRequest(
    host: String = NetworkConstants.HOST,
    path: String = "",
    headers: Map<String, String> = emptyMap(),
    params: StringValues = StringValues.Empty,
    method: HttpMethod = HttpMethod.Get,
    body: Any? = null,
) = try {
    val response = request {
        this.method = method
        this.host = host
        url {
            protocol = URLProtocol.HTTPS
            path(path)
            parameters.appendAll(params)
        }
        contentType(ContentType.Application.Json)
        if (body != null) {
            setBody(body)
        }
        headers.forEach { (key, value) ->
            this.headers.append(key, value)
        }
    }
    Log.i(NetworkConstants.DEBUG, "Request\n$response\n${response.bodyAsText()}")
    if (response.status.isSuccess()) {
        NetworkResult.Success(data = response.body<T>())
    }
    else {
        NetworkResult.Error(
            errorCode = response.status.value,
            message = response.status.description,
            errorData = response.body<E>()
        )
    }
}
catch (e: SerializationException) {
    e.printStackTrace()
    NetworkResult.Error(
        errorCode = NetworkErrorCode.Serialization.value,
        message = e.message ?: "Serialization",
        errorData = null
    )
}
catch (e: ServerResponseException) {
    e.printStackTrace()
    NetworkResult.Error(
        errorCode = NetworkErrorCode.ServerResponse.value,
        message = e.message,
        errorData = null
    )
}
catch (e: UnknownHostException) {
    e.printStackTrace()
    NetworkResult.Error(
        errorCode = NetworkErrorCode.Internet.value,
        message = e.message ?: "No internet",
        errorData = null
    )
}
catch (e: IOException) {
    e.printStackTrace()
    NetworkResult.Error(
        errorCode = NetworkErrorCode.IO.value,
        message = e.message ?: "IO",
        errorData = null
    )
}
catch (e: Exception) {
    e.printStackTrace()
    NetworkResult.Error(
        errorCode = NetworkErrorCode.Unknown.value,
        message = e.message ?: "Unknown error",
        errorData = null
    )
}
