package com.resucito.app.domain.usecase.preferences

import com.resucito.app.data.preferences.SongSharedPreferences
import com.resucito.app.domain.model.Stage
import javax.inject.Inject

class GetCountStageUseCase @Inject constructor(private val songSharedPreferences: SongSharedPreferences) {

    fun execute(stage: Stage): Int{
        return songSharedPreferences.getCountStage(stage)
    }
}