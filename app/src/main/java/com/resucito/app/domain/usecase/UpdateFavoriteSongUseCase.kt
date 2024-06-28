package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.SongRepository
import javax.inject.Inject

class UpdateFavoriteSongUseCase @Inject constructor(
    private val songRepository: SongRepository,
) {

    suspend fun execute(songId: String, favorite: Boolean): Result<Unit> {
        return songRepository.updateFavoriteSong(songId, favorite)
    }

}