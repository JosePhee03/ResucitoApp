package com.resucito.app.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.FtsOptions
import androidx.room.PrimaryKey


@Entity(tableName = "songs_fts")
@Fts4(contentEntity = SongEntity::class, tokenizer = FtsOptions.TOKENIZER_UNICODE61)
data class SongEntityFTS(
    @ColumnInfo(name = "rowid")
    @PrimaryKey(true) val rowId: Int,
    val title: String,
    val subtitle: String,
    val lyric: String
)
