package com.turastory.simpleapp.dagger.module.app

import com.turastory.simpleapp.dagger.scope.ActivityScope
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.ui.details.DetailsContract
import com.turastory.simpleapp.ui.details.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @ActivityScope
    @Provides
    fun provideDetailsPresenter(repository: PostRepository): DetailsContract.Presenter {
        return DetailsPresenter(repository)
    }
}