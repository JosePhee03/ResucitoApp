package com.resucito.app.presentation.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
data object Start

@Serializable
data object Home

@Serializable
data class Search(val stageId: String?, val categoryId: String?)

@Serializable
data class Song(val id: String)

@Serializable
data object Library

@Serializable
data object SongBook

@Serializable
data object More
