package com.resucito.app.presentation.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.presentation.ui.components.InputChipCategory
import com.resucito.app.presentation.ui.components.InputChipStage
import com.resucito.app.presentation.ui.screen.search.components.ItemSearchSong
import com.resucito.app.presentation.ui.screen.search.components.SearchBox
import com.resucito.app.presentation.ui.theme.ThemeApp
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    navigateToSong: (String) -> Unit,
    snackBarController: SnackbarHostState,
    vm: SearchScreenViewModel
) {

    val uiState by vm.state.collectAsState()

    val songs = uiState.songs
    val searchParams = uiState.searchParams
    val isLoading = uiState.isLoading
    val query = searchParams.query
    val snackBarText = uiState.snackBarText
    val setFilters = vm::setFilters
    val setQuery = vm::setQuery
    val switchFavoriteSong = vm::switchFavoriteSong
    val searchSong = vm::searchSong

    val scrollState = rememberLazyListState()

    val undoString = stringResource(R.string.undo)

    LaunchedEffect(searchParams) {
        searchSong(searchParams)
    }

    LaunchedEffect(snackBarText) {
        if (snackBarText != null) {
            snackBarController.showSnackbar(
                message = snackBarText, actionLabel = undoString, duration = SnackbarDuration.Short
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(query) {
            setQuery(it)
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            searchParams.stage?.let {
                item {
                    InputChipStage(stage = it, onClick = {
                        setFilters(null, null)
                    })
                }
            }
            searchParams.category?.let {
                item {
                    InputChipCategory(category = it, onClick = {
                        setFilters(null, null)
                    })
                }
            }

        }

        Column(modifier = Modifier.fillMaxWidth()) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = scrollState) {
                    item {

                    }
                    stickyHeader {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${songs.size} ${stringResource(R.string.songs)}",
                                style = MaterialTheme.typography.labelLarge,
                                color = ThemeApp.color.grey600
                            )
                        }
                    }
                    items(songs.size, key = { songs[it].id }) { item ->
                        val songItem = songs[item]
                        ItemSearchSong(songItem.page.toString(),
                            songItem.title,
                            songItem.subtitle,
                            songItem.favorite,
                            songItem.stage,
                            onChangeFavorite = {
                                switchFavoriteSong(songItem.id, it)
                            },
                            onClickItem = {
                                navigateToSong(songItem.id)
                            })
                    }
                }
            }
        }
    }

}