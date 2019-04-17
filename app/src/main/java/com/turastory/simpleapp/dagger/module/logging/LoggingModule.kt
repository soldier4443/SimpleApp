package com.turastory.simpleapp.dagger.module.logging

import com.turastory.simpleapp.util.Logger
import com.turastory.simpleapp.util.LoggerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggingModule {
    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return LoggerImpl()
    }
}