package com.resucito.app.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.GetSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SongScreenViewModel @Inject constructor(
    private val getSongUseCase: GetSongUseCase

): ViewModel() {

    var song by mutableStateOf<Song?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
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

}