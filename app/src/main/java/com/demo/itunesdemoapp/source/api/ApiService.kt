package com.demo.itunesdemoapp.source.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("search?term=Michael+Jackson")
    fun searchMoviesFromAustralia(
        @Query("term") keyword: String,
        @Query("limit") limit: Int
    ): Call<ItuneResponse>

    @POST("search?term=Michael+Jackson")
    fun searchMoviesFromAustralia(
        @Query("term") keyword: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ItuneResponse>
}