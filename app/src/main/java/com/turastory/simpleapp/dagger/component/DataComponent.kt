package com.turastory.simpleapp.dagger.component

import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.dagger.module.data.DataModule
import com.turastory.simpleapp.dagger.module.data.NetworkModule
import com.turastory.simpleapp.dagger.module.data.RoomModule
import com.turastory.simpleapp.dagger.module.logging.LoggingModule
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.db.PostDatabase
import com.turastory.simpleapp.util.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RoomModule::class,
        NetworkModule::class,
        DataModule::class,
        LoggingModule::class
    ]
)
interface DataComponent {
    fun postApiService(): PostApiService
    fun postDatabase(): PostDatabase
    fun repository(): PostRepository
    fun logger(): Logger
}