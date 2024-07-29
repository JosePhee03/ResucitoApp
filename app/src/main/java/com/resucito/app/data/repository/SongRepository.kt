package com.resucito.app.data.repository

import android.content.Context
import com.resucito.app.data.local.dao.SongDao
import com.resucito.app.data.local.json.LocalJsonParser
import com.resucito.app.data.mapper.SongMapper
import com.resucito.app.domain.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SongRepository @Inject constructor(private val songDao: SongDao) {

    suspend fun getSongsFromDatabase(): Result<Flow<List<Song>>> {
        return runCatching {
            val songEntities = withContext(Dispatchers.IO) { songDao.getAllSongs() }
            val songs = songEntities.map { list ->
                list.map { SongMapper.fromEntityToDomain(it) }
            }

            return Result.success(songs)
        }
    }

    suspend fun getSongsFromAssets(context: Context, filename: String): Result<List<Song>> {
        return LocalJsonParser.parseUsersFromAssets(context, filename).mapCatching { songsDto ->
            songsDto.map { SongMapper.fromDtoToDomain(it) }
        }
    }

    suspend fun insertSongs(songs: List<Song>): Result<Unit> {
        return runCatching {
            val songEntities = songs.map { SongMapper.fromDomainToEntity(it) }
            songDao.insertAll(songEntities)
        }
    }

    suspend fun searchSongs(query: String): Result<Flow<List<Song>>> {
        return runCatching {
            val songEntities = withContext(Dispatchers.IO) {
                songDao.searchSongs(query)
            }
            val songs = songEntities.map { list ->
                list.map { SongMapper.fromEntityToDomain(it) }
            }
            return Result.success(songs)
        }

    }

    suspend fun cleanSongs(): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) {
                songDao.deleteAllSongs()
            }
        }
    }

    suspend fun cleanSongsFts(): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) {
                songDao.deleteAllSongsFts()
            }
        }
    }

    suspend fun getSong(songId: String): Song? {
        val songEntity = withContext(Dispatchers.IO) {
            songDao.findSongById(songId)
        }
        return songEntity?.let { SongMapper.fromEntityToDomain(songEntity) }
    }


    suspend fun updateFavoriteSong(songId: String, favorite: Boolean): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) {
                songDao.updateFavoriteSong(songId, favorite)
            }
        }
    }

    suspend fun getAllFavoriteSongs(isFavorite: Boolean): Result<Flow<List<Song>>> {
        return runCatching {
            val songEntities = withContext(Dispatchers.IO) {
                songDao.getAllFavoriteSongs(isFavorite)
            }
            val songs = songEntities.map { list ->
                list.map { SongMapper.fromEntityToDomain(it) }
            }

            return Result.success(songs)
        }
    }
}