package com.resucito.app.domain.usecase

import com.resucito.app.domain.model.Song
import com.resucito.app.domain.repository.SongRepository
import javax.inject.Inject

class StartAppUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(filename: String): Result<List<Song>> {
        return runCatching {
            repository.cleanSongs().getOrThrow()
            repository.cleanSongsFts().getOrThrow()
            val songs = repository.getSongsFromAssets(filename).getOrThrow()
            repository.insertSongs(songs).getOrThrow()
            return Result.success(songs)
        }
    }
}