package com.resucito.app.presentation.viewmodel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class SearchState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val songs: List<Song> = emptyList(),
    val query: String = "",
    val filters: SearchFilters = SearchFilters(Stage.LITURGY, null),
    val snackBarText: String? = null
)

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val searchSongsUseCase: SearchSongsUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()


    fun searchSong(query: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    query = query
                )
            }
            val searchSong: List<Song> = if (query.isBlank()) {
                withContext(Dispatchers.IO) {
                    getSongsUseCase.execute()
                }
            } else {
                withContext(Dispatchers.IO) {
                    searchSongsUseCase.execute(query)
                }
            }

            val filterSongs = filterSongs(searchSong, state.value.filters)

            _state.update {
                it.copy(
                    songs = filterSongs, isLoading = false
                )
            }
        }
    }


    fun setSearchFilters(stage: Stage?, category: Category?) {
        _state.update {
            it.copy(
                filters = SearchFilters(stage, category)
            )
        }
    }

    fun switchFavoriteSong(songId: String, favorite: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    snackBarText = null
                )
            }
            delay(200)
            val songs = state.value.songs
            val songIndex = songs.indexOfFirst { it.id == songId }
            songs[songIndex].favorite = favorite
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            snackBarText = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""
                        )
                    }
                }, onFailure = { exception ->
                    _state.update {
                        it.copy(
                            snackBarText = exception.message ?: "Error favorito"
                        )
                    }
                })
        }
    }

    private fun filterSongs(songs: List<Song>, filters: SearchFilters): List<Song> {
        val (stage, category) = filters
        return songs.filter { song ->
            if (stage !== null) {
                return@filter song.stage == filters.stage
            } else if (category !== null) {
                return@filter song.categories.any { it == category }
            } else true
        }
    }

}

data class SearchFilters(val stage: Stage?, val category: Category?)
