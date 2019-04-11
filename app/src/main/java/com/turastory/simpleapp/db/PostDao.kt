package com.turastory.simpleapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.turastory.simpleapp.vo.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): List<Post>

    @Delete
    fun remove(post: Post)
}