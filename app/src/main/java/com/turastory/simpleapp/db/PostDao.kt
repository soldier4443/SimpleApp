package com.turastory.simpleapp.db

import androidx.paging.DataSource
import androidx.room.*
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll(): DataSource.Factory<Int, Post>

    @Query("SELECT * FROM post WHERE id = (:id)")
    fun getPostById(id: Int): Single<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

    @Query("DELETE FROM post WHERE id = (:id)")
    fun deleteById(id: Int): Completable

    @Update
    fun updatePost(newPost: Post): Completable
}