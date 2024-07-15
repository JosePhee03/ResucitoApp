package com.resucito.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.GetAllFavoriteSongsUseCase
import com.resucito.app.domain.usecase.UpdateFavoriteSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SongBookState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val songs: List<Song> = emptyList(),
    val snackbarText: String? = null
)

@HiltViewModel
class SongBookScreenViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SongBookState())
    val state: StateFlow<SongBookState> = _state

    fun getAllFavoriteSongs() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, isError = false)
            }
            val resultFavoriteSongs = getAllFavoriteSongsUseCase.execute()
            resultFavoriteSongs.fold(
                onSuccess = { songs ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            songs = songs
                        )
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            )
        }
    }

    fun switchFavoriteSong(songId: String, favorite: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    snackbarText = null
                )
            }
            delay(400)
            _state.update {
                it.copy(
                    snackbarText = null
                )
            }
            val updatedSongs = _state.value.songs.filterNot { it.id == songId }
            _state.update {
                it.copy(songs = updatedSongs)
            }
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            snackbarText = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            snackbarText = exception.message ?: "Error favorito"
                        )
                    }
                }
            )
        }
    }

}