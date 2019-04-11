package com.turastory.simpleapp.dagger.module.app

import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.dagger.ViewModelKey
import com.turastory.simpleapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * List of ViewModels
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}