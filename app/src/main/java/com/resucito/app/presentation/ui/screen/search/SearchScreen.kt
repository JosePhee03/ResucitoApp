package com.resucito.app.presentation.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.screen.search.componets.ItemSearchSong
import com.resucito.app.presentation.ui.screen.search.componets.SearchBox
import com.resucito.app.presentation.viewmodel.SearchFilters
import com.resucito.app.util.colorStage
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    navController: NavHostController,
    stageId: String?,
    categoryId: String?,
    songs: List<Song>,
    filters: SearchFilters,
    isLoading: Boolean,
    searchSong: (String) -> Unit,
    setSearchFilter: (Stage?, Category?) -> Unit,
    switchFavoriteSong: (String, Boolean) -> Unit,
    toastMessage: String?,
    snackBarController: SnackbarHostState,

    ) {

    val scrollState = rememberLazyListState()


    var text by rememberSaveable {
        mutableStateOf("")
    }

    val initialText by remember {
        mutableStateOf(text)
    }

    LaunchedEffect(Unit) {
        setSearchFilter(stageId?.let { Stage.valueOf(it) },
            categoryId?.let { Category.valueOf(it) })
    }

    LaunchedEffect(Unit) {
        if (text !== initialText) {
            searchSong(text)
        }


    }

    LaunchedEffect(text, filters) {
        delay(400)
        searchSong(text)
    }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            snackBarController.showSnackbar(
                message = toastMessage,
                actionLabel = "Deshacer",
                duration = SnackbarDuration.Short
            )
        }
    }


    Column {
        SearchBox(text) {
            text = it
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
                                    InputChip(
                                        selected = false,
                                        onClick = {
                                            setSearchFilter(null, null)
                                        },
                                        label = { Text(it.name, fontWeight = FontWeight.Medium) },
                                        colors = InputChipDefaults.inputChipColors(
                                            containerColor = colorStage(it).backgroundColor
                                        ),
                                        trailingIcon = {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Localized description",
                                                Modifier.size(InputChipDefaults.AvatarSize)
                                            )
                                        },
                                        border = null,
                                        elevation = InputChipDefaults.inputChipElevation(2.dp)
                                    )
                                }
                            }
                            filters.category?.let {
                                item {
                                    InputChip(
                                        selected = false,
                                        onClick = {
                                            setSearchFilter(null, null)
                                        },
                                        label = { Text(it.name, fontWeight = FontWeight.Medium) },
                                        colors = InputChipDefaults.inputChipColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                                        ),
                                        trailingIcon = {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Localized description",
                                                Modifier.size(InputChipDefaults.AvatarSize)
                                            )
                                        },
                                        elevation = InputChipDefaults.inputChipElevation(2.dp)
                                    )
                                }
                            }

                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${songs.size} Cantos",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                    items(songs.size, key = { songs[it].id }) { item ->
                        val songItem = songs[item]
                        ItemSearchSong(
                            songItem.page.toString(),
                            songItem.title,
                            songItem.subtitle,
                            songItem.favorite,
                            songItem.stage,
                            onChangeFavorite = {
                                switchFavoriteSong(songItem.id, it)
                            },
                            onClickItem = {
                                navController.navigate(
                                    com.resucito.app.presentation.ui.navigation.Song(
                                        songItem.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

}