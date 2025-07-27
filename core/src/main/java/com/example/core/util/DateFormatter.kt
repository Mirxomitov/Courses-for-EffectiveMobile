package com.example.core.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toReadableDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this // fallback to original if parsing fails
    }
}
