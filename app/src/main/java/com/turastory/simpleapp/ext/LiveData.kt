package com.turastory.simpleapp.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T, L : LiveData<T>> LifecycleOwner.observe(
    liveData: L,
    function: (T) -> Unit
) {
    liveData.observe(this, Observer(function))
}