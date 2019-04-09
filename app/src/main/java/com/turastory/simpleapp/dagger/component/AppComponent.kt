package com.turastory.simpleapp.dagger.component

import com.turastory.simpleapp.dagger.module.AppModule
import com.turastory.simpleapp.dagger.scope.ActivityScope
import com.turastory.simpleapp.ui.details.DetailsActivity
import com.turastory.simpleapp.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [
        AppModule::class
    ],
    dependencies = [
        NetworkComponent::class
    ]
)
interface AppComponent {

    fun inject(a: MainActivity)
    fun inject(a: DetailsActivity)

    // How to construct the component itself
//    @Component.Builder
//    interface Builder {
//        fun build(): AppComponent
//    }
}