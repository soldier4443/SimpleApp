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

    private val _showDeleteCompleteToast = MutableLiveData<Event<Int>>()
    val showDeleteCompleteToast: LiveData<Event<Int>>
        get() = _showDeleteCompleteToast

    // internal
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    init {
        loadPostsInitial()
    }

    private fun loadPostsInitial() {
        compositeDisposable += repository.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                // 매 번 스트림이 발생할 때마다 상태를 변경하는 게 맞음
                _state.value = NetworkState.LOADING
            }
            .subscribe({ posts ->
                if (posts.size > 0) {
                    _posts.value = posts
                    _state.value = NetworkState.LOADED
                }
            }, {
                _state.value = NetworkState.error(it)
            })
    }

    fun clickPostItem(post: Post) {
        _navigateToDetails.value = Event(post.id)
    }

    fun notifyPostDeleted(postId: Int) {
        _showDeleteCompleteToast.value = Event(postId)
    }
}
