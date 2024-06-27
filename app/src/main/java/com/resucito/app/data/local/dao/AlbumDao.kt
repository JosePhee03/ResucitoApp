package com.resucito.app.data.local.dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.resucito.app.data.local.entity.AlbumEntity
import com.resucito.app.data.local.entity.AlbumSongsRelation
import com.resucito.app.data.local.entity.SongEntity

interface AlbumDao {

    @Insert
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert
    suspend fun insertSong(song: SongEntity): Long

    @Transaction
    suspend fun insertAlbumWithSongs(album: AlbumEntity, songs: List<SongEntity>) {
        val albumId = insertAlbum(album)
        for (song in songs) {
            insertSong(song.copy(albumId = albumId.toInt()))
        }
    }

    @Transaction
    @Query("SELECT * FROM album WHERE id = :albumId")
    suspend fun getAlbumWithSongs(albumId: Int): List<AlbumSongsRelation>

}