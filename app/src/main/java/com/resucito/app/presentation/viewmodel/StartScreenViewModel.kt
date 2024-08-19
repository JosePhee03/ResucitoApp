package com.resucito.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.domain.usecase.SetCountCategoryUseCase
import com.resucito.app.domain.usecase.SetCountStageUseCase
import com.resucito.app.domain.usecase.StartAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StartState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val setCountCategoryUseCase: SetCountCategoryUseCase,
    private val setCountStageUseCase: SetCountStageUseCase,
    private val startAppUseCase: StartAppUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> get() = _state

    fun onCreate(filename: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    errorMessage = ""
                )
            }

            val songsResult = startAppUseCase.execute(filename)

            songsResult.fold(
                onSuccess = { songs ->
                    val countCategories =
                        songs.flatMap { it.categories }.groupingBy { it }.eachCount()

                    val countStages = songs.map { it.stage }.groupingBy { it }.eachCount()

                    Category.entries.forEach {
                        setCountCategoryUseCase.execute(it, countCategories[it] ?: 0)
                    }

                    Stage.entries.forEach {
                        setCountStageUseCase.execute(it, countStages[it] ?: 0)
                    }

                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                            errorMessage = exception.message ?: "Error al cargar los cantos"
                        )
                    }
                }
            )

        }
    }

}