package com.example.todoapp.presentation.functions

import android.icu.util.TimeZone
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormat {
    fun getDateString(date: LocalDate?, locale: Locale = Locale.getDefault()): String {
        date ?: return ""
        return date.format(
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", locale)
        )
    }
    fun dateToLong(date: LocalDate): Long {
        val startOfDay = date.atStartOfDay(ZoneOffset.UTC)
        return startOfDay.toInstant().toEpochMilli()
    }
    fun longToLocalDate(epochMilli: Long, zoneId: ZoneId = ZoneId.systemDefault()): LocalDate {
        val instant = Instant.ofEpochMilli(epochMilli)
        val zonedDateTime = instant.atZone(zoneId)
        return zonedDateTime.toLocalDate()
    }
}