package com.resucito.app.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data object Start

@Serializable
data object Home

@Serializable
data class Search(val stageId: String?, val categoryId: String?)

@Serializable
data class Song(val id: String)

@Serializable
data object Album