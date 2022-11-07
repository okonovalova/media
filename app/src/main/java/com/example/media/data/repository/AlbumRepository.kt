package com.example.media.data.repository

import com.example.media.data.network.ApiService
import com.example.media.data.network.response.Album
import com.example.media.domain.entity.AlbumEntity
import javax.inject.Inject

class AlbumRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAlbum(): AlbumEntity? {
       return apiService.getAlbum().body()?.let {
           Album.toEntity(it)
       }
    }
}