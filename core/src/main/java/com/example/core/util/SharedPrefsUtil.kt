package com.example.core.util

import android.annotation.SuppressLint
import android.content.Context

object SharedPrefsUtils {
    fun isUserLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getBoolean("logged_in", false)
    }

    @SuppressLint("UseKtx")
    fun setUserLoggedIn(context: Context, isLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("logged_in", isLoggedIn).apply()
    }
}
