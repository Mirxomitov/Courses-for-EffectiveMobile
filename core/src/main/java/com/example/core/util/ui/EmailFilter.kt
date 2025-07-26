package com.example.core.util.ui

fun Char.isCyrillic(): Boolean {
    return this in '\u0400'..'\u04FF' || this in '\u0500'..'\u052F'
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}