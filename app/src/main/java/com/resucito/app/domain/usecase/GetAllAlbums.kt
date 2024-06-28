package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.AlbumRepository
import com.resucito.app.domain.model.Album
import javax.inject.Inject

class GetAllAlbums @Inject constructor(private val albumRepository: AlbumRepository) {

    suspend fun execute(): Result<List<Album>> {
        return albumRepository.getAllAlbums()
    }
}