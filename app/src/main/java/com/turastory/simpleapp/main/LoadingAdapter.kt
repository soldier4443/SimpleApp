package com.turastory.simpleapp.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.DelegateViewHolder

class LoadingAdapter : RecyclerView.Adapter<LoadingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(p0)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    class ViewHolder(parent: ViewGroup)
        : DelegateViewHolder(R.layout.layout_loading, parent)
}