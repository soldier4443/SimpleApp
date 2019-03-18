package com.turastory.simpleapp.network

import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import retrofit2.Call
import retrofit2.http.*

interface PostApiService {
    @GET("posts")
    fun getPosts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): Call<List<Post>>

    @GET("posts/{postId}")
    fun getPost(
        @Path("postId") postId: Int
    ): Call<Post>

    @DELETE("posts/{postId}")
    fun deletePost(
        @Path("postId") postId: Int
    ): Call<Void>

    @GET("comments")
    fun getComments(
        @Query("postId") postId: Int
    ): Call<List<Comment>>

    @PATCH("posts/{postId}")
    fun updatePost(
        @Path("postId") postId: Int,
        @Body post: Post
    ): Call<Post>
}