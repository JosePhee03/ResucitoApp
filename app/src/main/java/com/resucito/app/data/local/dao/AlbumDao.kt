package com.resucito.app.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.resucito.app.data.local.entity.AlbumEntity
import com.resucito.app.data.local.entity.AlbumSongsRelation

interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAlbum(album: AlbumEntity): Int

    @Query("UPDATE song SET albumId = albumId WHERE id = :songId")
    suspend fun updateAlbumId(songId: String, albumId: Int?)

    @Query("SELECT id FROM album WHERE name  = :name")
    suspend fun getAlbumIdByName(name: String): Int?

    @Transaction
    @Query("SELECT * FROM album WHERE id = :albumId")
    suspend fun getAlbumWithSongs(albumId: Int): List<AlbumSongsRelation>

    @Query("SELECT COUNT(*) FROM album WHERE name = :name")
    suspend fun existAlbumName(name: String): Boolean

    @Query("DELETE FROM album")
    suspend fun deleteAlbums()

}