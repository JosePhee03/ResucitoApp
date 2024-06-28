package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage
import com.resucito.app.domain.usecase.GetSongsUseCase
import com.resucito.app.domain.usecase.SearchSongsUseCase
import com.resucito.app.domain.usecase.UpdateFavoriteSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val searchSongsUseCase: SearchSongsUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
): ViewModel() {

    private var _songs = mutableStateListOf<Song>()

    val songs: List<Song> get() = _songs.toList()

    var isLoading by mutableStateOf(false)
        private set

    var filters by mutableStateOf(SearchFilters(null,null))
        private set

    var toastMessage by mutableStateOf<String?>(null)


    fun searchSong(query: String) {
        viewModelScope.launch {
            isLoading = true
            val searchSong: List<Song> = if (query.isBlank()) {
                withContext(Dispatchers.IO) {
                    getSongsUseCase.execute()
                }
            } else {
                withContext(Dispatchers.IO) {
                    searchSongsUseCase.execute(query)
                }
            }

            val filterSongs = filterSongs(searchSong, filters)

            _songs.clear()
            _songs.addAll(filterSongs)
            isLoading = false
        }
    }

    fun setSearchFilters (stage: Stage?, category: Category?) {
        filters = SearchFilters(stage, category)
    }

    fun switchFavoriteSong (songId: String, favorite: Boolean) {
        viewModelScope.launch {
            toastMessage = null
            delay(400)
            val songIndex = _songs.indexOfFirst { it.id == songId }
            _songs[songIndex].favorite = favorite
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {toastMessage = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""},
                onFailure = { toastMessage = it.message ?: "Error favorito"}
            )
        }
    }

    private fun filterSongs (songs: List<Song>, filters: SearchFilters): List<Song> {
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
