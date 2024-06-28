package com.resucito.app.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.resucito.app.R
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.theme.ThemeApp

data class ColorStage(val textColor: Color, val backgroundColor: Color)

@Composable
fun colorStage(stage: Stage): ColorStage {

    return when (stage) {
        Stage.PRECATECHUMENATE -> ColorStage(
            MaterialTheme.colorScheme.surfaceVariant,
            ThemeApp.color.precatechumenateContainer
        )
        Stage.CATECHUMENATE -> ColorStage(
            MaterialTheme.colorScheme.surfaceVariant,
            ThemeApp.color.catechumenateContainer
        )
        Stage.ELECTION -> ColorStage(
            MaterialTheme.colorScheme.surfaceVariant,
            ThemeApp.color.electionContainer
        )
        Stage.LITURGY -> ColorStage(
            MaterialTheme.colorScheme.surfaceVariant,
            ThemeApp.color.liturgyContainer
        )
    }
}