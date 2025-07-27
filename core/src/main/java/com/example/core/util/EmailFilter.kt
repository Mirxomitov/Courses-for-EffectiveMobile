package com.example.core.util

import android.util.Patterns

fun Char.isCyrillic(): Boolean {
    return this in '\u0400'..'\u04FF' || this in '\u0500'..'\u052F'
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}