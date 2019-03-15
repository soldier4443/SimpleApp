package com.turastory.simpleapp.network

import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("posts/{postId}/comments")
    fun getComments(
        @Query("postId") postId: Int
    ): Call<List<Comment>>
}