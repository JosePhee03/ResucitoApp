package com.resucito.app.presentation.ui.screen.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resucito.app.R
import com.resucito.app.presentation.ui.components.SuggestionChipCategory
import com.resucito.app.presentation.ui.components.SuggestionChipStage
import com.resucito.app.presentation.ui.screen.song.components.LyricContainer
import com.resucito.app.presentation.ui.screen.song.components.TopAppBarSong
import com.resucito.app.presentation.viewmodel.SongScreenViewModel

@Composable
fun SongScreen(
    onBackNavigate: () -> Unit,
    songId: String,
    snackBarController: SnackbarHostState,
    vm: SongScreenViewModel,
    navigateToSearch: (String?, String?) -> Unit
) {

    val uiState by vm.state.collectAsState()
    val isLoading = uiState.isLoading
    val isError = uiState.isError
    val song = uiState.song
    val findSong = vm::findSongById
    val snackbarText = uiState.snackbarText
    val onChangeFavorite = vm::switchFavoriteSong

    LaunchedEffect(songId) {
        findSong(songId)
    }

    val undoString = stringResource(R.string.undo)

    LaunchedEffect(snackbarText) {
        if (snackbarText != null) {
            snackBarController.showSnackbar(
                message = snackbarText,
                actionLabel = undoString,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        topBar = {
            if (!isLoading && song != null) {
                TopAppBarSong(
                    favorite = song.favorite,
                    onChangeFavorite = { onChangeFavorite(songId, it) },
                    onBackNavigate = {
                        onBackNavigate()
                    })
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else if (isError || song == null) {
                Text(stringResource(R.string.song_not_found))
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    item {
                        SuggestionChipStage(
                            stage = song.stage,
                            onClick = { navigateToSearch(it.name, null) })
                    }
                    items(song.categories) {
                        SuggestionChipCategory(
                            category = it,
                            onClick = { category -> navigateToSearch(null, category.name) })
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(16.dp)
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = song.page.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(24.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.capo),
                        fontWeight = FontWeight.Bold
                    )
                    Text(

                        text = " ${song.capo}° ",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(

                        text = stringResource(R.string.fret),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(

                        text = stringResource(R.string.chords),
                        fontWeight = FontWeight.Bold
                    )
                    Text(

                        text = " ${song.chords}",
                        letterSpacing = 2.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }




                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = song.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = song.subtitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(24.dp)
                )

                song.lyric.split("\n").forEach {
                    LyricContainer(it)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(24.dp)
                )
            }
        }
    }


}