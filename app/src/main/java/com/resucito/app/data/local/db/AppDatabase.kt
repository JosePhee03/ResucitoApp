package com.resucito.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.resucito.app.data.local.dao.SongDao
import com.resucito.app.data.local.entity.AlbumEntity
import com.resucito.app.data.local.entity.CategoryListConverter
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.data.local.entity.SongEntityFTS
import com.resucito.app.data.local.entity.StageConverter
import com.resucito.app.data.local.entity.StringListConverter


@Database(entities = [SongEntity::class, SongEntityFTS::class, AlbumEntity::class], version = 1, exportSchema = false)
@TypeConverters(StageConverter::class, StringListConverter::class, CategoryListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

}