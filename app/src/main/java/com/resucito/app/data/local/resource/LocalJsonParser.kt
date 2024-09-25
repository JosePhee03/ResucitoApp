package com.resucito.app.data.local.resource

import android.content.Context
import com.google.gson.Gson
import com.resucito.app.data.dto.SongDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

interface AssetProvider {
    suspend fun getJsonDataFromAsset(fileName: String): Result<String>
}

interface JsonParser {
    fun <T> fromJson(json: String, clazz: Class<T>): T

    fun toJson(src: Any): String
}

class GsonParser(private val gson: Gson = Gson()) : JsonParser {
    override fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    override fun toJson(src: Any): String {
        return gson.toJson(src)
    }
}

class AndroidAssetProvider(private val context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : AssetProvider {
    override suspend fun getJsonDataFromAsset(fileName: String): Result<String> {
        return runCatching {
            val assetManager = context.assets
            val inputStream = withContext(dispatcher) {
                assetManager.open(fileName)
            }
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            bufferedReader.use { it.readText() }
        }
    }
}

class LocalJsonParser(
    private val assetProvider: AssetProvider,
    private val parser: JsonParser = GsonParser()
) {
    suspend fun parseSongsFromAssets(fileName: String): Result<List<SongDto>> {
        return assetProvider.getJsonDataFromAsset(fileName).mapCatching { jsonString ->
            parser.fromJson(jsonString, Array<SongDto>::class.java).toList()
        }
    }

    fun parseSongsToJson(src: Any): String {
        return parser.toJson(src)
    }
}