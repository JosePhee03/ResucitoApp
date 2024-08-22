package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.domain.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(query: String): Result<Flow<List<Song>>> {
        return repository.searchSongs(query)
    }
}