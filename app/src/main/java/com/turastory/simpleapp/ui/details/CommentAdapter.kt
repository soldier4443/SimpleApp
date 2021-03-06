package com.turastory.simpleapp.ui.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.BaseViewHolder
import com.turastory.simpleapp.vo.Comment
import kotlinx.android.synthetic.main.layout_comment.view.*

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val comments = mutableListOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(comments[position])
    }

    fun submitComments(comments: List<Comment>) {
        this.comments.apply {
            clear()
            addAll(comments)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(viewGroup: ViewGroup) : BaseViewHolder(R.layout.layout_comment, viewGroup) {
        fun bind(comment: Comment) {
            with(itemView) {
                comment_name.text = comment.name
                comment_body.text = comment.body
            }
        }
    }
}
