package com.resucito.app.data.mapper

import com.resucito.app.data.dto.SongDto
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SongMapperTest {

    @Test
    fun `fromDtoToDomain should map SongDto to Song correctly`() {
        val songDto = SongDto(
            id = "1",
            page = 1,
            title = "Test Song",
            subtitle = "Subtitle",
            capo = 0,
            stage = Stage.LITURGY,
            categories = listOf(Category.EXIT),
            lyric = "Some lyrics",
            chords = listOf("C", "G", "Am", "F"),
            tone = "C",
            scale = "Major"
        )

        val song = SongMapper.fromDtoToDomain(songDto)

        assertEquals(songDto.id, song.id)
        assertEquals(songDto.page, song.page)
        assertEquals(songDto.title, song.title)
        assertEquals(songDto.subtitle, song.subtitle)
        assertEquals(songDto.capo, song.capo)
        assertEquals(songDto.stage, song.stage)
        assertEquals(songDto.categories, song.categories)
        assertEquals(songDto.lyric, song.lyric)
        assertEquals(songDto.chords, song.chords)
        assertEquals(songDto.tone, song.tone)
        assertEquals(songDto.scale, song.scale)
        assertEquals(false, song.favorite)
    }

    @Test
    fun `fromDomainToEntity should map Song to SongEntity correctly`() {
        // 1. Datos de prueba
        val song = Song(
            id = "song1",
            page = 1,
            title = "Test Song",
            subtitle = "Subtitle",
            capo = 0,
            stage = Stage.LITURGY,
            categories = listOf(Category.EXIT),
            lyric = "Some lyrics",
            chords = listOf("C", "G", "Am", "F"),
            tone = "C",
            scale = "Major",
            favorite = true
        )

        val songEntity = SongMapper.fromDomainToEntity(song)

        assertEquals(song.id, songEntity.id)
        assertEquals(song.page, songEntity.page)
        assertEquals(song.title, songEntity.title)
        assertEquals(song.subtitle, songEntity.subtitle)
        assertEquals(song.capo, songEntity.capo)
        assertEquals(song.stage, songEntity.stage)
        assertEquals(song.categories, songEntity.categories)
        assertEquals(song.lyric, songEntity.lyric)
        assertEquals(song.chords, songEntity.chords)
        assertEquals(song.tone, songEntity.tone)
        assertEquals(song.scale, songEntity.scale)
        assertEquals(song.favorite, songEntity.favorite)
    }

    @Test
    fun `fromEntityToDomain should map SongEntity to Song correctly`() {
        val songEntity = SongEntity(
            id = "song1",
            page = 1,
            title = "Test Song",
            subtitle = "Subtitle",
            capo = 0,
            stage = Stage.LITURGY,
            categories = listOf(Category.EXIT),
            lyric = "Some lyrics",
            chords = listOf("C", "G", "Am", "F"),
            tone = "C",
            scale = "Major",
            favorite = true
        )

        val song = SongMapper.fromEntityToDomain(songEntity)

        assertEquals(songEntity.id, song.id)
        assertEquals(songEntity.page, song.page)
        assertEquals(songEntity.title, song.title)
        assertEquals(songEntity.subtitle, song.subtitle)
        assertEquals(songEntity.capo, song.capo)
        assertEquals(songEntity.stage, song.stage)
        assertEquals(songEntity.categories, song.categories)
        assertEquals(songEntity.lyric, song.lyric)
        assertEquals(songEntity.chords, song.chords)
        assertEquals(songEntity.tone, song.tone)
        assertEquals(songEntity.scale, song.scale)
        assertEquals(songEntity.favorite, song.favorite)
    }

}