package com.turastory.simpleapp.ext

import android.app.Activity
import com.turastory.simpleapp.dagger.DaggerComponentProvider

val Activity.injector
    get() = (application as DaggerComponentProvider).component