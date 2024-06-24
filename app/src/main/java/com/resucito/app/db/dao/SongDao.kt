package com.resucito.app.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import com.resucito.app.db.model.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<SongEntity>)

    @Query("INSERT INTO songs_fts (rowid, title, subtitle, lyric) VALUES (:rowId, :title, :subtitle, :lyric)")
    suspend fun insertAllFTS(rowId: Int, title: String, subtitle: String, lyric: String)

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun findSongById(id: String): SongEntity

    @Query(
        """
        SELECT songs.*
        FROM songs
        WHERE rowid IN (
            SELECT rowid
            FROM songs_fts
            WHERE songs_fts.title MATCH :query OR songs_fts.subtitle MATCH :query
        )
        """
    )
    suspend fun searchSongs(query: String): List<SongEntity>
    @Query("SELECT Count(*) FROM songs WHERE stage = :stage")
    suspend fun countSongsByStage(stage: Stage): Int

    @Query("SELECT Count(*) FROM songs WHERE categories IN (:categories)")
    suspend fun countSongsByCategory(categories: List<Category>): Int

    @Query("UPDATE songs SET favorite = :favorite WHERE id = :songId")
    suspend fun changeFavoriteSong(songId: String, favorite: Boolean)
}