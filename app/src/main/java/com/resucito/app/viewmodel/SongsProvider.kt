package com.resucito.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.resucito.app.data.Category
import com.resucito.app.data.Song
import com.resucito.app.data.Stage
import com.resucito.app.db.dao.SongDao
import com.resucito.app.db.model.SongEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongsProvider(
    private val dao: SongDao
) : ViewModel() {

    private val _songs = mutableListOf<SongEntity>()
    val songs get() = _songs.toList()

    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value

    private val _query = mutableStateOf("")
    val query get() = _query.value

    suspend fun countSongsByStage (stage: Stage): Int{
        return dao.countSongsByStage(stage)
    }

    suspend fun countSongsByCategory (category: Category): Int{
        return dao.countSongsByCategory(listOf(category))
    }

    private fun getAllSongs() {
        viewModelScope.launch {
            val newSongs = withContext(Dispatchers.IO) {
                dao.getAllSongs()
            }
            val setSongs = mutableSetOf<String>()
            _songs.addAll(newSongs)
            newSongs.forEach {
                setSongs.addAll(it.chords)
            }
        }
    }

    fun insertFromJson(jsonString: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val songsList = withContext(Dispatchers.IO) {
                parseJson(jsonString)
            }
            val songEntities = songsList.mapIndexed { index, it ->
                SongEntity(
                    index,
                    it.id,
                    it.page,
                    it.title,
                    it.subtitle,
                    it.capo,
                    it.stage,
                    it.categories,
                    it.lyric,
                    it.chords,
                    it.tone,
                    it.scale,
                    false
                )
            }
            withContext(Dispatchers.IO) {

                dao.insertAll(songEntities)
                songEntities.map { song ->
                    dao.insertAllFTS(song.rowId, song.title, song.subtitle, song.lyric)
                }
            }
            _songs.addAll(songEntities)
            _isLoading.value = false
        }
    }

    private fun parseJson(jsonString: String): List<Song> {
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Song>::class.java).toList()
    }
}