package com.resucito.app.domain.usecase

import com.resucito.app.data.preferences.SongSharedPreferences
import com.resucito.app.domain.model.Stage
import javax.inject.Inject

class SetCountStageUseCase @Inject constructor(private val songSharedPreferences: SongSharedPreferences) {

    fun execute(stage: Stage, count: Int) {
        songSharedPreferences.setCountStage(stage, count)
    }

}