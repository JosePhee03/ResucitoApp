package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CleanSongsUseCaseTest {
    private lateinit var cleanSongsUseCase: CleanSongsUseCase
    private val repository: SongRepository = mockk()

    @Before
    fun setUp() {
        cleanSongsUseCase = CleanSongsUseCase(repository)
    }

    @Test
    fun `execute returns success when repository clean operations succeed`() = runTest {

        coEvery { repository.cleanSongs() } returns Result.success(Unit)

        val result = cleanSongsUseCase.execute()

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { repository.cleanSongs() }
    }

    @Test
    fun `execute returns failure when cleanSongs fails`() = runTest {

        val exception = Exception("Error cleaning songs")
        coEvery { repository.cleanSongs() } returns Result.failure(exception)

        val result = cleanSongsUseCase.execute()

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { repository.cleanSongs() }
    }
}