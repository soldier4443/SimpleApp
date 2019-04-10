package com.turastory.simpleapp.data.source

import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.vo.Post
import io.reactivex.subjects.BehaviorSubject

// 일단 그냥 subscribe call하는 것으로 구현
// composite disposable을 받는게 좋을까?
class PostDataSource(private val postApiService: PostApiService) : PositionalDataSource<Post>() {

    class Factory(private val postApiService: PostApiService) : DataSource.Factory<Int, Post>() {
        override fun create(): DataSource<Int, Post> {
            return PostDataSource(postApiService)
        }
    }

    val networkStateStream: BehaviorSubject<NetworkState> = BehaviorSubject.create()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Post>) {
        Log.e("asdf", "load range called: [${params.startPosition}, ${params.loadSize}]")
        postApiService.getPosts(params.startPosition, params.loadSize)
            .doOnSubscribe { networkStateStream.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                callback.onResult(it)
                networkStateStream.onNext(NetworkState.LOADED)
            }
            .doOnError {
                networkStateStream.onNext(NetworkState.error(it.message ?: "Unknown Error"))
            }
            .subscribe()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Post>) {
        Log.e("asdf", "load initial called: [${params.requestedStartPosition}, ${params.requestedLoadSize}]")
        postApiService.getPosts(params.requestedStartPosition, params.requestedLoadSize)
            .doOnSubscribe { networkStateStream.onNext(NetworkState.LOADING) }
            .doOnSuccess {
                callback.onResult(it, params.requestedStartPosition, params.requestedLoadSize)
            }
            .doOnError {
                networkStateStream.onNext(NetworkState.error(it.message ?: "Unknown Error"))
            }
            .subscribe()
    }
}