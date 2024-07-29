package com.resucito.app.domain.usecase

import android.content.Context
import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Song
import javax.inject.Inject

class StartAppUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(context: Context, filename: String): Result<List<Song>> {
        return runCatching {
            repository.cleanSongs().getOrThrow()
            repository.cleanSongsFts().getOrThrow()
            val songs = repository.getSongsFromAssets(context, filename).getOrThrow()
            repository.insertSongs(songs).getOrThrow()
            return Result.success(songs)
        }
    }
}