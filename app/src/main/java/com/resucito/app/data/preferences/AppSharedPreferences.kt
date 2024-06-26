package com.resucito.app.data.preferences

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences (context: Context)  {

    companion object {
        private const val KEY_FIRST_RUN = "first_run"
        private const val DARK_THEME_PREFS = "dark_theme_prefs"
        private const val KEY_DARK_THEME = "key_dark_theme"
    }

    private val themePreferences: SharedPreferences = context.getSharedPreferences(
        DARK_THEME_PREFS, Context.MODE_PRIVATE)

    private val isFirstRunPreferences: SharedPreferences = context.getSharedPreferences(
        DARK_THEME_PREFS, Context.MODE_PRIVATE)

    var isFirstRun: Boolean
        get() = isFirstRunPreferences.getBoolean(KEY_FIRST_RUN, false)
        set(value) = isFirstRunPreferences.edit().putBoolean(KEY_FIRST_RUN, value).apply()

    fun clearIsFirstRunPreferences() {
        isFirstRunPreferences.edit().clear().apply()
    }

    fun setThemePreferences(isDarkMode: Boolean) {
        themePreferences.edit().putBoolean(KEY_DARK_THEME, isDarkMode).apply()
    }

    fun getThemePreferences(default: Boolean): Boolean {
        return themePreferences.getBoolean(KEY_DARK_THEME, default)
    }

}