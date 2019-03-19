package com.turastory.simpleapp.ui.main

import android.util.Log
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.util.plusAssign
import com.turastory.simpleapp.vo.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
    private val postRepository: PostRepository
) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: MainContract.View

    private val posts = mutableListOf<Post>()

    override fun setView(view: MainContract.View) {
        this.view = view

        view.showLoadingBar()
    }

    override fun requestNewPosts() {
        compositeDisposable += postRepository
            .getPosts(posts.size, MainContract.POST_LOAD_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e(MainContract.TAG, "Error while loading posts - $it")
            }
            .subscribe { newPosts ->
                newPosts?.let {
                    if (newPosts.isNotEmpty()) {
                        posts += it
                        view.showNewPosts(it)
                    } else {
                        view.hideLoadingBar()
                    }
                }
            }
    }

    override fun onItemClick(pos: Int) {
        view.openDetailsView(posts[pos].id)
    }

    override fun cleanUp() {
        compositeDisposable.dispose()
    }

    override fun notifyPostDeleted(deletedPostId: Int) {
        view.hideDeletedPost(deletedPostId)
    }
}
