package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.model.Song
import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.mock.getMockSongs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class SearchSongsUseCaseTest {

    private lateinit var searchSongsUseCase: SearchSongsUseCase
    private val repository: SongRepository = mockk()

    private val query = "resucito"

    @Before
    fun setUp() {
        searchSongsUseCase = SearchSongsUseCase(repository)
    }

    @Test
    fun `execute returns flow of songs for a given query`() = runTest {

        val songs = getMockSongs()
        val flowOfSongs: Flow<List<Song>> = flowOf(songs)

        coEvery { repository.searchSongs(query) } returns Result.success(flowOfSongs)

        val result = searchSongsUseCase.execute(query)

        assertEquals(Result.success(flowOfSongs), result)
        coVerify(exactly = 1) { repository.searchSongs(query) }
    }

    @Test
    fun `execute returns failure when search fails`() = runTest {

        val exception = Exception("Search error")

        coEvery { repository.searchSongs(query) } returns Result.failure(exception)

        val result = searchSongsUseCase.execute(query)

        assertEquals(Result.failure<Flow<List<Song>>>(exception), result)
        coVerify(exactly = 1) { repository.searchSongs(query) }
    }

}