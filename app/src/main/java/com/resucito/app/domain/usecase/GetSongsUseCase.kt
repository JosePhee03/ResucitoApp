package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(): Result<Flow<List<Song>>>{
        return repository.getSongsFromDatabase()
    }
}