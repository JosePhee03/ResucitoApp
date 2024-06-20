package com.resucito.app.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resucito.app.data.Category
import com.resucito.app.data.Stage

@Entity(tableName = "songs")
@TypeConverters(StageConverter::class, StringListConverter::class, CategoryListConverter::class)
data class SongEntity(
    @PrimaryKey(true)
    val rowId: Int,
    val id: String,
    val page: Int,
    val title: String,
    val subtitle: String,
    val capo: Int,
    val stage: Stage,
    val categories: List<String>,
    val lyric: String,
    val chords: List<String>,
    val tone: String,
    val scale: String
)

class StageConverter {
    @TypeConverter
    fun fromObject(stage: Stage): String {
        return stage.name
    }

    @TypeConverter
    fun toObject(value: String): Stage {
        return Stage.valueOf(value)
    }
}

class CategoryListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromObject(strings: List<Category>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toObject(data: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(data, listType)
    }
}

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromObject(strings: List<String>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toObject(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}