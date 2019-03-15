package com.turastory.simpleapp.main

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
    }

    interface Presenter {
        fun setView(view: View)
        fun requestNewPosts()
    }
}