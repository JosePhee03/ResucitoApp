package com.resucito.app.data.mapper

import com.resucito.app.data.local.entity.AlbumEntity
import com.resucito.app.domain.model.Album

object AlbumMapper {

    fun fromDomainToEntity(album: Album): AlbumEntity {
        return AlbumEntity(
            name = album.name
        )
    }

    fun fromEntityToDomain(albumEntity: AlbumEntity): Album {
        return Album(
            name = albumEntity.name
        )
    }
}