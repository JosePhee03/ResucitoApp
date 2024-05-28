package com.resucito.model

data class SongList(
    val title: String,
    var page: Int,
    val stage: Stage,
    val subtitle: String,
    val favorite: Boolean
)
