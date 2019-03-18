package com.turastory.simpleapp.ui.details

import android.util.Log
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.vo.Post
import java.util.concurrent.CountDownLatch

class DetailsPresenter(
    private val postRepository: PostRepository
) : DetailsContract.Presenter {

    private lateinit var view: DetailsContract.View
    private var counter: CountDownLatch? = null

    private var postId: Int = -1
    private var post: Post? = null

    override fun setView(view: DetailsContract.View) {
        this.view = view
    }

    override fun requestPostDetails(postId: Int) {
        this.postId = postId

        counter = CountDownLatch(2)
        view.showLoadingPage()

        postRepository
            .getPost(postId)
            .doOnSuccess {
                it?.let { post ->
                    this.post = post
                    view.showPostDetails(post)
                    checkLoaded()
                }
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Error while loading post details - $it")
            }
            .done()

        requestComments()
    }

    private fun requestComments() {
        postRepository
            .getComments(postId)
            .doOnSuccess {
                it?.run {
                    view.showComments(this)
                    checkLoaded()
                }
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Error while loading comments - $it")
            }
            .done()
    }

    private fun checkLoaded() {
        counter?.countDown()

        val count = counter?.count ?: 0
        if (count == 0L)
            view.hideLoadingPage()
    }

    override fun requestDeletePost() {
        view.showConfirmDialog()
    }

    override fun deletePost() {
        postRepository
            .deletePost(postId)
            .doOnSuccess {
                view.showDeletionComplete()
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Erorr while deleting post $postId")
            }
            .done()
    }

    override fun editPost() {
        post?.let { view.openEditPostView(it) }
    }

    override fun updatePost(post: Post) {
        postRepository
            .updatePost(postId, post)
            .doOnSuccess { updatedPost ->
                updatedPost?.let {
                    this.post = it
                    view.showPostDetails(it)
                }
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Error while updating post $postId")
            }
            .done()
    }
}