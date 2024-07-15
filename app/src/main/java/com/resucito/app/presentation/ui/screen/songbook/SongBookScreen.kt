package com.resucito.app.presentation.ui.screen.songbook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.resucito.app.R
import com.resucito.app.presentation.ui.components.EmptyLayout
import com.resucito.app.presentation.ui.components.ErrorLayout
import com.resucito.app.presentation.ui.screen.songbook.components.ListSongBookContent
import com.resucito.app.presentation.ui.screen.songbook.components.TopBarSongBook
import com.resucito.app.presentation.viewmodel.SongBookScreenViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.sample

@OptIn(FlowPreview::class)
@Composable
fun SongBookScreen(
    vm: SongBookScreenViewModel,
    onBackNavigate: () -> Unit,
    navigateToSong: (String) -> Unit
) {

    val uiState by vm.state.collectAsState()
    val getSongBook = vm::getAllFavoriteSongs
    val onChangeFavorite = vm::switchFavoriteSong

    val lazyListState = rememberLazyListState()

    var isExpand by remember { mutableStateOf(true) }

    LaunchedEffect(lazyListState, uiState.songs) {
        if (uiState.songs.isNotEmpty()) {
            snapshotFlow { lazyListState.canScrollBackward to lazyListState.canScrollForward }
                .sample(400)
                .collect { (canScrollUp, canScrollBack) ->
                    isExpand = !canScrollUp && canScrollBack
                }
        }
    }


    LaunchedEffect(Unit) {
        getSongBook()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBarSongBook(
            isExpand = isExpand,
            onBackNavigate = { onBackNavigate() },
            onExpand = { isExpand = true })
        if (uiState.isError && !uiState.isLoading) {
            ErrorLayout(
                iconPainter = painterResource(R.drawable.ic_music_off),
                iconDescription = "Icono de error con una nota musical en rojo",
                text = "Error al cargar la lista",
                buttonText = "Recargar",
                onReload = { getSongBook() }
            )
        } else {
            if (uiState.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else if (uiState.songs.isEmpty()) {
                EmptyLayout(
                    text = "La lista esta vacia",
                    buttonText = "Recargar",
                    onReset = { getSongBook() })
            } else {
                ListSongBookContent(
                    songs = uiState.songs,
                    state = lazyListState,
                    onClickItem = { navigateToSong(it) },
                    onChangeFavorite = { songId, isFavorite ->
                        onChangeFavorite(
                            songId,
                            isFavorite
                        )
                    }
                )
            }
        }
    }

}