package com.resucito.app.presentation.ui.screen.songbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.resucito.app.domain.model.Song
import com.resucito.app.presentation.ui.screen.search.components.ItemSearchSong
import com.resucito.app.presentation.ui.screen.songbook.components.TopBarSongBook

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SongBookScreen(
    onBackNavigate: () -> Unit,
    getAllFavoriteSongs: () -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    favoriteSongs: List<Song>
) {

    LaunchedEffect(Unit) {
        getAllFavoriteSongs()
    }

    val scrollState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset > 0 || scrollState.firstVisibleItemIndex > 1 }
    }

    Column {
        TopBarSongBook(isScrolling)

        LazyColumn(state = scrollState) {
            items(favoriteSongs) {
                ItemSearchSong(
                    page = it.page.toString(),
                    title = it.title,
                    subtitle = it.subtitle,
                    favorite = it.favorite,
                    stage = it.stage,
                    onClickItem = { /*TODO*/ }) {

                }
            }
        }
    }


}