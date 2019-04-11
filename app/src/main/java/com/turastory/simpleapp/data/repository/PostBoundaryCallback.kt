package com.turastory.simpleapp.data.repository

import android.util.Log
import androidx.paging.PagedList
import com.turastory.simpleapp.api.PostApiService
import com.turastory.simpleapp.vo.Post
import io.reactivex.schedulers.Schedulers

// TODO: Manage disposables
// TODO: Logging errors
class PostBoundaryCallback(
    private val initialLoadSize: Int = 30,
    private val loadSize: Int = initialLoadSize,
    private val api: PostApiService,
    private val loadCallback: (List<Post>) -> Unit
) : PagedList.BoundaryCallback<Post>() {

    override fun onZeroItemsLoaded() {
        // No data exists in DB. should fetch data from the api.
        Log.e("asdf", "No data exists in DB. should fetch data from the api.")
        api.getPosts(0, initialLoadSize)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { posts ->
                if (posts.isNotEmpty()) {
                    loadCallback(posts)
                }
            }
            .doOnError {
                Log.e("asdf", "Error while fetching posts from the network in onZeroItemLoaded()")
            }
            .subscribe()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        // End of our database reached. should fetch data from the api.
        Log.e("asdf", "End of our database reached. should fetch data from the api.")
        api.getPosts(itemAtEnd.id, loadSize)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { posts ->
                if (posts.isNotEmpty()) {
                    loadCallback(posts)
                }
            }
            .doOnError {
                Log.e("asdf", "Error while fetching posts from the network in onItemAtEndLoaded()")
            }
            .subscribe()
    }
}
