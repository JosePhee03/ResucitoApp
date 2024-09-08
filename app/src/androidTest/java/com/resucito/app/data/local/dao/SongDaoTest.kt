package com.resucito.app.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.resucito.app.data.local.db.AppDatabase
import com.resucito.app.util.createSongEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SongDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var songDao: SongDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        songDao = db.songDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAllSongs_returns_empty_list_when_no_songs_are_inserted() = runTest {
        val songs = songDao.getAllSongs().first()

        Assert.assertTrue(songs.isEmpty())
    }

    @Test
    fun getAllSongs_returns_all_inserted_songs() = runTest {

        val id1 = "1"
        val id2 = "2"

        val song1 = createSongEntity(id = id1)
        val song2 = createSongEntity(id = id2)

        songDao.insertAll(listOf(song1, song2))

        val songs = songDao.getAllSongs().first()

        Assert.assertEquals(2, songs.size)
        Assert.assertEquals(id1, songs[0].id)
        Assert.assertEquals(id2, songs[1].id)
    }

    @Test
    fun deleteAllSongs_deletes_all_songs() = runTest {

        val song1 = createSongEntity()
        val song2 = createSongEntity()

        songDao.insertAll(listOf(song1, song2))

        val songsBeforeDelete = songDao.getAllSongs().first()
        Assert.assertEquals(2, songsBeforeDelete.size)

        songDao.deleteAllSongs()

        val songsAfterDelete = songDao.getAllSongs().first()
        Assert.assertTrue(songsAfterDelete.isEmpty())
    }

    @Test
    fun deleteAllSongsFts_deletes_all_songs() = runTest {

        val song1 = createSongEntity()
        val song2 = createSongEntity()

        songDao.insertAll(listOf(song1, song2))

        val songsBeforeDelete = songDao.getAllSongsFTS()

        Assert.assertEquals(2, songsBeforeDelete.size)

        songDao.deleteAllSongs()

        val songsAfterDelete = songDao.getAllSongs().first()
        Assert.assertTrue(songsAfterDelete.isEmpty())
    }

    @Test
    fun findSongById_returns_found_song() = runTest {
        val songId = "100"

        val song = createSongEntity(id = songId)

        songDao.insertAll(listOf(song))

        val songFound = songDao.findSongById(songId)

        Assert.assertEquals(songId, songFound?.id)
    }

    @Test
    fun findSongById_returns_null_when_not_found() = runTest {
        val id = "100"
        val expectedId = "200"

        val song = createSongEntity(id = id)

        songDao.insertAll(listOf(song))

        val songNotFound = songDao.findSongById(expectedId)

        Assert.assertNull(songNotFound)
    }

    @Test
    fun searchSongs_returns_the_songs_found_by_the_query() = runTest {

        val query = "test"

        val correctTitle = "Test Title"
        val incorrectTitle = ""
        val correctSubtitle = "Test Subtitle"
        val incorrectSubtitle = ""
        val correctId1 = "1"
        val correctId2 = "3"
        val incorrectId1 = "2"
        val incorrectId2 = "4"

        val correctSong1 =
            createSongEntity(id = correctId1, title = correctTitle, subtitle = incorrectSubtitle)
        val incorrectSong1 = createSongEntity(
            id = incorrectId1,
            title = incorrectTitle,
            subtitle = incorrectSubtitle
        )
        val correctSong2 =
            createSongEntity(id = correctId2, title = incorrectTitle, subtitle = correctSubtitle)
        val incorrectSong2 = createSongEntity(
            id = incorrectId2,
            title = incorrectTitle,
            subtitle = incorrectSubtitle
        )

        songDao.insertAll(listOf(correctSong1, incorrectSong1, correctSong2, incorrectSong2))

        val searchSongs = songDao.searchSongs(query).first()

        Assert.assertEquals(2, searchSongs.size)
    }

    @Test
    fun searchSongs_returns_empty_list_when_no_song_found() = runTest {

        val query = "not found"

        val song1 = createSongEntity()
        val song2 = createSongEntity()

        songDao.insertAll(listOf(song1, song2))

        val searchSong = songDao.searchSongs(query).first()

        Assert.assertTrue(searchSong.isEmpty())
    }

    @Test
    fun updateFavoriteSong_updates_favorite_status_of_specific_song() = runTest {

        val songId = "100"
        val initialStatus = false
        val updateStatus = true

        val song1 = createSongEntity(id = songId, favorite = initialStatus)

        songDao.insertAll(listOf(song1))

        songDao.updateFavoriteSong(songId, updateStatus)

        val song = songDao.findSongById(songId)

        Assert.assertEquals(updateStatus, song?.favorite)
    }

    @Test
    fun getAllFavoriteSongs_returns_list_favorite_songs() = runTest {

        val song1 = createSongEntity(favorite = true)
        val song2 = createSongEntity(favorite = true)
        val song3 = createSongEntity(favorite = false)

        songDao.insertAll(listOf(song1, song2, song3))

        val favoriteSongs = songDao.getAllFavoriteSongs(true).first()

        Assert.assertTrue(favoriteSongs.all { it.favorite })
    }
}