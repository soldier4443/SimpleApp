package com.turastory.simpleapp.data.repository

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.toObservable
import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.data.Repository
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

    private val postDao = db.postDao()

    fun getPosts(): Observable<PagedList<Post>> {
        val pageSize = 10

        val boundaryCallback = PostBoundaryCallback(
            initialLoadSize = pageSize * 5,
            loadSize = pageSize * 3,
            api = postApiService,
            loadCallback = { posts ->
                db.runInTransaction {
                    // Room automatically invalidates when the data changes
                    postDao.insert(posts)
                    Log.e("asdf", "Items inserted in the database correctly")
                }
            })

        return postDao.getAll().toObservable(
            pageSize,
            initialLoadKey = 0,
            boundaryCallback = boundaryCallback,
            fetchScheduler = Schedulers.io(),
            notifyScheduler = AndroidSchedulers.mainThread()
        )
    }

    // Suppose we already loaded posts for local use.
    fun getPost(postId: Int): Single<Post> {
        return postDao.getPostById(postId)
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