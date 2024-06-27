package com.resucito.app.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val insertSongsUseCase: InsertSongsUseCase,
    private val getSongsUseCase: GetSongsUseCase,
    private val setCountCategoryUseCase: SetCountCategoryUseCase,
    private val setCountStageUseCase: SetCountStageUseCase,
    private val cleanDatabasesUseCase: CleanDatabasesUseCase
) : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var isError by mutableStateOf(false)
        private set

    fun onCreate(context: Context, filename: String) {
        viewModelScope.launch {
            isLoading = true
            if (cleanDatabasesUseCase.execute().isSuccess) {
                insertSongsUseCase.execute(context, filename).fold(
                    onSuccess = {
                        val songsResult = getSongsUseCase.execute()
                        val countCategories = songsResult.flatMap { it.categories }
                            .groupingBy { it }
                            .eachCount()

                        val countStages = songsResult.map { it.stage }
                            .groupingBy { it }
                            .eachCount()

                        Category.entries.forEach {
                            setCountCategoryUseCase.execute(it, countCategories[it] ?: 0)
                        }

                        Stage.entries.forEach {
                            setCountStageUseCase.execute(it, countStages[it] ?: 0)
                        }
                    },
                    onFailure = {
                        isError = true
                    }
                )
            } else {
                isError = true
            }

            isLoading = false
        }
    }


}