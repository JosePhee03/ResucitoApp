package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Album
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.GetAllFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumScreenViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase
) : ViewModel() {

    var isError by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _favoriteSongs = mutableStateListOf<Song>()
    val favoriteSongs get() = _favoriteSongs.toList()

    fun getAllAlbums() {
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
}