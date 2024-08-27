package com.resucito.app.data.repository

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.resucito.app.data.dto.SongDto
import com.resucito.app.data.local.dao.SongDao
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.data.local.json.LocalJsonParser
import com.resucito.app.data.mapper.SongMapper
import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.mock.getMockSongEntities
import com.resucito.app.mock.getMockSongsDto
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class SongRoomRepositoryTest {

    private val songDao: SongDao = mockk()
    private val context: Context = mockk()
    private lateinit var repository: SongRepository
    private lateinit var parser: LocalJsonParser

    @Before
    fun setup() {
        repository = SongRoomRepository(songDao, context)
    }

    @Test
    fun `getSongsFromDatabase should return flow of songs`() = runTest {

        val songEntityList = getMockSongEntities()
        val expectedSongs = songEntityList.map { SongMapper.fromEntityToDomain(it) }
        val songFlow: Flow<List<SongEntity>> = flowOf(songEntityList)

        coEvery { songDao.getAllSongs() } returns songFlow

        val result = repository.getSongsFromDatabase()

        assertTrue(result.isSuccess)
        val resultSongList = result.getOrNull()?.toList()?.flatten()

        assertEquals(expectedSongs, resultSongList)

        verify(exactly = 1) { songDao.getAllSongs() }
    }

    @Test
    fun `getSongsFromDatabase should return failure when dao throws exception`() = runTest {

        val exception = Exception("Database error")

        coEvery { songDao.getAllSongs() } throws exception

        val result = repository.getSongsFromDatabase()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)

        verify(exactly = 1) { songDao.getAllSongs() }
    }

}