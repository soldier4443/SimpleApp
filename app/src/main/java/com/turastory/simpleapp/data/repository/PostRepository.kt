package com.turastory.simpleapp.data.repository

import com.turastory.simpleapp.data.Repository
import com.turastory.simpleapp.data.source.PostRemoteDataSource
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Single

class PostRepository(private val remoteDataSource: PostRemoteDataSource) : Repository {

    fun getPosts(start: Int, limit: Int): Single<List<Post>> {
        return remoteDataSource.getPosts(start, limit)
    }

    fun getPost(postId: Int): Single<Post> {
        return remoteDataSource.getPost(postId)
    }

    fun getComments(postId: Int): Single<List<Comment>> {
        return remoteDataSource.getComments(postId)
    }

    fun deletePost(postId: Int): Completable {
        return remoteDataSource.deletePost(postId)
    }

    fun updatePost(postId: Int, newPostData: Post): Single<Post> {
        return remoteDataSource.updatePost(postId, newPostData)
    }
}