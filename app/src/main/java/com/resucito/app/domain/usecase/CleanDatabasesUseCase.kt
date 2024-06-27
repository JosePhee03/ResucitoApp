package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.AlbumRepository
import com.resucito.app.data.repository.SongRepository
import javax.inject.Inject

class CleanDatabasesUseCase @Inject constructor(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) {

    suspend fun execute(): Result<Unit> {
        return runCatching {
            albumRepository.cleanAlbums().getOrThrow()
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