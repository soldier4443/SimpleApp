package com.turastory.simpleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock

inline fun <reified T> mock(): T = mock(T::class.java) as T

inline fun <reified T> observeAndVerify(liveData: LiveData<T>) {
    val observer = mock<Observer<T>>()
    liveData.observeForever(observer)
    Mockito.verify(observer).onChanged(ArgumentMatchers.any())
}

inline fun <reified T> observeAndVerify(liveData: LiveData<T>, expected: T) {
    val observer = mock<Observer<T>>()
    liveData.observeForever(observer)
    Mockito.verify(observer).onChanged(expected)
}
