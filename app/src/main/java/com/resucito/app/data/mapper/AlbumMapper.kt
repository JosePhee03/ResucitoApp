package com.resucito.app.data.mapper

import com.resucito.app.data.local.entity.AlbumEntity
import com.resucito.app.domain.model.Album

class AlbumMapper {

    fun fromDomainToEntity(album: Album): AlbumEntity {
        return AlbumEntity(
            id = album.id,
            name = album.name
        )
    }

    fun fromEntityToDomain(albumEntity: AlbumEntity): Album {
        return Album(
            id = albumEntity.id,
            name = albumEntity.name
        )
    }
}