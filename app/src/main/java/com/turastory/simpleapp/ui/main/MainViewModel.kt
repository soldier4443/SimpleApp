package com.turastory.simpleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.data.source.NetworkState
import com.turastory.simpleapp.ext.plusAssign
import com.turastory.simpleapp.ui.Event
import com.turastory.simpleapp.vo.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * MainViewModel
 *
 * Post의 리스트를 가져와서 View에 던져줌
 */
class MainViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    // Exposed observables
    private val _state = MutableLiveData<NetworkState>()
    val state: LiveData<NetworkState>
        get() = _state

    private val _posts = MutableLiveData<PagedList<Post>>()
    val posts: LiveData<PagedList<Post>>
        get() = _posts

    private val _navigateToDetails = MutableLiveData<Event<Int>>()
    val navigateToDetails: LiveData<Event<Int>>
        get() = _navigateToDetails

    // internal
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    init {
        loadPosts()
    }

    // called from view

    fun loadPosts() {
        repository.getPosts()
            .apply {
                compositeDisposable += networkState
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ networkState ->
                        _state.value = networkState
                    }, {
                        _state.value = NetworkState.error(it)
                    })

                compositeDisposable += pagedList
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ posts ->
                        _posts.value = posts
                    }, {
                        _state.value = NetworkState.error(it)
                    })
            }
    }

    fun clickPostItemOn(pos: Int) {
        _navigateToDetails.value = Event(pos)
    }

    fun deletePost(id: Int) {
        // Search
    }
}
