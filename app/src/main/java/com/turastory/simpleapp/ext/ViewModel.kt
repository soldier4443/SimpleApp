package com.turastory.simpleapp.ext

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(vmf: ViewModelProvider.Factory? = null) =
    ViewModelProviders.of(this, vmf).get(T::class.java)