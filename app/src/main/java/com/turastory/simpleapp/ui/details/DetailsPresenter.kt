package com.turastory.simpleapp.ui.details

import android.util.Log
import com.turastory.simpleapp.network.doOnSuccess
import com.turastory.simpleapp.network.postApi
import java.util.concurrent.CountDownLatch

class DetailsPresenter : DetailsContract.Presenter {

    private lateinit var view: DetailsContract.View
    private var counter: CountDownLatch? = null

    private var postId: Int = -1

    override fun setView(view: DetailsContract.View) {
        this.view = view
    }

    override fun requestPostDetails(postId: Int) {
        this.postId = postId

        counter = CountDownLatch(2)
        view.showLoadingPage()

        postApi()
            .getPost(postId)
            .doOnSuccess {
                it?.run {
                    Log.e("testtest", "load post details success")
                    view.showPostDetails(this)
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
        postApi()
            .getComments(postId)
            .doOnSuccess {
                it?.run {
                    Log.e("testtest", "load comments success")
                    view.showComments(this)
                    checkLoaded()
                }
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Error while loading comments - $it")
            }
            .done()
    }

    override fun requestDeletePost() {
        view.showConfirmDialog()
    }

    override fun deletePost() {
        postApi()
            .deletePost(postId)
            .doOnSuccess {
                view.showDeletionComplete()
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Erorr while deleting post $postId")
                Log.e(DetailsContract.TAG, it)
            }
            .done()
    }

    override fun editPost() {
        // TODO
    }

    private fun checkLoaded() {
        counter?.countDown()

        val count = counter?.count ?: 0
        if (count == 0L)
            view.hideLoadingPage()
    }
}