package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import javax.inject.Inject

class UpdateFavoriteSongUseCase @Inject constructor(
    private val songRepository: SongRepository,
) {

    suspend fun execute(songId: String, favorite: Boolean): Result<Unit> {
        return songRepository.updateFavoriteSong(songId, favorite)
    }

}