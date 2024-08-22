package com.resucito.app.domain.usecase.preferences

import com.resucito.app.data.preferences.SongSharedPreferences
import com.resucito.app.domain.model.Category
import javax.inject.Inject

class SetCountCategoryUseCase @Inject constructor(private val songSharedPreferences: SongSharedPreferences) {

    fun execute(category: Category, count: Int) {
        songSharedPreferences.setCountCategory(category, count)
    }

}