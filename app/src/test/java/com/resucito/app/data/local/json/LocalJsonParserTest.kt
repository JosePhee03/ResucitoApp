package com.resucito.app.data.local.json

import com.resucito.app.mock.getMockSongsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

class LocalJsonParserTest {

    private val mockAssetProvider: AssetProvider = mockk()
    private lateinit var localJsonParser: LocalJsonParser

    @Before
    fun setup() {
        localJsonParser = LocalJsonParser(mockAssetProvider)
    }

    @Test
    fun `test parseUsersFromAssets with valid JSON`() = runTest {
        val mockSongDto = getMockSongsDto()
        val jsonSong = localJsonParser.parseSongsToJson(mockSongDto)
        coEvery { mockAssetProvider.getJsonDataFromAsset(any()) } returns Result.success(jsonSong)

        val result = localJsonParser.parseSongsFromAssets("fake_file.json")

        assertTrue(result.isSuccess)

        assertEquals(Result.success(mockSongDto), result)
    }

    @Test
    fun `test parseUsersFromAssets with invalid JSON`() = runTest {

        val invalidJson = "Invalid JSON"
        coEvery { mockAssetProvider.getJsonDataFromAsset(any()) } returns Result.success(invalidJson)

        val result = localJsonParser.parseSongsFromAssets("fake_file.json")

        assertTrue(result.isFailure)
    }


}