package com.resucito.app.di

import android.content.Context
import com.resucito.app.data.preferences.AppSharedPreferences
import com.resucito.app.data.preferences.SongSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideAppSharedPreferences(
        @ApplicationContext context: Context
    ): AppSharedPreferences {
        return AppSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideSongSharedPreferences(
        @ApplicationContext context: Context
    ): SongSharedPreferences {
        return SongSharedPreferences(context)
    }

}