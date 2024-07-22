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

enum class MainRoute(val page: Int) {
    HOME(0),
    SEARCH(1),
    LIBRARY(2),
    MORE(3)
}

