package com.resucito.app.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.AndroidViewModel
import com.resucito.app.data.Category
import com.resucito.app.data.Stage

class AppPreferencesProvider(application: Application) : AndroidViewModel(application) {

    companion object {
        const val STAGE_PREFS = "STAGE_PREFS"
        const val CATEGORY_PREFS = "CATEGORY_PREFS"
        const val THEME_PREFS = "THEME_PREFS"
        const val THEME_IS_DARK_MODE_KEY = "IS_DARK_MODE"
    }

    private val stagePreferences: SharedPreferences = application.getSharedPreferences(STAGE_PREFS, Context.MODE_PRIVATE)
    private val categoryPreferences: SharedPreferences = application.getSharedPreferences(CATEGORY_PREFS, Context.MODE_PRIVATE)
    private val themePreferences: SharedPreferences = application.getSharedPreferences(
        THEME_PREFS, Context.MODE_PRIVATE)

    fun updateStagePreferences(stage: Stage, count: Int) {
        stagePreferences.edit().putInt(stage.name, count).apply()
    }

    fun getStagePreferences(stage: Stage): Int {
        return stagePreferences.getInt(stage.name, 0)
    }

    fun updateCategoryPreferences(category: Category, count: Int) {
        categoryPreferences.edit().putInt(category.name, count).apply()
    }

    fun getCategoryPreferences(category: Category): Int {
        return categoryPreferences.getInt(category.name, 0)
    }

    fun updateThemePreferences(isDarkMode: Boolean) {
        themePreferences.edit().putBoolean(THEME_IS_DARK_MODE_KEY, isDarkMode).apply()
    }

    fun getThemePreferences(default: Boolean): Boolean {
        return themePreferences.getBoolean(THEME_IS_DARK_MODE_KEY, default)
    }

}