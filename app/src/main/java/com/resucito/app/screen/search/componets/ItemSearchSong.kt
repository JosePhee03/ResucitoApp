package com.resucito.app.screen.search.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.data.Stage

@Composable
fun ItemSearchSong(page: String, title: String, subtitle: String, favorite: Boolean, stage: Stage, onClick: () -> Unit) {

    val (_, backgroundColor) = colorStage(stage)

    ListItem(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        headlineContent = { Text(title) },
        supportingContent = {
            Text(subtitle)
        },
        leadingContent = {
            Text(
                text = page,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(backgroundColor)
                    .padding(8.dp),
            )
        },
        trailingContent = {
            Icon(
                imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Seleccionar como favorito",
            )
        }
    )
}

@Composable
@Preview
fun ItemSearchSongPreview() {

    ListItem(
        modifier = Modifier.fillMaxWidth(),
        headlineContent = { Text("Three line list item") },
        supportingContent = {
            Text("asd")
        },
        leadingContent = {
            Text(text = "123")
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Localized description",
            )
        }
    )
}

internal data class ColorStage (val textColor: Color, val backgroundColor: Color)

@Composable
internal fun colorStage (stage: Stage): ColorStage {

    return when (stage) {
        Stage.PRECATECHUMENATE -> ColorStage(MaterialTheme.colorScheme.surfaceVariant, colorResource(id = R.color.precatechumenateContainer))
        else -> ColorStage(MaterialTheme.colorScheme.surfaceVariant, colorResource(id = R.color.liturgyContainer))
    }
}