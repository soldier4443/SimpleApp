package com.turastory.simpleapp.ui.details

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turastory.simpleapp.vo.Comment

@BindingAdapter(value = ["app:items"])
fun RecyclerView.bindComments(comments: List<Comment>?) {
    if (comments == null) return
    val adapter = adapter as? CommentAdapter ?: return
    adapter.submitComments(comments)
}