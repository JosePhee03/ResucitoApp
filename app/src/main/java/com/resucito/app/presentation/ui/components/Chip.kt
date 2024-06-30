package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Stage
import com.resucito.app.util.colorStage

@Composable
fun Chip(text: String, icon: Boolean, stage: Stage?, onClick: () -> Unit) {
    InputChip(
        selected = false,
        onClick = {
            onClick()
        },
        label = { Text(text, fontWeight = FontWeight.Medium) },
        colors = InputChipDefaults.inputChipColors(
            containerColor = if (stage != null) colorStage(stage).backgroundColor else MaterialTheme.colorScheme.background
        ),
        trailingIcon = {
            if (icon) {
                Icon(
                    painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.remove_chip),
                    Modifier.size(InputChipDefaults.AvatarSize)
                )
            }
        },
        border = null,
        elevation = InputChipDefaults.inputChipElevation(2.dp)
    )
}