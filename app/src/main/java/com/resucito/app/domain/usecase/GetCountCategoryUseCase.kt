package com.resucito.app.domain.usecase

import com.resucito.app.data.preferences.SongSharedPreferences
import com.resucito.app.domain.model.Category
import javax.inject.Inject

class GetCountCategoryUseCase @Inject constructor(private val songSharedPreferences: SongSharedPreferences) {

    fun execute(category: Category): Int {
        return songSharedPreferences.getCountCategory(category)
    }
}