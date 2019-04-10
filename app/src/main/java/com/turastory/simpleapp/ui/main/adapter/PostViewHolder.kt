package com.turastory.simpleapp.ui.main.adapter

import android.view.ViewGroup
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.BaseViewHolder
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.layout_post.view.*

class PostViewHolder(parent: ViewGroup) :
    BaseViewHolder(R.layout.layout_post, parent) {
    fun bind(post: Post?) {
        post?.let {
            with(itemView) {
                post_title.text = post.title
                post_body.text = post.body
            }
        } ?: apply {
            with(itemView) {
                post_title.setText(R.string.text_loading)
                post_body.setText(R.string.text_loading)
            }
        }
    }
}