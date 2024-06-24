package com.resucito.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import com.resucito.app.db.dao.SongDao
import com.resucito.app.db.model.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchSongProvider(
    private val dao: SongDao
) : ViewModel() {

    var songs = mutableListOf<SongEntity>()
        private set

    var isLoading by mutableStateOf(false)
        private set

    var filters by mutableStateOf(SearchFilters(null,null))
    private set
    fun searchSong(query: String) {
        viewModelScope.launch {
            isLoading = true
            val searchSong: List<SongEntity> = if (query.isBlank()) {
                withContext(Dispatchers.IO) {
                    dao.getAllSongs()
                }
            } else {
                withContext(Dispatchers.IO) {
                    dao.searchSongs("*$query*")
                }
            }



            val filterSongs = filterSongs(searchSong, filters)

            songs.clear()
            songs.addAll(filterSongs)
            isLoading = false
        }
    }

    fun updateSearchFilters (searchFilters: SearchFilters) {
        filters = searchFilters
    }

    private fun filterSongs (songs: List<SongEntity>, filters: SearchFilters): List<SongEntity> {
        val (stage, category) = filters
        return songs.filter { song ->
            if (stage !== null) {
                return@filter song.stage == filters.stage
            } else if (category !== null) {
                return@filter song.categories.any{it == category}
            }else true
        }
    }

}
data class SearchFilters (val stage: Stage?, val category: Category?)

