package com.plum.superheroapp.presentation.di

import com.plum.superheroapp.data.cache.HeroDao
import com.plum.superheroapp.data.datasource.HeroLocalDataSource
import com.plum.superheroapp.data.datasource.HeroLocalDataSourceImpl
import com.plum.superheroapp.data.datasource.HeroRemoteDataSource
import com.plum.superheroapp.data.datasource.HeroRemoteDataSourceImpl
import com.plum.superheroapp.data.network.HeroesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideHeroLocalDataSource(
        dao: HeroDao
    ): HeroLocalDataSource {
        return HeroLocalDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideHeroRemoteDataSource(api: HeroesApi): HeroRemoteDataSource {
        return HeroRemoteDataSourceImpl(api)
    }


}