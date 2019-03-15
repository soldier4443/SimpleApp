package com.turastory.simpleapp.ui.details

import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post

interface DetailsContract {
    companion object {
        const val TAG = "Details"
    }

    interface View {
        fun showPostDetails(post: Post)
        fun showComments(comments: List<Comment>)
        fun showLoadingPage()
        fun hideLoadingPage()
    }

    interface Presenter {
        fun setView(view: View)
        fun requestPostDetails(postId: Int)
    }
}