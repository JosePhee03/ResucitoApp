package com.resucito.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.db.dao.SongDao
import com.resucito.app.db.model.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongProvider(private val dao: SongDao) : ViewModel() {

    var song by mutableStateOf<SongEntity?>(null)
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
                    dao.findSongById(id.trim())
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