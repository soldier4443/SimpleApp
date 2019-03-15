package com.turastory.simpleapp.util

import android.view.ViewGroup

open class DelegateViewHolder(layoutId: Int, viewGroup: ViewGroup)
    : BaseViewHolder(layoutId, viewGroup) {

    open fun bind(item: ViewType) {

    }
}