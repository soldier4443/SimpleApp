package com.turastory.simpleapp.data.source

import com.turastory.simpleapp.network.RequestWrapper
import com.turastory.simpleapp.network.api.PostApiService
import com.turastory.simpleapp.network.wrap
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post

class PostRemoteDataSource(private val api: PostApiService) {

    fun getPosts(start: Int, limit: Int): RequestWrapper<List<Post>> {
        return api.getPosts(start, limit).wrap()
    }

    fun getPost(postId: Int): RequestWrapper<Post> {
        return api.getPost(postId).wrap()
    }

    fun getComments(postId: Int): RequestWrapper<List<Comment>> {
        return api.getComments(postId).wrap()
    }

    fun deletePost(postId: Int): RequestWrapper<Void> {
        return api.deletePost(postId).wrap()
    }

    fun updatePost(postId: Int, newPostData: Post): RequestWrapper<Post> {
        return api.updatePost(postId, newPostData).wrap()
    }
}