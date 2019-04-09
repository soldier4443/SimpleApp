package com.turastory.simpleapp.base

import android.app.Application
import com.turastory.simpleapp.dagger.DaggerComponentProvider
import com.turastory.simpleapp.dagger.component.AppComponent
import com.turastory.simpleapp.dagger.component.DaggerAppComponent
import com.turastory.simpleapp.dagger.component.DaggerNetworkComponent

class SimpleApplication : Application(), DaggerComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .networkComponent(DaggerNetworkComponent.create())
            .build()
    }

    override val component: AppComponent
        get() = appComponent
}