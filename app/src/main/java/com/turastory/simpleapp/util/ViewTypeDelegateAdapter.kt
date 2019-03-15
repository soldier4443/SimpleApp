package com.turastory.simpleapp.util

import android.view.ViewGroup
import com.turastory.simpleapp.main.ViewType

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): DelegateViewHolder

    fun onBindViewHolder(holder: DelegateViewHolder, item: ViewType, position: Int) {
        holder.bind(item)
    }
}