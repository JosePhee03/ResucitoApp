package com.resucito.app.presentation.ui.screen.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.resucito.app.R
import com.resucito.app.presentation.ui.screen.library.components.ItemLibrary
import com.resucito.app.presentation.viewmodel.LibraryScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    vm: LibraryScreenViewModel,
    navigateToSongbook: () -> Unit
) {

    val uiState by vm.state.collectAsState()
    val isLoading = remember { uiState.isLoading }
    val isError = remember { uiState.isError }
    val songbooks = remember { uiState.songbooks }
    val getSongBooks: () -> Unit = remember { { vm.getAllFavoriteSongs() } }
    val navigate: () -> Unit = remember { { navigateToSongbook() } }

    LaunchedEffect(Unit) {
        getSongBooks()
    }

    Column {
        TopAppBar(
            title = { Text(stringResource(R.string.lists)) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        )
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Column {
            ItemLibrary(
                name = stringResource(R.string.favorites),
                count = songbooks.size,
                enabled = { !isLoading }
            ) {
                navigate()
            }
        }
        if (isError) {
            Text(stringResource(R.string.libraries_not_found))
        }
    }
}