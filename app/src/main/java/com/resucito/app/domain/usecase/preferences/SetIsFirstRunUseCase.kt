package com.resucito.app.domain.usecase.preferences

import com.resucito.app.data.preferences.AppSharedPreferences
import javax.inject.Inject

class SetIsFirstRunUseCase @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

    fun execute(isFirstRun: Boolean) {
        appSharedPreferences.isFirstRun = isFirstRun
    }

}