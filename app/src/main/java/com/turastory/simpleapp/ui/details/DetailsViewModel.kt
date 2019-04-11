package com.turastory.simpleapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.data.source.NetworkState
import com.turastory.simpleapp.ext.plusAssign
import com.turastory.simpleapp.ui.Event
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    companion object {
        const val TAG = "DetailsViewModel"
    }

    // Expose observables
    private val _postDetails = MutableLiveData<Post>()
    val postDetails: LiveData<Post>
        get() = _postDetails

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    private val _state = MutableLiveData<NetworkState>()
    val state: LiveData<NetworkState>
        get() = _state

    private val _showDeleteConfirmDialog = MutableLiveData<Event<Unit>>()
    val showDeleteConfirmDialog: LiveData<Event<Unit>>
        get() = _showDeleteConfirmDialog

    private val _showUpdateCompleteToast = MutableLiveData<Event<Unit>>()
    val showUpdateCompleteToast: LiveData<Event<Unit>>
        get() = _showUpdateCompleteToast

    private val _navigateBackToMain = MutableLiveData<Event<Int>>()
    val navigateBackToMain: LiveData<Event<Int>>
        get() = _navigateBackToMain

    private val _navigateToEditPost = MutableLiveData<Event<Post>>()
    val navigateToEditPost: LiveData<Event<Post>>
        get() = _navigateToEditPost

    // internal
    private var postId: Int = -1
    private var post: Post? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun initPostId(postId: Int) {
        this.postId = postId

        getPostDetails(postId)
    }

    private fun getPostDetails(postId: Int) {
        val postDetails = repository.getPost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(TAG, "Error while loading post details - $it")
            }
            .doOnSuccess { post ->
                this.post = post
                _postDetails.value = post
            }

        val requestComments = repository.getComments(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(TAG, "Error while loading comments - $it")
            }
            .doOnSuccess { comments ->
                _comments.value = comments
            }

        compositeDisposable += Single.merge(postDetails, requestComments)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _state.value = NetworkState.LOADING
            }
            .doOnTerminate {
                _state.value = NetworkState.LOADED
            }
            .subscribe()
    }

    fun updatePost(post: Post) {
        compositeDisposable += repository.updatePost(post)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _postDetails.value = post
                _showUpdateCompleteToast.value = Event(Unit)
            }, {
                Log.e(TAG, "Error while updating post ${post.id}")
            })
    }

    fun clickDeletePost() {
        _showDeleteConfirmDialog.value = Event(Unit)
    }

    fun clickEditPost() {
        // post가 null이면 어떻게 처리하는게 좋을까?
        post?.apply {
            _navigateToEditPost.value = Event(this)
        } ?: return
    }

    fun deletePost() {
        compositeDisposable += repository.deletePost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _navigateBackToMain.value = Event(postId)
            }, {
                Log.e("DetailsViewModel", "Error while deleting post - $it")
            })
    }
}