package com.example.media.data.network.response

import com.example.media.domain.entity.AlbumEntity
import com.example.media.domain.entity.TrackEntity
import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("published")
    val published: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tracks")
    val tracks: List<Track>
) {
    companion object {
        fun toEntity(album: Album): AlbumEntity {
            return AlbumEntity(
                album.artist,
                album.genre,
                album.id,
                album.published,
                album.subtitle,
                album.title,
                album.tracks.map { Track.toEntity(it) }
            )
        }
    }
}

data class Track(
    @SerializedName("file")
    val fileName: String,
    @SerializedName("id")
    val id: Int
) {
    companion object {
        fun toEntity(track: Track): TrackEntity {
            return TrackEntity(
                track.fileName,
                track.id,
                "https://raw.githubusercontent.com/netology-code/andad-homeworks/master/09_multimedia/data/${track.fileName}"
            )
        }
    }
}