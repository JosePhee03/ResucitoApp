package com.resucito.app.data.repository

import com.resucito.app.data.local.dao.SongDao
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.data.local.resource.LocalJsonParser
import com.resucito.app.data.mapper.SongMapper
import com.resucito.app.domain.repository.SongRepository
import com.resucito.app.mock.getMockSongEntities
import com.resucito.app.mock.getMockSongs
import com.resucito.app.mock.getMockSongsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

class SongRoomRepositoryTest {

    private val songDao: SongDao = mockk()
    private lateinit var repository: SongRepository
    private val localJsonParser: LocalJsonParser = mockk()

    @Before
    fun setup() {
        repository = SongRoomRepository(songDao, localJsonParser)
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

    @Test
    fun `getSongsFromAssets returns success with valid JSON data`() = runTest {
        val filename = "songs.json"

        val songsDto = getMockSongsDto()
        val expectedSongs = songsDto.map { SongMapper.fromDtoToDomain(it) }

        coEvery { localJsonParser.parseSongsFromAssets(any()) } returns Result.success(songsDto)

        val result = repository.getSongsFromAssets(filename)

        assertEquals(Result.success(expectedSongs), result)

        coVerify(exactly = 1) { localJsonParser.parseSongsFromAssets(any()) }
    }

    @Test
    fun `getSongsFromAssets returns failure with invalid JSON data`() = runTest {
        val filename = "songs.json"

        val exception = Exception("Parsing failed")

        coEvery { localJsonParser.parseSongsFromAssets(filename) } returns Result.failure(exception)

        val result = repository.getSongsFromAssets(filename)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)

        coVerify(exactly = 1) { localJsonParser.parseSongsFromAssets(filename) }
    }

    @Test
    fun `insertSongs should insert songs successfully and return Result success`() = runTest {

        val songs = getMockSongs()

        coEvery { songDao.insertAll(any()) } returns Unit

        val result = repository.insertSongs(songs)

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { songDao.insertAll(any()) }
    }

    @Test
    fun `insertSongs should return failure when dao throws exception`() = runTest {

        val exception = Exception("Database error")
        val songs = getMockSongs()

        coEvery { songDao.insertAll(any()) } throws exception

        val result = repository.insertSongs(songs)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)

        coVerify(exactly = 1) { songDao.insertAll(any()) }
    }

    @Test
    fun `searchSongs should return Flow of songs matching the query`() = runTest {
        val songEntities = getMockSongEntities()
        val expectedSongs = songEntities.map { SongMapper.fromEntityToDomain(it) }

        coEvery { songDao.searchSongs(any()) } returns flowOf(songEntities)

        val result = repository.searchSongs("")

        assertTrue(result.isSuccess)
        val resultSongList = result.getOrNull()?.toList()?.flatten()

        assertEquals(expectedSongs, resultSongList)

        coVerify(exactly = 1) { songDao.searchSongs(any()) }
    }

    @Test
    fun `searchSongs should return Result failure when dao throws exception`() = runTest {
        val exception = Exception("Database error")

        coEvery { songDao.searchSongs(any()) } throws exception

        val result = repository.searchSongs("")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)

        coVerify(exactly = 1) { songDao.searchSongs(any()) }
    }

    @Test
    fun `getSong should return Song when songEntity is found`() = runTest {

        val songId = "songId"
        val songEntity = getMockSongEntities()[0]
        val expectedSong = SongMapper.fromEntityToDomain(songEntity)

        coEvery { songDao.findSongById(songId) } returns songEntity

        val result = repository.getSong(songId)

        assertEquals(expectedSong, result)
        coVerify(exactly = 1) { songDao.findSongById(any()) }
    }

    @Test
    fun `getSong should return null when songEntity is not found`() = runTest {
        val songId = "songId"

        coEvery { songDao.findSongById(songId) } returns null

        val result = repository.getSong(songId)

        assertNull(result)
        coVerify(exactly = 1) { songDao.findSongById(songId) }
    }

}