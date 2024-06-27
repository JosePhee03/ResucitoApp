package com.resucito.app.presentation.ui.screen.album

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.resucito.app.presentation.ui.screen.album.components.ItemAlbumSongs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen () {
    
    Column {
        TopAppBar(title = { Text("Albumes") })
        LazyColumn {
            item {
                ItemAlbumSongs("Favoritos", 100)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlbumScreenPreview () {

    AlbumScreen()
}