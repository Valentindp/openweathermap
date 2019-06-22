package com.valentyn.openweathermap.util

import java.text.DateFormat
import java.util.*

fun getFormatDate(date: Date?): String {
    var dateString = ""
    if (date != null) {
        val df = DateFormat.getDateTimeInstance()
        dateString = df.format(date)
    }
    return dateString
}

fun getFormatTemp(temp :Double?) = "$temp ℃"

