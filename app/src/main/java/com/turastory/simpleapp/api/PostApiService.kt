package com.turastory.simpleapp.api

import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface PostApiService {
    @GET("posts")
    fun getPosts(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): Single<List<Post>>

    @GET("posts/{postId}")
    fun getPost(
        @Path("postId") postId: Int
    ): Single<Post>

    @DELETE("posts/{postId}")
    fun deletePost(
        @Path("postId") postId: Int
    ): Completable // Ignore the response

    @GET("comments")
    fun getComments(
        @Query("postId") postId: Int
    ): Single<List<Comment>>

    @PATCH("posts/{postId}")
    fun updatePost(
        @Path("postId") postId: Int,
        @Body post: Post
    ): Single<Post>
}