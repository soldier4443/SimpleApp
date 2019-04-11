package com.turastory.simpleapp.data.source

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.vo.Post

// 일단 그냥 subscribe call하는 것으로 구현
// composite disposable을 받는게 좋을까?
class PostDataSource(private val postApiService: PostApiService) : PageKeyedDataSource<Int, Post>() {

    companion object {
        const val TAG = "PostDataSource"
    }

    class Factory(private val postApiService: PostApiService) : DataSource.Factory<Int, Post>() {
        override fun create(): DataSource<Int, Post> {
            return PostDataSource(postApiService)
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Post>) {
        Log.d(TAG, "load initial called: [0, ${params.requestedLoadSize}]")
        postApiService.getPosts(0, params.requestedLoadSize)
            .doOnSuccess {
                callback.onResult(it, 0, params.requestedLoadSize)
            }
            .subscribe()
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        Log.d(TAG, "load range called: [${params.key}, ${params.requestedLoadSize}]")
        val nextPageKey = params.key + params.requestedLoadSize
        postApiService.getPosts(params.key, params.requestedLoadSize)
            .doOnSuccess {
                callback.onResult(it, nextPageKey)
            }
            .subscribe()
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        // We don't have anything to load before.
    }
}