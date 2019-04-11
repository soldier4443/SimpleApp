package com.turastory.simpleapp.dagger.component

import com.turastory.simpleapp.dagger.module.app.ViewModelFactoryModule
import com.turastory.simpleapp.dagger.module.app.ViewModelModule
import com.turastory.simpleapp.dagger.scope.ActivityScope
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ],
    dependencies = [
        DataComponent::class
    ]
)
interface AppComponent {
    fun inject(a: MainActivity)
    fun inject(a: DetailsActivity)
}