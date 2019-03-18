package com.turastory.simpleapp.ui.details

import com.turastory.simpleapp.vo.Comment
import com.turastory.simpleapp.vo.Post

interface DetailsContract {
    companion object {
        const val TAG = "Details"
        const val REQUEST_POST_EDIT = 101
    }

    interface View {
        fun showPostDetails(post: Post)
        fun showComments(comments: List<Comment>)
        fun showLoadingPage()
        fun hideLoadingPage()
        fun showConfirmDialog()
        fun openEditPostView(post: Post)
        fun showDeletionComplete()
    }

    interface Presenter {
        fun setView(view: View)
        fun requestPostDetails(postId: Int)
        fun requestDeletePost()
        fun editPost()
        fun deletePost()
        fun updatePost(post: Post)
    }
}