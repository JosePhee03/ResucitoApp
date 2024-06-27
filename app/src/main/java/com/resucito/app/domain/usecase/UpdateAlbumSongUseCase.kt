package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.AlbumRepository
import javax.inject.Inject

class UpdateAlbumSongUseCase @Inject constructor(private val albumRepository: AlbumRepository) {

    suspend fun execute(songId: String, albumId: Int): Result<Unit> {
        return albumRepository.updateAlbumId(songId, albumId)
    }
}