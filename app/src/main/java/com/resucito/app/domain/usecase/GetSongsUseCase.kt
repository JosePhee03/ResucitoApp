package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Song
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute():List<Song>{
        return repository.getSongsFromDatabase()
    }
}