package com.resucito.app.domain.usecase.preferences

import com.resucito.app.data.preferences.AppSharedPreferences
import javax.inject.Inject

class GetIsFirstRunUseCase @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

    fun execute(): Boolean{
        return appSharedPreferences.isFirstRun
    }

}