package com.turastory.simpleapp.ui.main.adapter.delegate

import android.view.ViewGroup
import com.turastory.simpleapp.R
import com.turastory.simpleapp.util.DelegateViewHolder
import com.turastory.simpleapp.util.ViewType
import com.turastory.simpleapp.util.ViewTypeDelegateAdapter

/**
 * Created by nyh0111 on 2017-06-28.
 */

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): DelegateViewHolder {
        return LoadingViewHolder(parent)
    }

    override fun onBindViewHolder(holder: DelegateViewHolder, item: ViewType, position: Int) {
    }

    class LoadingViewHolder(parent: ViewGroup)
        : DelegateViewHolder(R.layout.layout_loading, parent)
}
