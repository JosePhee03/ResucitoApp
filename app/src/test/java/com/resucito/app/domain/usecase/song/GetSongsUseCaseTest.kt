package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.model.Song
import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.mock.getMockSongs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSongsUseCaseTest {

    private lateinit var getSongsUseCase: GetSongsUseCase
    private val repository: SongRepository = mockk()

    @Before
    fun setUp() {
        getSongsUseCase = GetSongsUseCase(repository)
    }

    @Test
    fun `execute should return flow of songs when repository returns successfully`() = runTest {

        val expectedSongs = getMockSongs()
        val flowOfSongs: Flow<List<Song>> = flowOf(expectedSongs)

        coEvery { repository.getSongsFromDatabase() } returns Result.success(flowOfSongs)

        val result = getSongsUseCase.execute()

        assertEquals(Result.success(flowOfSongs), result)
    }

    @Test
    fun `execute should return failure when repository throws exception`() = runTest {

        val exception = Exception("Database error")
        coEvery { repository.getSongsFromDatabase() } returns Result.failure(exception)

        val result = getSongsUseCase.execute()

        assertEquals(Result.failure<Flow<List<Song>>>(exception), result)
    }


}