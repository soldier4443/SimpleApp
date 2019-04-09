package com.turastory.simpleapp.dagger

import com.turastory.simpleapp.dagger.component.AppComponent

interface DaggerComponentProvider {
    val component: AppComponent
}