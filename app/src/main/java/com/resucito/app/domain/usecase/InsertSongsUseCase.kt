package com.resucito.app.domain.usecase

import android.content.Context
import com.resucito.app.data.repository.SongRepository
import javax.inject.Inject

class InsertSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(context: Context, filename: String): Result<Unit> {
        return runCatching {
            val songs = repository.getSongsFromAssets(context, filename).getOrThrow()

            repository.cleanSongs().getOrThrow()
            repository.cleanSongsFts().getOrThrow()
            repository.insertSongs(songs).getOrThrow()
        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}