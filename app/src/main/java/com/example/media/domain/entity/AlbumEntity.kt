package com.example.media.domain.entity

data class AlbumEntity(
    val artist: String,
    val genre: String,
    val id: Int,
    val published: String,
    val subtitle: String,
    val title: String,
    val tracks: List<TrackEntity>
)

