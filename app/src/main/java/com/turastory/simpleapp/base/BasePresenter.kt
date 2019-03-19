package com.turastory.simpleapp.base

interface BasePresenter<T : BaseView> {
    fun setView(view: T)
    fun cleanUp() {
        // For release resources, dispose network requests, etc...
    }
}