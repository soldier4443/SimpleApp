package com.turastory.simpleapp.dagger.module.data

import android.app.Application
import androidx.room.Room
import com.turastory.simpleapp.db.PostDao
import com.turastory.simpleapp.db.PostDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {
    private val db: PostDatabase = Room.databaseBuilder(
        application,
        PostDatabase::class.java,
        "post-database"
    ).build()

    @Singleton
    @Provides
    fun providePostDatabase(): PostDatabase {
        return db
    }

    @Singleton
    @Provides
    fun providePostDao(): PostDao {
        return db.postDao()
    }
}