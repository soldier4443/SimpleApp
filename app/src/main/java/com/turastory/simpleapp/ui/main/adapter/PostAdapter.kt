package com.turastory.simpleapp.ui.main.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.turastory.simpleapp.ui.main.adapter.delegate.LoadingDelegateAdapter
import com.turastory.simpleapp.ui.main.adapter.delegate.PostDelegateAdapter
import com.turastory.simpleapp.util.DelegateViewHolder
import com.turastory.simpleapp.util.ViewType
import com.turastory.simpleapp.util.ViewTypeDelegateAdapter
import com.turastory.simpleapp.vo.Post

/**
 * Presents the list of posts.
 */
class PostAdapter : RecyclerView.Adapter<DelegateViewHolder>() {

    private val delegates = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val items = mutableListOf<ViewType>()

    init {
        delegates.put(ViewType.LOADING, LoadingDelegateAdapter())
        delegates.put(ViewType.CONTENT, PostDelegateAdapter())
    }

    fun addPosts(newPosts: List<Post>) {
        // Add new posts
        val start = items.lastIndex

        items.addAll(start, newPosts)
        notifyItemRangeInserted(start, newPosts.size)
    }

    fun removePost(postId: Int) {
        val index = items.indexOfFirst { item -> item is Post && item.id == postId }

        items.removeAt(index)
        notifyItemRemoved(index)
    }

    // Does not count loading bar as an item.
    fun getPostCount(): Int {
        return if (isLoadingBarExists()) items.size - 1 else items.size
    }

    fun isNotLoading(pos: Int): Boolean {
        return items[pos].getViewType() != ViewType.LOADING
    }

    fun hideLoadingBar() {
        // last item should be loading bar
        if (isLoadingBarExists()) {
            val index = items.lastIndex

            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun showLoadingBar() {
        if (!isLoadingBarExists()) {
            items += object : ViewType {
                override fun getViewType(): Int {
                    return ViewType.LOADING
                }
            }

            notifyItemInserted(items.lastIndex)
        }
    }

    private fun isLoadingBarExists(): Boolean =
        items.lastIndex != -1 && items[items.lastIndex].getViewType() == ViewType.LOADING

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DelegateViewHolder {
        return delegates.get(viewType)?.onCreateViewHolder(parent)
            ?: throw IllegalArgumentException("Invalid viewType")
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        delegates.get(viewType)?.onBindViewHolder(holder, items[position], position)
    }
}
