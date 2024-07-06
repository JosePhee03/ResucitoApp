package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resucito.app.domain.model.Stage
import com.resucito.app.util.GetStringResource
import com.resucito.app.util.colorStage

@Composable
fun SuggestionChipStage(stage: Stage, onClick: () -> Unit) {

    SuggestionChip(
        onClick = {
            onClick()
        },
        label = { Text(GetStringResource.getLocalizedName(stage), fontWeight = FontWeight.Medium) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = colorStage(stage).backgroundColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )
}