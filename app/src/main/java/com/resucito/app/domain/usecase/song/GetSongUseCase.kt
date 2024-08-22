package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.domain.model.Song
import javax.inject.Inject

class GetSongUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(songId: String):Song?{
        return repository.getSong(songId)
    }
}