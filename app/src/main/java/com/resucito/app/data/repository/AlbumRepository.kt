package com.resucito.app.data.repository

import com.resucito.app.data.local.dao.AlbumDao
import com.resucito.app.data.mapper.AlbumMapper
import com.resucito.app.domain.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepository @Inject constructor(private val albumDao: AlbumDao) {

    suspend fun insertAlbum (album: Album): Result<Int> {
        val albumEntity = AlbumMapper.fromDomainToEntity(album)
        return runCatching {
            val albumId = withContext(Dispatchers.IO) { albumDao.insertAlbum(albumEntity) }
            return Result.success(albumId)
        }
    }

    suspend fun updateAlbumId (songId: String, albumId: Int?): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) { albumDao.updateAlbumId(songId, albumId) }
        }
    }

    suspend fun cleanAlbums (): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) { albumDao.deleteAlbums() }
        }
    }

    suspend fun existAlbumName (name: String): Result<Boolean> {
        return runCatching {
            withContext(Dispatchers.IO) { albumDao.existAlbumName(name) }
        }
    }

    suspend fun getAlbumId (name: String): Result<Int?> {
        return runCatching {
            withContext(Dispatchers.IO) { albumDao.getAlbumIdByName(name) }
        }
    }

}