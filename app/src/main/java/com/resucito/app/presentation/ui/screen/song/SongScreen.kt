package com.resucito.app.presentation.ui.screen.song

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.resucito.app.R
import com.resucito.app.presentation.ui.screen.song.components.SongContent
import com.resucito.app.presentation.ui.screen.song.components.TopAppBarSong
import com.resucito.app.presentation.viewmodel.SongScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongScreen(
    onBackNavigate: () -> Unit,
    songId: String,
    vm: SongScreenViewModel = hiltViewModel(),
    navigateToSearch: (String?, String?) -> Unit
) {

    val uiState by vm.state.collectAsState()
    val isLoading = uiState.isLoading
    val isError = uiState.isError
    val song = uiState.song
    val findSong = vm::findSongById
    val onChangeFavorite = vm::switchFavoriteSong
    val snackbarHostState = vm.snackbarHostState

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(songId) {
        findSong(songId)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                snackbarHostState.currentSnackbarData?.let { Snackbar(snackbarData = it) }
            }
        },
        topBar = {
            TopAppBarSong(
                scrollBehavior = scrollBehavior,
                favorite = song?.favorite,
                onChangeFavorite = {
                    onChangeFavorite(songId, it)
                },
                onBackNavigate = {
                    onBackNavigate()
                })

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(paddingValues)
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else if (isError || song == null) {
                Text(stringResource(R.string.song_not_found))
            } else {
                SongContent(song = song, navigateToSearch = navigateToSearch)
            }
        }
    }

}