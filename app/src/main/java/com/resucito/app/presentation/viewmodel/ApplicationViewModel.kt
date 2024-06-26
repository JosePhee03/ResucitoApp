package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.resucito.app.domain.usecase.GetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.GetIsFirstRunUseCase
import com.resucito.app.domain.usecase.SetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.SetIsFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val setIsDarkThemeUseCase: SetIsDarkThemeUseCase,
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase,
    private val getIsFirstRunUseCase: GetIsFirstRunUseCase,
    private val setIsFirstRunUseCase: SetIsFirstRunUseCase
): ViewModel() {

    var isDarkTheme by mutableStateOf(false)
        private set

    fun setIsDarkMode (isDarkTheme: Boolean) {
        this.isDarkTheme = isDarkTheme
        setIsDarkThemeUseCase.execute(isDarkTheme)
    }

    fun getIsFirstRun (): Boolean {
        return getIsFirstRunUseCase.execute()
    }

    fun setIsFirstRun (isFirstRun: Boolean) {
        setIsFirstRunUseCase.execute(isFirstRun)
    }

    fun onCreate (systemTheme: Boolean) {
        this.isDarkTheme = getIsDarkThemeUseCase.execute(systemTheme)
    }


}