package com.turastory.simpleapp.vo

import com.turastory.simpleapp.main.ViewType

data class Post(val id: Int,
                val userId: Int,
                val title: String,
                val body: String) : ViewType {
    override fun getViewType(): Int {
        return ViewType.CONTENT
    }
}