package com.resucito.app.domain.usecase.song

import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage
import com.resucito.app.domain.repository.SongRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSongUseCaseTest {

    private lateinit var getSongUseCase: GetSongUseCase
    private val repository: SongRepository = mockk()

    @Before
    fun setUp() {
        getSongUseCase = GetSongUseCase(repository)
    }

    @Test
    fun `execute returns song when found`() = runTest {

        val songId = "123"
        val expectedSong = Song(
            id = songId,
            title = "Test Song",
            capo = 0,
            page = 0,
            tone = "",
            lyric = "",
            scale = "",
            stage = Stage.LITURGY,
            chords = listOf(""),
            favorite = false,
            subtitle = "",
            categories = listOf(Category.EXIT)
        )
        coEvery { repository.getSong(songId) } returns expectedSong

        val result = getSongUseCase.execute(songId)

        assertEquals(expectedSong, result)
        coVerify (exactly = 1) { repository.getSong(songId) }
    }

    @Test
    fun `execute returns null when song not found`() = runTest {
        val songId = "123"
        coEvery { repository.getSong(songId) } returns null

        val result = getSongUseCase.execute(songId)

        assertEquals(null, result)
        coVerify(exactly = 1) { repository.getSong(songId) }
    }
}