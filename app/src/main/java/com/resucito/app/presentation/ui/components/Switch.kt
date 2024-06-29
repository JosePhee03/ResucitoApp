package com.resucito.app.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable

@Composable
fun Switch(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    androidx.compose.material3.Switch(
        checked = isChecked,
        onCheckedChange = {
            onCheckedChange(it)
        },
        colors = SwitchDefaults.colors(
            uncheckedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainer,
            checkedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            checkedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
            checkedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    )
}