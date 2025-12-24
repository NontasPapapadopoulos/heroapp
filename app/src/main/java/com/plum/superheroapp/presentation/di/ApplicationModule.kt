package com.plum.superheroapp.presentation.di

import android.app.Application
import android.content.Context
import com.plum.superheroapp.domain.executor.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @ExperimentalCoroutinesApi
    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(4)


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}
