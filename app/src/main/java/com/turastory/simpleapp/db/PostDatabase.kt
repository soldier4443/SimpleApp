package com.turastory.simpleapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.turastory.simpleapp.vo.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}