package com.resucito.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.resucito.app.db.dao.SongDao
import com.resucito.app.db.model.CategoryListConverter
import com.resucito.app.db.model.SongEntity
import com.resucito.app.db.model.SongEntityFTS
import com.resucito.app.db.model.StageConverter
import com.resucito.app.db.model.StringListConverter


@Database(entities = [SongEntity::class, SongEntityFTS::class], version = 1, exportSchema = false)
@TypeConverters(StageConverter::class, StringListConverter::class, CategoryListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}