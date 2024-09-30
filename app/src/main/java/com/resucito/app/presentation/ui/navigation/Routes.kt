package com.resucito.app.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object Start : Routes()

    @Serializable
    data class Song(val id: String) : Routes()

    @Serializable
    data object SongBook : Routes()

    @Serializable
    data class Main(val page: Int) : Routes()
}

object Page {
    const val HOME = 0
    const val SEARCH = 1
    const val LIBRARY = 2
    const val MORE = 3
}

