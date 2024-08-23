package com.resucito.app.mock

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

fun getMockSongs(): List<Song> {
    return MOCK_SONGS
}