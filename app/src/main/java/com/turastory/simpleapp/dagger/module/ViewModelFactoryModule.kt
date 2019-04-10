package com.turastory.simpleapp.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.turastory.simpleapp.ui.main.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(
        viewModelFactory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}