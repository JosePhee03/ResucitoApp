package com.resucito.app.presentation.ui.screen.song.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.resucito.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSong(
    favorite: Boolean,
    onChangeFavorite: (Boolean) -> Unit,
    onBackNavigate: () -> Unit
) {

    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var favoriteState by remember {
        mutableStateOf(favorite)
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text("Canción")
        },
        navigationIcon = {
            IconButton(onClick = { onBackNavigate() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Fecha volver hacia atrás"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                val newFavorite = !favoriteState
                favoriteState = newFavorite
                onChangeFavorite(newFavorite)
            }) {
                Icon(
                    painter = painterResource(if (favoriteState) R.drawable.ic_favorite_filled else R.drawable.ic_favorite),
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}