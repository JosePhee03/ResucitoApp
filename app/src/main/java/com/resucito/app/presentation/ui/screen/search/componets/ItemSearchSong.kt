package com.resucito.app.presentation.ui.screen.search.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Stage
import com.resucito.app.util.colorStage

@Composable
fun ItemSearchSong(
    page: String,
    title: String,
    subtitle: String,
    favorite: Boolean,
    stage: Stage,
    onClickItem: () -> Unit,
    onChangeFavorite: (Boolean) -> Unit
) {

    var favoriteState by remember{ mutableStateOf(favorite) }
    val (_, backgroundColor) = colorStage(stage)

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem() },
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
            IconButton(onClick = {
                val newFavorite = !favoriteState
                favoriteState = newFavorite
                onChangeFavorite(newFavorite)
            }) {
                Icon(
                    painter = painterResource(if (favoriteState) R.drawable.ic_favorite_filled else R.drawable.ic_favorite),
                    contentDescription = if (favoriteState) "Seleccionar como favorito" else "Deseleccionar como favorito",
                )
            }
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

