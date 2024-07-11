package com.resucito.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.resucito.app.domain.usecase.GetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.GetIsFirstRunUseCase
import com.resucito.app.domain.usecase.SetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.SetIsFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class ApplicationState(
    val isDarkTheme: Boolean = false, val isFirstRun: Boolean = false
)

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val setIsDarkThemeUseCase: SetIsDarkThemeUseCase,
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase,
    private val getIsFirstRunUseCase: GetIsFirstRunUseCase,
    private val setIsFirstRunUseCase: SetIsFirstRunUseCase
) : ViewModel() {

    val state = MutableStateFlow(ApplicationState())

    fun setIsDarkMode(isDarkTheme: Boolean) {
        setIsDarkThemeUseCase.execute(isDarkTheme)
        state.value = state.value.copy(
            isDarkTheme = isDarkTheme
        )
    }

    fun getIsFirstRun(): Boolean {
        return getIsFirstRunUseCase.execute()
    }

    fun setIsFirstRun(isFirstRun: Boolean) {
        setIsFirstRunUseCase.execute(isFirstRun)
    }

    fun getIsDarkTheme(systemTheme: Boolean): Boolean {
        return getIsDarkThemeUseCase.execute(systemTheme)
    }


}