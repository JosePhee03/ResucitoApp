package com.resucito.app.screen.search

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.resucito.app.navigation.Song
import com.resucito.app.screen.search.componets.ItemSearchSong
import com.resucito.app.screen.search.componets.SearchBox
import com.resucito.app.viewmodel.SearchSongProvider
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun SearchScreen(navController: NavHostController, songsProvider: SearchSongProvider) {

    Column {
        SearchBox(songsProvider.songs.isEmpty()) {
            songsProvider.searchSong(it)
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            if (songsProvider.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(songsProvider.songs.size) { item ->
                        val (_, id, page, title, subtitle, _, stage) = songsProvider.songs[item]
                        ItemSearchSong(page.toString(), title, subtitle, false, stage) {
                            navController.navigate(Song(id))
                        }
                    }
                }
            }
        }
    }

}

fun readJsonFromAssets(context: Context, fileName: String): String {
    val assetManager = context.assets
    val inputStream = assetManager.open(fileName)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    return bufferedReader.use { it.readText() }
}