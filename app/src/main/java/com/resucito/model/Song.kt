package com.resucito.model

data class Song(
    val lyric: String,
    val tone: String,
    val capo: Int,
    val tags: List<Tags>,
    val title: String,
    var page: Int,
    val stage: Stage,
    val subtitle: String
)


enum class Note() {
    C,
    D,
    E,
    F,
    G,
    A,
    B
}

enum class Tags {
    PSALM,
    ADVENT,
    CHILDRENS_SONG,
    CHRISTMAS,
    COMMUNION,
    LENT,
    EASTER,
    ENTRANCE,
    EXIT,
    FRACTION_OF_BREAD,
    LUTES_AND_VESPERS,
    PEACE_AND_OFFERINGS,
    PENTECOST,
    SIGNING_TO_THE_VIRGIN
}

enum class Stage {
    ELECTION,
    PRECATECHUMENATE,
    CATECHUMENATE,
    LITURGY
}