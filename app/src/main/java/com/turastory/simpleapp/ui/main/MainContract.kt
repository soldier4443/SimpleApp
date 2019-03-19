package com.turastory.simpleapp.ui.main

import com.turastory.simpleapp.base.BasePresenter
import com.turastory.simpleapp.base.BaseView
import com.turastory.simpleapp.vo.Post

interface MainContract {
    companion object {
        const val TAG = "Main"
        const val POST_LOAD_LIMIT = 10
        const val REQUEST_DETAILS = 100
    }

    interface View : BaseView {
        fun showNewPosts(posts: List<Post>)
        fun hideDeletedPost(deletedPostId: Int)
        fun showLoadingBar()
        fun hideLoadingBar()
        fun openDetailsView(id: Int)
    }

    interface Presenter : BasePresenter<View> {
        override fun setView(view: View)
        fun requestNewPosts()
        fun onItemClick(pos: Int)
        fun notifyPostDeleted(deletedPostId: Int)
    }
}