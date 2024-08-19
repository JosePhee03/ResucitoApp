package com.resucito.app.domain.usecase

import android.content.Context
import com.resucito.app.domain.repository.SongRepository
import javax.inject.Inject

class InsertSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(filename: String): Result<Unit> {
        return runCatching {
            val songs = repository.getSongsFromAssets(filename).getOrThrow()
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