package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.GetSongUseCase
import com.resucito.app.domain.usecase.UpdateFavoriteSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SongScreenViewModel @Inject constructor(
    private val getSongUseCase: GetSongUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
): ViewModel() {

    var song by mutableStateOf<Song?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
        private set

    var snackBarText by mutableStateOf<String?>(null)
        private set

    fun findSongById(id: String) {
        viewModelScope.launch {
            isLoading = true
            isError = false
            try {
                val newSong = withContext(Dispatchers.IO) {
                    getSongUseCase.execute(id.trim())
                }
                song = newSong
            } catch (e: Exception) {
                isError = true
            } finally {
                isLoading = false
            }
        }
    }

    fun switchFavoriteSong (songId: String, favorite: Boolean) {
        viewModelScope.launch {
            snackBarText = null
            delay(400)
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {snackBarText = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""},
                onFailure = { snackBarText = it.message ?: "Error favorito"}
            )
        }
    }

}