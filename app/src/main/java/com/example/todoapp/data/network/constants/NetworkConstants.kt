package com.example.todoapp.data.network.constants

object NetworkConstants {
    const val HOST = "beta.mrdekk.ru/todo"
    const val PATH = "list"

    const val DEBUG = "NetworkDebug"

    const val SUCCESS_STATUS = "ok"

    const val TOKEN_TYPE = "Bearer"

    object Headers {
        const val REVISION = "X-Last-Known-Revision"
        const val FAILS = "X-Generate-Fails"
        const val AUTHORIZATION = "Authorization"
    }
    object Wrappers {
        const val ELEMENT = "element"
        const val LIST = "list"
    }
    object ServerPriorities {
        const val LOW = "low"
        const val NORMAL = "basic"
        const val HIGH = "important"
    }

    object HttpClientSettings {
        const val THREADS_COUNT = 8

        const val CONNECT_TIMEOUT_MILLIS = 10_000L
        const val REQUEST_TIMEOUT_MILLIS = 10_000L
        const val SOCKET_TIMEOUT_MILLIS = 10_000L

        const val CONNECT_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L

        const val MAX_IDLE_CONNECTIONS = 5
        const val KEEP_ALIVE_DURATION_MINUTES = 5L
    }
}
