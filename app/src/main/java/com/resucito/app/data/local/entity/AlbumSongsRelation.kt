package com.resucito.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumSongsRelation(
    @Embedded val album: AlbumEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val songs: List<SongEntity>
)