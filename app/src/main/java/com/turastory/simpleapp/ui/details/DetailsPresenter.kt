package com.turastory.simpleapp.ui.details

import android.util.Log
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.util.plusAssign
import com.turastory.simpleapp.vo.Post
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
    private val postRepository: PostRepository
) : DetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: DetailsContract.View

    private var postId: Int = -1
    private var post: Post? = null

    override fun setView(view: DetailsContract.View) {
        this.view = view
    }

    override fun requestPostDetails(postId: Int) {
        this.postId = postId

        val postDetails = postRepository.getPost(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(DetailsContract.TAG, "Error while loading post details - $it")
            }
            .doOnSuccess { post ->
                this.post = post
                view.showPostDetails(post)
            }

        val requestComments = postRepository.getComments(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(DetailsContract.TAG, "Error while loading comments - $it")
            }
            .doOnSuccess { comments ->
                view.showComments(comments)
            }

        compositeDisposable += Single.merge(postDetails, requestComments)
            .doOnSubscribe {
                view.showLoadingPage()
            }
            .doOnTerminate {
                view.hideLoadingPage()
            }
            .subscribe()
    }

    override fun requestDeletePost() {
        view.showConfirmDialog()
    }

    override fun deletePost() {
        compositeDisposable += postRepository
            .deletePost(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(DetailsContract.TAG, "Erorr while deleting post $postId")
            }
            .subscribe {
                view.showDeletionComplete()
            }
    }

    override fun editPost() {
        post?.let { view.openEditPostView(it) }
    }

    override fun updatePost(post: Post) {
        compositeDisposable += postRepository
            .updatePost(postId, post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(DetailsContract.TAG, "Error while updating post $postId")
            }
            .subscribe { updatedPost ->
                updatedPost?.let {
                    this.post = it
                    view.showPostDetails(it)
                }
            }
    }

    override fun cleanUp() {
        Log.e("testtest", "Size of composite disposable: [${compositeDisposable.size()}]")
        compositeDisposable.dispose()
    }
}