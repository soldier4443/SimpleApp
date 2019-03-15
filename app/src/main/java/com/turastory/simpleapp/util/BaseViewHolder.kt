package com.turastory.simpleapp.util

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

open class BaseViewHolder(@LayoutRes layoutId: Int, viewGroup: ViewGroup)
    : RecyclerView.ViewHolder(viewGroup.inflate(layoutId))