package com.resucito.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.domain.usecase.GetCountCategoryUseCase
import com.resucito.app.domain.usecase.GetCountStageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCountCategoryUseCase: GetCountCategoryUseCase,
    private val getCountStageUseCase: GetCountStageUseCase
): ViewModel() {

    fun getCountStage (stage: Stage): Int {
        return getCountStageUseCase.execute(stage)
    }

    fun getCountCategory (category: Category): Int {
        return getCountCategoryUseCase.execute(category)
    }

}