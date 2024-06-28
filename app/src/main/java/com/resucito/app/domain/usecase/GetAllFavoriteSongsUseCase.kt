package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Song
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val songRepository: SongRepository) {

    suspend fun execute(): Result<List<Song>> {
        return songRepository.getAllFavoriteSongs(true)
    }
}