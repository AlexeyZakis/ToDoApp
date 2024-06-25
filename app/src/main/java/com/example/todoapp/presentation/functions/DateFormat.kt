package com.example.todoapp.presentation.functions

import java.time.LocalDate

object DateFormat {
    fun getDateString(date: LocalDate?): String {
        date ?: return ""
        return getDateString(date.dayOfMonth, date.monthValue, date.year)
    }
    fun getDateString(day: Int, month: Int, year: Int): String {
        return String.format("%02d.%02d.%04d", day, month, year)
    }
}