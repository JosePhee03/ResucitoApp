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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val songs: List<Song> = emptyList(),
    val searchParams: SearchParams = SearchParams("", null, null),
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

    private var previousSearchParams: SearchParams? = null
    private var collectionJob: Job? = null

    fun searchSong(searchParams: SearchParams) {

        previousSearchParams = when (previousSearchParams) {
            null -> searchParams
            searchParams -> return
            else -> searchParams
        }

        val query = searchParams.query

        collectionJob?.cancel()

        collectionJob = viewModelScope.launch {

            _state.update {
                it.copy(
                    isError = false,
                    isLoading = true,
                )
            }

            val searchSongs = if (query.isEmpty()) {
                getSongsUseCase.execute()
            } else {
                searchSongsUseCase.execute("*$query*")
            }

            searchSongs.fold(
                onSuccess = { songs ->
                    songs.collect { songsCollect ->
                        val filterSongs = filterSongs(songsCollect, searchParams)
                        _state.update {
                            it.copy(
                                songs = filterSongs, isLoading = false
                            )
                        }
                    }

                },
                onFailure = {
                    _state.update {
                        it.copy(
                            isError = true, isLoading = false
                        )
                    }
                }
            )


        }
    }


    fun setFilters(stage: Stage?, category: Category?) {
        viewModelScope.launch {
            val searchParams = _state.value.searchParams
            previousSearchParams = searchParams
            val newSearchParams = searchParams.copy(stage = stage, category = category)
            _state.update {
                it.copy(
                    searchParams = newSearchParams
                )
            }
        }
    }

    fun setFiltersById(stageId: String?, categoryId: String?) {
        viewModelScope.launch {
            val stage = stageId?.let { Stage.valueOf(it) }
            val category = categoryId?.let { Category.valueOf(it) }
            val searchParams = _state.value.searchParams
            previousSearchParams = searchParams
            val newSearchParams = searchParams.copy(stage = stage, category = category)
            _state.update {
                it.copy(
                    searchParams = newSearchParams
                )
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch {
            val searchParams = _state.value.searchParams
            previousSearchParams = searchParams
            val newSearchParams = searchParams.copy(query = query)
            _state.update {
                it.copy(
                    searchParams = newSearchParams
                )
            }
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

    private fun filterSongs(songs: List<Song>, searchParams: SearchParams): List<Song> {
        val (_, stage, category) = searchParams
        return songs.filter { song ->
            if (stage !== null) {
                return@filter song.stage == stage
            } else if (category !== null) {
                return@filter song.categories.any { it == category }
            } else true
        }
    }

}

data class SearchParams(val query: String = "", val stage: Stage?, val category: Category?)
