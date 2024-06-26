package com.resucito.app.data.local.json

import android.content.Context
import com.google.gson.Gson
import com.resucito.app.data.dto.SongDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object LocalJsonParser {

    private suspend fun getJsonDataFromAsset(context: Context, fileName: String): Result<String> {
        return runCatching {
            val assetManager = context.assets
            val inputStream = withContext(Dispatchers.IO) {
                assetManager.open(fileName)
            }
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val textJson = bufferedReader.use { it.readText() }
            return Result.success(textJson)
        }
    }

    suspend fun parseUsersFromAssets(context: Context, fileName: String): Result<List<SongDto>> {
        return getJsonDataFromAsset(context, fileName).mapCatching { jsonString ->
            val gson = Gson()
            gson.fromJson(jsonString, Array<SongDto>::class.java).toList()
        }
    }

}