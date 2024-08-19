package com.resucito.app.domain.repository

import android.content.Context
import com.resucito.app.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {

    suspend fun getSongsFromDatabase(): Result<Flow<List<Song>>>

    suspend fun getSongsFromAssets(filename: String): Result<List<Song>>

    suspend fun insertSongs(songs: List<Song>): Result<Unit>

    suspend fun searchSongs(query: String): Result<Flow<List<Song>>>

    suspend fun cleanSongs(): Result<Unit>

    suspend fun cleanSongsFts(): Result<Unit>

    suspend fun getSong(songId: String): Song?

    suspend fun updateFavoriteSong(songId: String, favorite: Boolean): Result<Unit>

    suspend fun getAllFavoriteSongs(isFavorite: Boolean): Result<Flow<List<Song>>>

}