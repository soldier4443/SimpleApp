package com.turastory.simpleapp.base

import com.turastory.simpleapp.data.repository.PostRepository
import com.turastory.simpleapp.data.source.PostRemoteDataSource
import com.turastory.simpleapp.network.api.PostApiService
import com.turastory.simpleapp.network.retrofitBuilder
import com.turastory.simpleapp.ui.details.DetailsContract
import com.turastory.simpleapp.ui.details.DetailsPresenter
import com.turastory.simpleapp.ui.main.MainContract
import com.turastory.simpleapp.ui.main.MainPresenter
import org.koin.dsl.module

val modules = module {
    single { postApi() }

    single { PostRemoteDataSource(get()) }

    single { PostRepository(get()) }

    factory<MainContract.Presenter> { MainPresenter(get()) }
    factory<DetailsContract.Presenter> { DetailsPresenter(get()) }
}

private fun postApi(): PostApiService {
    return retrofitBuilder
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .build()
        .create(PostApiService::class.java)
}