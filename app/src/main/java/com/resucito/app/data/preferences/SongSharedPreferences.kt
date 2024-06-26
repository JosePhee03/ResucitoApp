package com.resucito.app.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage

class SongSharedPreferences(context: Context) {

    companion object {
        const val STAGE_PREFS = "stage_prefs"
        const val CATEGORY_PREFS = "category_prefs"
    }

    private val stagePreferences: SharedPreferences = context.getSharedPreferences(STAGE_PREFS, Context.MODE_PRIVATE)
    private val categoryPreferences: SharedPreferences = context.getSharedPreferences(
        CATEGORY_PREFS, Context.MODE_PRIVATE)

    fun setCountStage(stage: Stage, count: Int) {
        stagePreferences.edit().putInt(stage.name, count).apply()
    }

    fun getCountStage(stage: Stage): Int {
        return stagePreferences.getInt(stage.name, 0)
    }

    fun setCountCategory(category: Category, count: Int) {
        categoryPreferences.edit().putInt(category.name, count).apply()
    }

    fun getCountCategory(category: Category): Int {
        return categoryPreferences.getInt(category.name, 0)
    }

}