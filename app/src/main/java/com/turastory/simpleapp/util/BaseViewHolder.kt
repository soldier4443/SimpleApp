package com.turastory.simpleapp.util

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.turastory.simpleapp.ext.inflate

open class BaseViewHolder(@LayoutRes layoutId: Int, viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(viewGroup.inflate(layoutId))