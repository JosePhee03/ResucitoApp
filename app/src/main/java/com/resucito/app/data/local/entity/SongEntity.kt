package com.resucito.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage

@Entity(
    tableName = "song",
    indices = [Index(value = ["albumId"])],
    foreignKeys = [ForeignKey(
        entity = AlbumEntity::class,
        parentColumns = ["id"],
        childColumns = ["albumId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
@TypeConverters(StageConverter::class, StringListConverter::class, CategoryListConverter::class)
data class SongEntity(
    @PrimaryKey(true)
    val rowId: Int = 0,
    val id: String,
    val page: Int,
    val title: String,
    val subtitle: String,
    val capo: Int,
    val stage: Stage,
    val categories: List<Category>,
    val lyric: String,
    val chords: List<String>,
    val tone: String,
    val scale: String,
    val favorite: Boolean,
    val albumId: Int?
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
    fun fromObject(value: List<Category>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toObject(value: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(value, listType)
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