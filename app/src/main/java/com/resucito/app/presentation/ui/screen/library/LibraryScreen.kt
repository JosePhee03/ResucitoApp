package com.resucito.app.presentation.ui.screen.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.resucito.app.R
import com.resucito.app.domain.model.Song
import com.resucito.app.presentation.ui.screen.library.components.ItemLibrary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    isLoading: Boolean,
    isError: Boolean,
    getAlbums: () -> Unit,
    favoriteSongs: List<Song>,
    navigateToSongBook: () -> Unit
) {

    LaunchedEffect(Unit) {
        getAlbums()
    }

    Column {
        TopAppBar(
            title = { Text(stringResource(R.string.lists)) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        )
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn {
            item {
                ItemLibrary(
                    name = stringResource(R.string.favorites),
                    count = favoriteSongs.size,
                    enabled = !isLoading) {
                    navigateToSongBook()
                }
            }
        }
        if (isError) {
            Text(stringResource(R.string.libraries_not_found))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlbumScreenPreview() {

    LibraryScreen(false, false, { }, emptyList()) { }
}