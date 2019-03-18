package com.turastory.simpleapp.data.repository

import com.turastory.simpleapp.data.Repository
import com.turastory.simpleapp.data.source.PostRemoteDataSource
import com.turastory.simpleapp.network.RequestWrapper
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post

class PostRepository(private val remoteDataSource: PostRemoteDataSource) : Repository {

    fun getPosts(start: Int, limit: Int): RequestWrapper<List<Post>> {
        return remoteDataSource.getPosts(start, limit)
    }

    fun getPost(postId: Int): RequestWrapper<Post> {
        return remoteDataSource.getPost(postId)
    }

    fun getComments(postId: Int): RequestWrapper<List<Comment>> {
        return remoteDataSource.getComments(postId)
    }

    fun deletePost(postId: Int): RequestWrapper<Void> {
        return remoteDataSource.deletePost(postId)
    }

    fun updatePost(postId: Int, newPostData: Post): RequestWrapper<Post> {
        return remoteDataSource.updatePost(postId, newPostData)
    }
}