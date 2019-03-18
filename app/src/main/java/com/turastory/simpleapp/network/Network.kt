package com.turastory.simpleapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
    .client(makeClient())
    .addConverterFactory(GsonConverterFactory.create())

private fun makeClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    })
    .build()
