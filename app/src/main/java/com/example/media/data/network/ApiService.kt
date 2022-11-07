package com.example.media.data.network

import com.example.media.data.network.response.Album
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("netology-code/andad-homeworks/master/09_multimedia/data/album.json")
    suspend fun getAlbum(): Response<Album>
}