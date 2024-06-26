package com.resucito.app.domain.usecase

import com.resucito.app.data.preferences.SongSharedPreferences
import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Category
import javax.inject.Inject

class UpdateFavoriteSongUseCase @Inject constructor(private val songRepository: SongRepository) {

    suspend fun execute(songId: String, favorite: Boolean) {
        songRepository.updateFavoriteSong(songId, favorite)
    }

}