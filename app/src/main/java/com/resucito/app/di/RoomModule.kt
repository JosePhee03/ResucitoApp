package com.resucito.app.di

import android.content.Context
import androidx.room.Room
import com.resucito.app.data.local.dao.SongDao
import com.resucito.app.data.local.db.AppDatabase
import com.resucito.app.data.repository.SongRoomRepository
import com.resucito.app.domain.repository.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "app_db"

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSongDao(db: AppDatabase) = db.songDao()

    @Provides
    @Singleton
    fun provideSongRepository(songDao: SongDao, context: Context): SongRepository {
        return SongRoomRepository(songDao, context)
    }

}