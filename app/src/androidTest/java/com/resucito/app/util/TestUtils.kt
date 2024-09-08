package com.resucito.app.util

import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage

fun createSongEntity(
    id: String = "1",
    page: Int = 1,
    title: String = "Test Song",
    subtitle: String = "Subtitle",
    lyric: String = "La la la...",
    chords: List<String> = listOf("C", "G", "Am", "F"),
    tone: String = "C",
    scale: String = "Major",
    favorite: Boolean = false,
    capo: Int = 0,
    categories: List<Category> = listOf(Category.EXIT),
    stage: Stage = Stage.LITURGY
): SongEntity {
    return SongEntity(
        id = id,
        page = page,
        title = title,
        subtitle = subtitle,
        lyric = lyric,
        chords = chords,
        tone = tone,
        scale = scale,
        capo = capo,
        stage = stage,
        categories = categories,
        favorite = favorite
    )
}