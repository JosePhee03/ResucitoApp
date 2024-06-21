package com.resucito.app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.db.dao.SongDao
import com.resucito.app.db.model.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchSongProvider(
    private val dao: SongDao
) : ViewModel() {

    private val _songs = mutableListOf<SongEntity>()
    val songs get() = _songs.toList()

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    fun searchSong(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val searchSong: List<SongEntity> = if (query.isBlank()) {
                withContext(Dispatchers.IO) {
                    dao.getAllSongs()
                }
            } else {
                withContext(Dispatchers.IO) {
                    dao.searchSongs("*$query*")
                }
            }

            _songs.clear()
            _songs.addAll(searchSong)
            _isLoading.value = false


        }
    }

}