package com.resucito.app.di

import android.content.Context
import androidx.room.Room
import com.resucito.app.data.local.db.SongDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SONGS_DATABASE_NAME = "song_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, SongDatabase::class.java, SONGS_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSongDao(db: SongDatabase) = db.songDao()

}