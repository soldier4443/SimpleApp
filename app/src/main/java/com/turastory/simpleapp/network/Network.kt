package com.turastory.simpleapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofitBuilder = Retrofit.Builder()
    .client(makeClient())
    .addConverterFactory(GsonConverterFactory.create())

private fun makeClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

fun postApi(): PostApiService {
    return retrofitBuilder
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .build()
        .create(PostApiService::class.java)
}