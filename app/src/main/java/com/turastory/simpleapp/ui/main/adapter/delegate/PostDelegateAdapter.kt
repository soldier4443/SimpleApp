package com.turastory.simpleapp.ui.main.adapter.delegate

import android.view.ViewGroup
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.DelegateViewHolder
import com.turastory.simpleapp.util.ViewType
import com.turastory.simpleapp.util.ViewTypeDelegateAdapter
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.layout_post.view.*

class PostDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): DelegateViewHolder {
        return PostViewHolder(parent)
    }

    class PostViewHolder(parent: ViewGroup) : DelegateViewHolder(R.layout.layout_post, parent) {
        override fun bind(item: ViewType) {
            val post: Post = item as Post
            with(itemView) {
                post_title.text = post.title
                post_body.text = post.body
            }
        }
    }
}