package com.turastory.simpleapp.dagger.module.data

import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.data.source.PostLocalDataSource
import com.turastory.simpleapp.data.source.PostRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideRepository(
        apiService: PostApiService,
        localDataSource: PostLocalDataSource,
        remoteDataSource: PostRemoteDataSource
    ): PostRepository {
        return PostRepository(apiService, localDataSource, remoteDataSource)
    }
}