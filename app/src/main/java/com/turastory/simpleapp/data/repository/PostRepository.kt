package com.turastory.simpleapp.data.repository

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.data.Repository
import com.turastory.simpleapp.data.source.PostDataSource
import com.turastory.simpleapp.db.PostDatabase
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostRepository(
    private val postApiService: PostApiService,
    private val db: PostDatabase
) : Repository {

    private val sourceFactory = PostDataSource.Factory(postApiService)

    fun getPosts(): Observable<PagedList<Post>> {
        return sourceFactory.toObservable(
            PagedList.Config.Builder()
                .setPageSize(10)
                .setMaxSize(PagedList.Config.MAX_SIZE_UNBOUNDED)
                .build(),
            initialLoadKey = 0,
            fetchScheduler = Schedulers.io(),
            notifyScheduler = AndroidSchedulers.mainThread()
        )
    }

    fun getPost(postId: Int): Single<Post> {
        return postApiService.getPost(postId)
    }

    fun getComments(postId: Int): Single<List<Comment>> {
        return postApiService.getComments(postId)
    }

    fun deletePost(postId: Int): Completable {
        return postApiService.deletePost(postId)
    }

    fun updatePost(postId: Int, newPostData: Post): Single<Post> {
        return postApiService.updatePost(postId, newPostData)
    }
}