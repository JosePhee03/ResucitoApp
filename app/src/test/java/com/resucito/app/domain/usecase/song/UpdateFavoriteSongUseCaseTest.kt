package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.repository.SongRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class UpdateFavoriteSongUseCaseTest {

    private lateinit var updateFavoriteSongUseCase: UpdateFavoriteSongUseCase
    private val repository: SongRepository = mockk()

    private val songId = "1"

    @Before
    fun setUp() {
        updateFavoriteSongUseCase = UpdateFavoriteSongUseCase(repository)
    }

    @Test
    fun `execute updates favorite status successfully`() = runTest {

        val favorite = true

        coEvery { repository.updateFavoriteSong(songId, favorite) } returns Result.success(Unit)

        val result = updateFavoriteSongUseCase.execute(songId, favorite)

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { repository.updateFavoriteSong(songId, favorite) }
    }

    @Test
    fun `execute returns failure when update fails`() = runTest {

        val favorite = false
        val exception = Exception("Update failed")

        coEvery {
            repository.updateFavoriteSong(
                songId,
                favorite
            )
        } returns Result.failure(exception)

        val result = updateFavoriteSongUseCase.execute(songId, favorite)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { repository.updateFavoriteSong(songId, favorite) }
    }

}