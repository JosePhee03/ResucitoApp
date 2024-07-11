package com.resucito.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.domain.usecase.CleanDatabasesUseCase
import com.resucito.app.domain.usecase.GetSongsUseCase
import com.resucito.app.domain.usecase.InsertSongsUseCase
import com.resucito.app.domain.usecase.SetCountCategoryUseCase
import com.resucito.app.domain.usecase.SetCountStageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StartState(
    val isLoading: Boolean = true, val isError: Boolean = false
)

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val insertSongsUseCase: InsertSongsUseCase,
    private val getSongsUseCase: GetSongsUseCase,
    private val setCountCategoryUseCase: SetCountCategoryUseCase,
    private val setCountStageUseCase: SetCountStageUseCase,
    private val cleanDatabasesUseCase: CleanDatabasesUseCase
) : ViewModel() {

    val state = MutableStateFlow(StartState())

    fun onCreate(context: Context, filename: String) {
        viewModelScope.launch {
            state.value = state.value.copy(
                isLoading = true, isError = false
            )
            if (cleanDatabasesUseCase.execute().isSuccess) {
                insertSongsUseCase.execute(context, filename).fold(onSuccess = {
                    val songsResult = getSongsUseCase.execute()
                    val countCategories =
                        songsResult.flatMap { it.categories }.groupingBy { it }.eachCount()

                    val countStages = songsResult.map { it.stage }.groupingBy { it }.eachCount()

                    Category.entries.forEach {
                        setCountCategoryUseCase.execute(it, countCategories[it] ?: 0)
                    }

                    Stage.entries.forEach {
                        setCountStageUseCase.execute(it, countStages[it] ?: 0)
                    }
                }, onFailure = {
                    state.value = state.value.copy(
                        isError = true
                    )
                })
            } else {
                state.value = state.value.copy(
                    isError = true
                )
            }

            state.value = state.value.copy(
                isLoading = false
            )
        }
    }


}