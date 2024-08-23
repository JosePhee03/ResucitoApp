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

class GetAllFavoriteSongsUseCaseTest {

    private lateinit var getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase
    private val repository: SongRepository = mockk()

    @Before
    fun setUp() {
        getAllFavoriteSongsUseCase = GetAllFavoriteSongsUseCase(repository)
    }

    @Test
    fun `execute returns flow of favorite songs from repository`() = runTest {

        val favoriteSongs = getMockSongs()
        val flowOfFavoriteSongs: Flow<List<Song>> = flowOf(favoriteSongs)
        coEvery { repository.getAllFavoriteSongs(true) } returns Result.success(flowOfFavoriteSongs)

        val result = getAllFavoriteSongsUseCase.execute()

        assertEquals(Result.success(flowOfFavoriteSongs), result)
        coVerify(exactly = 1) { repository.getAllFavoriteSongs(true) }
    }

    @Test
    fun `execute returns failure when repository fails`() = runTest {

        val exception = Exception("Database error")
        coEvery { repository.getAllFavoriteSongs(true) } returns Result.failure(exception)

        val result = getAllFavoriteSongsUseCase.execute()

        assertEquals(Result.failure<Flow<List<Song>>>(exception), result)
        coVerify(exactly = 1) { repository.getAllFavoriteSongs(true) }
    }

}