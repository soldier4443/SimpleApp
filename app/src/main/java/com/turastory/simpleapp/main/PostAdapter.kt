package com.turastory.simpleapp.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.BaseViewHolder
import com.turastory.simpleapp.vo.Post
import kotlinx.android.synthetic.main.layout_post.view.*

/**
 * TODO: insertion animation
 * TODO: infinite scroll
 */
class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val posts = mutableListOf<Post>()

    // All posts are provided from outside.
    fun loadPosts(newPosts: List<Post>) {
        val start = posts.size

        posts.addAll(newPosts)
        notifyItemRangeInserted(start, newPosts.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    class ViewHolder(viewGroup: ViewGroup) : BaseViewHolder(R.layout.layout_post, viewGroup) {
        fun bind(post: Post) {
            with(itemView) {
                post_title.text = post.title
                post_body.text = post.body
            }
        }
    }
}
