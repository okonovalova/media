package com.example.media.domain.entity

data class TrackEntity(
    val fileName: String,
    val id: Int,
    val url: String,
    val state: STATE = STATE.NOT_PLAY
)

enum class STATE {
    PLAY, PAUSE, NOT_PLAY
}