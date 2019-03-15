package com.turastory.simpleapp.ui.details

import android.util.Log
import com.turastory.simpleapp.network.doOnSuccess
import com.turastory.simpleapp.network.postApi
import java.util.concurrent.CountDownLatch

class DetailsPresenter : DetailsContract.Presenter {

    private lateinit var view: DetailsContract.View
    private var counter: CountDownLatch? = null

    override fun setView(view: DetailsContract.View) {
        this.view = view
    }

    override fun requestPostDetails(postId: Int) {
        counter = CountDownLatch(2)
        view.showLoadingPage()

        postApi()
            .getPost(postId)
            .doOnSuccess {
                Log.e("testtest", "load post details success")
                view.showPostDetails(it)
                checkLoaded()
            }
            .doOnFailed {
                Log.e(DetailsContract.TAG, "Error while loading post details - $it")
            }
            .done()

        requestComments(postId)
    }

    private fun requestComments(postId: Int) {
        postApi()
            .getComments(postId)
            .doOnSuccess {
                Log.e("testtest", "load comments success")
                view.showComments(it)
                checkLoaded()
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
}