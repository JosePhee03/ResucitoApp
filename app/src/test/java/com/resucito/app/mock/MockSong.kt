package com.resucito.app.mock

import com.resucito.app.data.dto.SongDto
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage

val MOCK_SONGS = listOf(
    Song(
        id = "1", title = "Song 2", subtitle = "Subtitle 2",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        favorite = true,
        categories = listOf(Category.EXIT)
    ),
    Song(
        id = "2", title = "Song 2", subtitle = "Subtitle 2",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        favorite = true,
        categories = listOf(Category.EXIT)
    )
)

val MOCK_SONG_ENTITIES = listOf(
    SongEntity(
        id = "1", title = "Song 1", subtitle = "Subtitle 1",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        favorite = true,
        categories = listOf(Category.EXIT)
    ),
    SongEntity(
        id = "2", title = "Song 2", subtitle = "Subtitle 2",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        favorite = true,
        categories = listOf(Category.EXIT)
    )
)

val MOCK_SONG_DTO = listOf(
    SongDto(
        id = "1", title = "Song 1", subtitle = "Subtitle 1",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        categories = listOf(Category.EXIT)
    ),
    SongDto(
        id = "2", title = "Song 2", subtitle = "Subtitle 2",
        capo = 0,
        page = 0,
        tone = "",
        lyric = "",
        scale = "",
        stage = Stage.LITURGY,
        chords = listOf(""),
        categories = listOf(Category.EXIT)
    )
)


fun getMockSongs(): List<Song> {
    return MOCK_SONGS
}

fun getMockSongEntities(): List<SongEntity> {
    return MOCK_SONG_ENTITIES
}

fun getMockSongsDto(): List<SongDto> {
    return MOCK_SONG_DTO
}