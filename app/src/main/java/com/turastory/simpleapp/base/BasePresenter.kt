package com.turastory.simpleapp.base

interface BasePresenter<T : BaseView> {
    fun setView(view: T)
}