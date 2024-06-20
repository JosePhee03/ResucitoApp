package com.resucito.app.navigation

import kotlinx.serialization.Serializable


@Serializable
data object Start

@Serializable
data object Home

@Serializable
data object Search

@Serializable
data class Song(val id: String)

@Serializable
data object Album
