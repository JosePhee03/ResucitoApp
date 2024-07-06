package com.resucito.app.presentation.ui.screen.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R


@Composable
fun ItemLibrary(name: String, count: Int, enabled: Boolean, onClick: () -> Unit) {

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onClick() },
        leadingContent = {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(16.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    painter = painterResource(R.drawable.ic_favorite_filled),
                    contentDescription = stringResource(R.string.cover_image)
                )
            }

        },
        headlineContent = { Text(name) },
        supportingContent = {
            Text("$count ${stringResource(R.string.songs)}")
        }
    )
}