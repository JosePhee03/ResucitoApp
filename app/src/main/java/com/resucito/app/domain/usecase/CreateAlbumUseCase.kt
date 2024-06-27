package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.AlbumRepository
import com.resucito.app.domain.model.Album
import javax.inject.Inject

class CreateAlbumUseCase @Inject constructor(private val albumRepository: AlbumRepository) {

    suspend fun execute(album: Album): Result<Int> {
        return albumRepository.insertAlbum(album)
    }
}