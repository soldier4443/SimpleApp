package com.turastory.simpleapp.util

interface ViewType {
    companion object {
        const val CONTENT = 0
        const val LOADING = 1
    }

    fun getViewType(): Int
}