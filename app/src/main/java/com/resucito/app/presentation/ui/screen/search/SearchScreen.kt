package com.resucito.app.presentation.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.components.Chip
import com.resucito.app.presentation.ui.screen.search.components.ItemSearchSong
import com.resucito.app.presentation.ui.screen.search.components.SearchBox
import com.resucito.app.presentation.ui.theme.ThemeApp
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
import com.resucito.app.util.GetStringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    navigateToSong: (String) -> Unit,
    stageId: String?,
    categoryId: String?,
    snackBarController: SnackbarHostState,
    vm: SearchScreenViewModel
) {

    var queryRemember by rememberSaveable {
        mutableStateOf("")
    }

    val uiState by vm.state.collectAsState()

    val songs = uiState.songs
    val filters = uiState.filters
    val isLoading = uiState.isLoading
    val query = uiState.query
    val snackBarText = uiState.snackBarText
    val setSearchFilters = vm::setSearchFilters
    val switchFavoriteSong = vm::switchFavoriteSong
    val searchSong = vm::searchSong

    val scrollState = rememberLazyListState()

    val undoString = stringResource(R.string.undo)

    LaunchedEffect(Unit) {
        println("stageId: $stageId = categoryId: $categoryId // $filters")
        setSearchFilters(stageId?.let { Stage.valueOf(it) },
            categoryId?.let { Category.valueOf(it) })
        searchSong(query)
    }

    LaunchedEffect(snackBarText) {
        if (snackBarText != null) {
            snackBarController.showSnackbar(
                message = snackBarText, actionLabel = undoString, duration = SnackbarDuration.Short
            )
        }
    }

    Column {
        SearchBox(query) {
            searchSong(it)
            queryRemember = it
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = scrollState) {
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            filters.stage?.let {
                                item {
                                    Chip(
                                        text = GetStringResource.getLocalizedName(it),
                                        icon = true,
                                        stage = it
                                    ) {
                                        setSearchFilters(null, null)
                                    }
                                }
                            }
                            filters.category?.let {
                                item {
                                    Chip(
                                        text = GetStringResource.getLocalizedName(it),
                                        icon = true,
                                        stage = null
                                    ) {
                                        setSearchFilters(null, null)
                                    }
                                }
                            }

                        }
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