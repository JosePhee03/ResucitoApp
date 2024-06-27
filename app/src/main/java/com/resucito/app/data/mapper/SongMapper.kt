package com.resucito.app.data.mapper

import com.resucito.app.data.dto.SongDto
import com.resucito.app.data.local.entity.SongEntity
import com.resucito.app.domain.model.Song

object SongMapper {

    fun fromDtoToDomain(songDto: SongDto): Song {
        return Song(
            id = songDto.id,
            page = songDto.page,
            title = songDto.title,
            subtitle = songDto.subtitle,
            capo = songDto.capo,
            stage = songDto.stage,
            categories = songDto.categories,
            lyric = songDto.lyric,
            chords = songDto.chords,
            tone = songDto.tone,
            scale = songDto.scale,
            favorite = false,
            albumId = null
        )
    }

    fun fromDomainToEntity(song: Song): SongEntity {
        return SongEntity(
            id = song.id,
            page = song.page,
            title = song.title,
            subtitle = song.subtitle,
            capo = song.capo,
            stage = song.stage,
            categories = song.categories,
            lyric = song.lyric,
            chords = song.chords,
            tone = song.tone,
            scale = song.scale,
            favorite = song.favorite,
            albumId = song.albumId
        )
    }

    fun fromEntityToDomain(songEntity: SongEntity): Song {
        return Song(
            id = songEntity.id,
            page = songEntity.page,
            title = songEntity.title,
            subtitle = songEntity.subtitle,
            capo = songEntity.capo,
            stage = songEntity.stage,
            categories = songEntity.categories,
            lyric = songEntity.lyric,
            chords = songEntity.chords,
            tone = songEntity.tone,
            scale = songEntity.scale,
            favorite = songEntity.favorite,
            albumId = null
        )
    }
}