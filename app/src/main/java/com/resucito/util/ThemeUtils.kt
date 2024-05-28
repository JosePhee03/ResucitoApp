package com.resucito.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate


class ThemeUtils {

    companion object {

        private const val PREFS_NAME = "THEME_PREFS"
        private const val PREF_THEME_KEY = "THEME_KEY"

        fun toggle(context: Context) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val currentMode =
                prefs.getInt(PREF_THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            val newMode = when (currentMode) {
                AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_YES
            }
            val editor = prefs.edit()
            editor.putInt(PREF_THEME_KEY, newMode)
            editor.apply()
            AppCompatDelegate.setDefaultNightMode(newMode)
        }
    }


}