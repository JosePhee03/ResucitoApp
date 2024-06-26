package com.resucito.app.domain.usecase

import com.resucito.app.data.preferences.AppSharedPreferences
import javax.inject.Inject

class GetIsDarkThemeUseCase @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

        fun execute(systemTheme: Boolean): Boolean{
            return appSharedPreferences.getThemePreferences(systemTheme)
        }
}