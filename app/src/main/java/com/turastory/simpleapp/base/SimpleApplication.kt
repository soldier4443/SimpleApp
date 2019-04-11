package com.turastory.simpleapp.base

import android.app.Application
import com.turastory.simpleapp.dagger.DaggerComponentProvider
import com.turastory.simpleapp.dagger.component.AppComponent
import com.turastory.simpleapp.dagger.component.DaggerAppComponent
import com.turastory.simpleapp.dagger.component.DaggerDataComponent
import com.turastory.simpleapp.dagger.module.data.RoomModule

class SimpleApplication : Application(), DaggerComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        val dataComponent = DaggerDataComponent.builder()
            .roomModule(RoomModule(this))
            .build()

        appComponent = DaggerAppComponent.builder()
            .dataComponent(dataComponent)
            .build()
    }

    override val component: AppComponent
        get() = appComponent
}