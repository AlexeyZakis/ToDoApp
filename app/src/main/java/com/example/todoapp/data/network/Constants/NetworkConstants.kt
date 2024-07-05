package com.example.todoapp.data.network.Constants

object NetworkConstants {
    const val HOST = "beta.mrdekk.ru/todo"
    const val PATH = "list"
    const val HOST_SHA = "sha256/d60ed1216dbfbfa4aea4cf734da50035ca290772e284afe98a03472f4c482c07"//32:00


    const val DEBUG = "NetworkDebug"

    const val SUCCESS_STATUS = "ok"

    object Headers {
        const val REVISION = "X-Last-Known-Revision"
        const val FAILS = "X-Generate-Fails"
    }
    object Wrappers {
        const val ELEMENT = "element"
        const val LIST = "list"
    }
}