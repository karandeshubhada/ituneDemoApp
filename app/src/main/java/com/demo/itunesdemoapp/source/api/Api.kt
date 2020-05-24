package com.demo.itunesdemoapp.source.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A singleton implementation of the API.
 */
object Api {

    private val client = OkHttpClient.Builder().build()

    val api = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(ApiService::class.java)!!
}