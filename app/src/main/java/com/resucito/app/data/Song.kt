package com.resucito.app.data

data class Song(
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