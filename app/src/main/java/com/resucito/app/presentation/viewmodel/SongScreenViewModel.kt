package com.resucito.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.song.GetSongUseCase
import com.resucito.app.domain.usecase.song.UpdateFavoriteSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


data class SongState(
    val song: Song? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val snackbarText: String? = null
)


@HiltViewModel
class SongScreenViewModel @Inject constructor(
    private val getSongUseCase: GetSongUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SongState())
    val state: StateFlow<SongState> = _state

    fun findSongById(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, isError = false
                )
            }

            try {
                val newSong = withContext(Dispatchers.IO) {
                    getSongUseCase.execute(id.trim())
                }
                _state.update {
                    it.copy(
                        song = newSong, isLoading = false
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isError = true, isLoading = false
                    )
                }
            }
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
                })
        }
    }

}