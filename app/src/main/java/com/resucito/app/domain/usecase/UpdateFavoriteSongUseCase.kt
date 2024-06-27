package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.AlbumRepository
import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Album
import javax.inject.Inject

class UpdateFavoriteSongUseCase @Inject constructor(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
) {


   private val favoriteAlbumName = "Favoritos"

    suspend fun execute(songId: String, favorite: Boolean): Result<Unit> {
        return runCatching {
            var albumId: Int? = null

            if (favorite) {
                var newAlbumId = albumRepository.getAlbumId(favoriteAlbumName).getOrThrow()

                if (newAlbumId === null) {
                    newAlbumId = albumRepository.insertAlbum(Album(favoriteAlbumName)).getOrThrow()
                }
                albumId = newAlbumId
            }

            albumRepository.updateAlbumId(songId, albumId).getOrThrow()

            songRepository.updateFavoriteSong(songId, favorite).getOrThrow()
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