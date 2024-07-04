package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

@HiltViewModel
class SongBookScreenViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
): ViewModel() {

    var isError by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _favoriteSongs = mutableStateListOf<Song>()
    val favoriteSongs get() = _favoriteSongs.toList()

    var snackBarText by mutableStateOf<String?>(null)

    fun getAllFavoriteSongs() {
        viewModelScope.launch {
            isLoading = true
            isError = false
            val resultFavoriteSongs = getAllFavoriteSongsUseCase.execute()
            resultFavoriteSongs.fold(
                onSuccess = {
                    _favoriteSongs.clear()
                    _favoriteSongs.addAll(it)
                },
                onFailure = { isError = true }
            )

            isLoading = false
        }
    }

    fun switchFavoriteSong (songId: String, favorite: Boolean) {
        viewModelScope.launch {
            snackBarText = null
            delay(400)
            _favoriteSongs.removeIf { it.id == songId }
            updateFavoriteSongUseCase.execute(songId, favorite).fold(
                onSuccess = {snackBarText = if (favorite) "Canto Guardado en el album de \"Favoritos\"" else "Se quito el canto del album de \"Favoritos\""},
                onFailure = { snackBarText = it.message ?: "Error favorito"}
            )
        }
    }

}