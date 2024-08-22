package com.resucito.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.usecase.song.GetAllFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LibraryState(
    val isError: Boolean = true,
    val isLoading: Boolean = false,
    val songbooks: List<Song> = emptyList()
)

@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            val resultFavoriteSongs = getAllFavoriteSongsUseCase.execute()
            resultFavoriteSongs.fold(
                onSuccess = { songbooks ->
                    songbooks.collect { songbooksCollect ->
                        _state.update {
                            it.copy(
                                songbooks = songbooksCollect,
                                isLoading = false
                            )
                        }
                    }
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            isError = true,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}