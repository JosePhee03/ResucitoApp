package com.resucito.app.presentation.ui.screen.songbook

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.resucito.app.R
import com.resucito.app.presentation.ui.screen.songbook.components.TopAppBarSongBook
import com.resucito.app.presentation.ui.screen.songbook.components.ListSongBookContent
import com.resucito.app.presentation.viewmodel.SongBookScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongBookScreen(
    vm: SongBookScreenViewModel, onBackNavigate: () -> Unit, navigateToSong: (String) -> Unit
) {

    val uiState by vm.state.collectAsState()
    val getSongBook = vm::getAllFavoriteSongs
    val onChangeFavorite = vm::switchFavoriteSong
    val songs = uiState.songs

    LaunchedEffect(Unit) {
        getSongBook()
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarSongBook(
                scrollBehavior = scrollBehavior,
                onBackNavigate = onBackNavigate,
                songBookName = stringResource(R.string.favorites),
                totalSongs = songs.size
            )
        }) { paddingValues ->

        ListSongBookContent(
            modifier = Modifier.padding(paddingValues),
            songs = songs,
            onClickItem = navigateToSong,
            onChangeFavorite = onChangeFavorite,
        )
    }

}