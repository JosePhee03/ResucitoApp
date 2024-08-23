package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.mock.getMockSongs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class InsertSongsUseCaseTest {
    private lateinit var insertSongsUseCase: InsertSongsUseCase
    private val repository: SongRepository = mockk()

    private val filename = "songs.json"

    @Before
    fun setUp() {
        insertSongsUseCase = InsertSongsUseCase(repository)
    }

    @Test
    fun `execute inserts songs successfully`() = runTest {
        val songs = getMockSongs()

        coEvery { repository.getSongsFromAssets(filename) } returns Result.success(songs)
        coEvery { repository.insertSongs(songs) } returns Result.success(Unit)

        val result = insertSongsUseCase.execute(filename)

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { repository.getSongsFromAssets(filename) }
        coVerify(exactly = 1) { repository.insertSongs(songs) }
    }

    @Test
    fun `execute fails when getting songs from assets fails`() = runTest {

        val exception = Exception("Error getting songs from assets")

        coEvery { repository.getSongsFromAssets(filename) } returns Result.failure(exception)

        val result = insertSongsUseCase.execute(filename)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { repository.getSongsFromAssets(filename) }
        coVerify(exactly = 0) { repository.insertSongs(any()) }
    }

    @Test
    fun `execute fails when inserting songs fails`() = runTest {

        val filename = "songs.json"
        val songs = getMockSongs()
        val exception = Exception("Error inserting songs")

        coEvery { repository.getSongsFromAssets(filename) } returns Result.success(songs)
        coEvery { repository.insertSongs(songs) } returns Result.failure(exception)

        val result = insertSongsUseCase.execute(filename)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { repository.getSongsFromAssets(filename) }
        coVerify(exactly = 1) { repository.insertSongs(songs) }
    }
}