package com.resucito.app.presentation.ui.screen.album

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.resucito.app.domain.model.Song
import com.resucito.app.presentation.ui.screen.album.components.ItemAlbumSongs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(
    isLoading: Boolean,
    isError: Boolean,
    getAlbums: () -> Unit,
    favoriteSongs: List<Song>
) {

    LaunchedEffect(Unit) {
        getAlbums()
    }

    Column {
        TopAppBar(title = { Text("Albumes") })
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        if (isError) {
            Text("ERRORORORORO")
        }
        LazyColumn {
            item {
                ItemAlbumSongs("Favoritos", favoriteSongs.size)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlbumScreenPreview() {

    AlbumScreen(false, false, { }, emptyList())
}