package com.resucito.app.domain.usecase

import com.resucito.app.data.repository.SongRepository
import com.resucito.app.domain.model.Song
import javax.inject.Inject

class SearchSongsUseCase @Inject constructor(private val repository: SongRepository) {

    suspend fun execute(query: String):List<Song>{
        return repository.searchSongs(query)
    }
}