package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Category
import com.resucito.app.util.GetStringResource


@Composable
fun SuggestionChipCategory(category: Category, onClick: (Category) -> Unit) {

    SuggestionChip(
        onClick = {
            onClick(category)
        },
        label = {
            Text(
                GetStringResource.getLocalizedName(category),
                fontWeight = FontWeight.Medium
            )
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )
}

@Composable
fun InputChipCategory(category: Category, onClick: (Category) -> Unit) {

    SuggestionChip(
        onClick = {
            onClick(category)
        },
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = stringResource(R.string.remove_chip),
                modifier = Modifier.size(InputChipDefaults.AvatarSize)
            )
        },
        label = {
            Text(
                GetStringResource.getLocalizedName(category),
                fontWeight = FontWeight.Medium
            )
        },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    )
}