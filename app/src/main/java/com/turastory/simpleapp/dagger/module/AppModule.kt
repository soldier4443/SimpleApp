package com.turastory.simpleapp.dagger.module

import com.turastory.simpleapp.dagger.scope.ActivityScope
import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.ui.details.DetailsContract
import com.turastory.simpleapp.ui.details.DetailsPresenter
import com.turastory.simpleapp.ui.main.MainContract
import com.turastory.simpleapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @ActivityScope
    @Provides
    fun provideMainPresenter(repository: PostRepository): MainContract.Presenter {
        return MainPresenter(repository)
    }

    @ActivityScope
    @Provides
    fun provideDetailsPresenter(repository: PostRepository): DetailsContract.Presenter {
        return DetailsPresenter(repository)
    }
}