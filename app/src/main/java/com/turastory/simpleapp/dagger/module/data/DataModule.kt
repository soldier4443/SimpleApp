package com.turastory.simpleapp.dagger.module.data

import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.db.PostDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideRepository(
        api: PostApiService,
        db: PostDatabase
    ): PostRepository {
        return PostRepository(api, db)
    }
}