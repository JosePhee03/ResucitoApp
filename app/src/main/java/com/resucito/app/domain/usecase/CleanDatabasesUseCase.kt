package com.resucito.app.domain.usecase

import com.resucito.app.domain.repository.SongRepository
import javax.inject.Inject

class CleanDatabasesUseCase @Inject constructor(
    private val songRepository: SongRepository,
) {

    suspend fun execute(): Result<Unit> {
        return runCatching {
            songRepository.cleanSongs().getOrThrow()
            songRepository.cleanSongsFts().getOrThrow()
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