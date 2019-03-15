package com.turastory.simpleapp.ui.main

import com.turastory.simpleapp.vo.Post

interface MainContract {
    companion object {
        const val TAG = "Main"
        const val POST_LOAD_LIMIT = 10
    }

    interface View {
        fun showNewPosts(posts: List<Post>)
        fun showLoadingBar()
        fun hideLoadingBar()
        fun openDetailsView(id: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun requestNewPosts()
        fun onItemClick(pos: Int)
    }
}