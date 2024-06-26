package com.resucito.app.data.dto

import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage

data class SongDto(
    val id: String,
    val page: Int,
    val title: String,
    val subtitle: String,
    val capo: Int,
    val stage: Stage,
    val categories: List<Category>,
    val lyric: String,
    val chords: List<String>,
    val tone: String,
    val scale: String
)