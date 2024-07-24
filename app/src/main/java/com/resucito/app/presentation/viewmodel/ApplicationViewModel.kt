package com.resucito.app.presentation.viewmodel

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import com.resucito.app.domain.usecase.GetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.GetIsFirstRunUseCase
import com.resucito.app.domain.usecase.SetIsDarkThemeUseCase
import com.resucito.app.domain.usecase.SetIsFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ApplicationState(
    val isDarkTheme: Boolean = false, val isFirstRun: Boolean = false
)

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val setIsDarkThemeUseCase: SetIsDarkThemeUseCase,
    private val getIsDarkThemeUseCase: GetIsDarkThemeUseCase,
    private val getIsFirstRunUseCase: GetIsFirstRunUseCase,
    private val setIsFirstRunUseCase: SetIsFirstRunUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(
        ApplicationState()
    )
    val state: StateFlow<ApplicationState> = _state

    init {
        _state.update {
            it.copy(
                isFirstRun = getIsFirstRun(),
                isDarkTheme = getIsDarkTheme()
            )
        }
    }

    fun setIsDarkMode(isDarkTheme: Boolean) {
        setIsDarkThemeUseCase.execute(isDarkTheme)
        _state.update {
            it.copy(
                isDarkTheme = isDarkTheme
            )
        }
    }

    private fun getIsFirstRun(): Boolean {
        return getIsFirstRunUseCase.execute()
    }

    fun setIsFirstRun(isFirstRun: Boolean) {
        setIsFirstRunUseCase.execute(isFirstRun)
    }

    private fun getIsDarkTheme(): Boolean {
        val systemTheme = (context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        return getIsDarkThemeUseCase.execute(systemTheme)
    }


}