package com.turastory.simpleapp.ui.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.data.source.NetworkState
import com.turastory.simpleapp.ext.plusAssign
import com.turastory.simpleapp.ui.Event
import com.turastory.simpleapp.ui.SimpleEvent
import com.turastory.simpleapp.util.Logger
import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: PostRepository,
    private val logger: Logger
) : ViewModel() {

    companion object {
        const val TAG = "DetailsViewModel"
    }

    // Expose observables
    private val _postTitle = MutableLiveData<String>()
    val postTitle: LiveData<String>
        get() = _postTitle

    private val _postBody = MutableLiveData<String>()
    val postBody: LiveData<String>
        get() = _postBody

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    private val _state = MutableLiveData<NetworkState>()
    val state: LiveData<NetworkState>
        get() = _state

    private val _showDeleteConfirmDialog = MutableLiveData<SimpleEvent>()
    val showDeleteConfirmDialog: LiveData<SimpleEvent>
        get() = _showDeleteConfirmDialog

    private val _showUpdateCompleteToast = MutableLiveData<SimpleEvent>()
    val showUpdateCompleteToast: LiveData<SimpleEvent>
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
                logger.e("Error while loading post details - $it", TAG)
            }
            .doOnSuccess { post ->
                this.post = post
                setPostData(post)
            }

        val requestComments = repository.getComments(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                logger.e("Error while loading comments - $it", TAG)
            }
            .doOnSuccess { comments ->
                _comments.postValue(comments)
            }

        compositeDisposable += Single.merge(postDetails, requestComments)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                logger.e("Error while... $it", TAG)
            }
            .doOnSubscribe {
                _state.postValue(NetworkState.LOADING)
            }
            .doOnTerminate {
                _state.postValue(NetworkState.LOADED)
            }
            .subscribe()
    }

    fun updatePost(post: Post) {
        compositeDisposable += repository.updatePost(post)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setPostData(post)
                _showUpdateCompleteToast.postValue(SimpleEvent())
            }, {
                logger.e("Error while updating post ${post.id}", TAG)
            })
    }

    private fun setPostData(post: Post) {
        _postTitle.postValue(post.title)
        _postBody.postValue(post.body)
    }

    fun clickDeletePost() {
        _showDeleteConfirmDialog.postValue(SimpleEvent())
    }

    fun clickEditPost() {
        // post가 null이면 어떻게 처리하는게 좋을까?
        post?.apply {
            _navigateToEditPost.postValue(Event(this))
        } ?: return
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun setPost(post: Post) {
        this.post = post
    }

    fun deletePost() {
        compositeDisposable += repository.deletePost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _navigateBackToMain.postValue(Event(postId))
            }, {
                logger.e("DetailsViewModel", "Error while deleting post - $it")
            })
    }
}