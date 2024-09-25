package com.resucito.app.di

import android.content.Context
import com.resucito.app.data.local.resource.AndroidResourceProvider
import com.resucito.app.data.local.resource.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

        @Provides
        fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
            return AndroidResourceProvider(context)
        }
}