package com.turastory.simpleapp.dagger.module.data

import com.turastory.simpleapp.api.PostApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providePostApiService(): PostApiService {
        return retrofitBuilder
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
            .create(PostApiService::class.java)
    }

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .client(makeClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private fun makeClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .build()
}