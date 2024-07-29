package com.resucito.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.resucito.app.data.local.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAllSongs(): Flow<List<SongEntity>>

    @Query("DELETE FROM song")
    suspend fun deleteAllSongs()

    @Query("DELETE FROM song_fts")
    suspend fun deleteAllSongsFts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<SongEntity>)

    @Query("INSERT INTO song_fts (rowid, title, subtitle, lyric) VALUES (:rowId, :title, :subtitle, :lyric)")
    suspend fun insertAllFTS(rowId: Int, title: String, subtitle: String, lyric: String)

    @Query("SELECT * FROM song WHERE id = :id")
    suspend fun findSongById(id: String): SongEntity?

    @Query(
        """
        SELECT song.*
        FROM song
        WHERE rowid IN (
            SELECT rowid
            FROM song_fts
            WHERE song_fts.title MATCH :query OR song_fts.subtitle MATCH :query
        )
        """
    )
    fun searchSongs(query: String): Flow<List<SongEntity>>

    @Query("UPDATE song SET favorite = :favorite WHERE id = :songId")
    suspend fun updateFavoriteSong(songId: String, favorite: Boolean)

    @Query("SELECT * FROM song WHERE favorite = :favorite")
    fun getAllFavoriteSongs(favorite: Boolean): Flow<List<SongEntity>>
}