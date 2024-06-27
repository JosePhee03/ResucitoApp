package com.resucito.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity(

    @PrimaryKey(true)
    val id: Int = 0,
    val name: String

)

