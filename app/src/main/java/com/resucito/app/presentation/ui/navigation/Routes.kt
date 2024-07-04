package com.resucito.app.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object More : Routes()

    @Serializable
    data object Start: Routes()

    @Serializable
    data object Home: Routes()

    @Serializable
    data class Search(val stageId: String?, val categoryId: String?): Routes()

    @Serializable
    data class Song(val id: String): Routes()

    @Serializable
    data object Library: Routes()

    @Serializable
    data object SongBook: Routes()
}