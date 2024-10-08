package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Stage
import com.resucito.app.util.GetStringResource
import com.resucito.app.util.colorStage

@Composable
fun SuggestionChipStage(stage: Stage, onClick: (Stage) -> Unit) {

    SuggestionChip(
        onClick = {
            onClick(stage)
        },
        label = { Text(GetStringResource.getLocalizedName(stage), fontWeight = FontWeight.Medium) },
        colors = SuggestionChipDefaults.suggestionChipColors().copy(
            containerColor = colorStage(stage).backgroundColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )
}

@Composable
fun InputChipStage(stage: Stage, onClick: (Stage) -> Unit) {

    SuggestionChip(
        onClick = {
            onClick(stage)
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.remove_chip),
                modifier = Modifier.size(InputChipDefaults.AvatarSize)
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors().copy(
            containerColor = colorStage(stage).backgroundColor
        ),
        label = {
            Text(
                GetStringResource.getLocalizedName(stage),
                fontWeight = FontWeight.Medium
            )
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )
}