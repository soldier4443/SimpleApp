package com.turastory.simpleapp.dagger.module.app

import androidx.lifecycle.ViewModel
import com.turastory.simpleapp.dagger.ViewModelKey
import com.turastory.simpleapp.ui.details.DetailsViewModel
import com.turastory.simpleapp.ui.edit.EditPostViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditPostViewModel::class)
    abstract fun provideEditPostViewModel(editPostViewModel: EditPostViewModel): ViewModel
}