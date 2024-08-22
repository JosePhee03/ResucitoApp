package com.resucito.app.domain.usecase.preferences

import com.resucito.app.data.preferences.AppSharedPreferences
import javax.inject.Inject

class SetIsDarkThemeUseCase @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

    fun execute(isDarkTheme: Boolean){
        appSharedPreferences.setThemePreferences(isDarkTheme)
    }
}