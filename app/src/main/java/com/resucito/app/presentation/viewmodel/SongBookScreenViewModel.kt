package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.GetAllFavoriteSongsUseCase
import com.resucito.app.domain.usecase.UpdateFavoriteSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SongBookUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val songs: List<Song> = emptyList(),
    val snackBarText: String? = null
)

@HiltViewModel
class SongBookScreenViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SongBookUiState())
        private set

    fun getAllFavoriteSongs() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isError = false)
            val resultFavoriteSongs = getAllFavoriteSongsUseCase.execute()
            resultFavoriteSongs.fold(
                onSuccess = {
                    uiState = uiState.copy(
                        isLoading = false,
                        songs = it
                    )
                },
                onFailure = {
                    uiState = uiState.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            )
        }
    }

    fun switchFavoriteSong(songId: String, favorite: Boolean) {
        viewModelScope.launch {
            uiState = uiState.copy(snackBarText = null)
            delay(400)
            val updatedSongs = uiState.songs.filterNot { it.id == songId }
            uiState = uiState.copy(songs = updatedSongs)
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {
                    uiState = uiState.copy(
                        snackBarText = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""
                    )
                },
                onFailure = {
                    uiState = uiState.copy(
                        snackBarText = it.message ?: "Error favorito"
                    )
                }
            )
        }
    }

}