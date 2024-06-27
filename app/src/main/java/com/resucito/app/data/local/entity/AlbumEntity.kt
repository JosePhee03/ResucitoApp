package com.resucito.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "album", indices = [Index(value = ["name"], unique = true)])
data class AlbumEntity(

    @PrimaryKey(true)
    val id: Int = 0,
    val name: String

)

