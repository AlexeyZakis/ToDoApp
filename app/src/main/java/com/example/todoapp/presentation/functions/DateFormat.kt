package com.example.todoapp.presentation.functions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormat {
    fun getDateString(currentTimeMillis: Long?, locale: Locale = Locale.getDefault()): String {
        currentTimeMillis ?: return ""
        return SimpleDateFormat(
            "EEEE, d MMMM yyyy",
            locale
        ).format(Date(currentTimeMillis))
    }
}