package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.domain.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val songRepository: SongRepository) {

    suspend fun execute(): Result<Flow<List<Song>>> {
        return songRepository.getAllFavoriteSongs(true)
    }
}