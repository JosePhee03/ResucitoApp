package com.resucito.app.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.resucito.app.R

@Composable
fun FavoriteIconButton (favorite: Boolean, onChangeFavorite: (Boolean) -> Unit) {
    var favoriteState by remember { mutableStateOf(favorite) }

    IconButton(onClick = {
        val newFavorite = !favoriteState
        favoriteState = newFavorite
        onChangeFavorite(newFavorite)
    }) {
        Icon(
            painter = painterResource(if (favoriteState) R.drawable.ic_favorite_filled else R.drawable.ic_favorite),
            contentDescription = stringResource(if (favoriteState) R.string.select_as_favorite else R.string.deselect_as_favorite),
        )
    }
}