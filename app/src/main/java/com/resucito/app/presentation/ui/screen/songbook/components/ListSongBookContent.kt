package com.resucito.app.presentation.ui.screen.songbook.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.resucito.app.domain.model.Song
import com.resucito.app.presentation.ui.screen.search.components.ItemSearchSong

@Composable
fun ListSongBookContent(
    songs: List<Song>,
    onClickItem: (String) -> Unit,
    onChangeFavorite: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier,
    ) {
        items(songs, key = { it.id }) {
            var visible by remember { mutableStateOf(true) }

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(durationMillis = 200)),
                exit = fadeOut(animationSpec = tween(durationMillis = 200))
            ) {
                ItemSearchSong(
                    page = it.page.toString(),
                    title = it.title,
                    subtitle = it.subtitle,
                    favorite = it.favorite,
                    stage = it.stage,
                    onClickItem = { onClickItem(it.id) },
                    onChangeFavorite = { isFavorite ->
                        visible = false
                        onChangeFavorite(it.id, isFavorite)
                    })
            }
        }
    }
}