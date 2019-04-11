package com.turastory.simpleapp.data.source

import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Single

class PostRemoteDataSource(private val api: PostApiService) {
    fun getPost(postId: Int): Single<Post> {
        return api.getPost(postId)
    }

    fun getComments(postId: Int): Single<List<Comment>> {
        return api.getComments(postId)
    }

    fun deletePost(postId: Int): Completable {
        return api.deletePost(postId)
    }

    fun updatePost(postId: Int, newPostData: Post): Single<Post> {
        return api.updatePost(postId, newPostData)
    }
}