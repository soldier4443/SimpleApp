package com.turastory.simpleapp.dagger.component

import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.dagger.module.NetworkModule
import com.turastory.simpleapp.data.repository.PostRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {
    fun postApiService(): PostApiService
    fun repository(): PostRepository
}